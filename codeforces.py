import hashlib
import json
import os
import random
import time
from pathlib import Path
from urllib.parse import urlencode

import requests


HANDLE = os.environ.get("CF_HANDLE", "rohitkerketta484")
COUNT = int(os.environ.get("CF_COUNT", "100"))

API_KEY = os.environ["CF_API_KEY"]
API_SECRET = os.environ["CF_API_SECRET"]

ROOT = Path(__file__).resolve().parent
STATE_FILE = ROOT / "data" / "submissions.json"

API_URL = "https://codeforces.com/api/user.status"


def load_state():
    STATE_FILE.parent.mkdir(parents=True, exist_ok=True)

    if not STATE_FILE.exists():
        return {"processed_submission_ids": []}

    try:
        return json.loads(STATE_FILE.read_text(encoding="utf-8"))
    except (json.JSONDecodeError, OSError):
        return {"processed_submission_ids": []}


def save_state(state):
    STATE_FILE.write_text(
        json.dumps(state, indent=2),
        encoding="utf-8"
    )


def get_extension(language):
    language = language.lower()

    if "java" in language:
        return ".java"

    if "kotlin" in language:
        return ".kt"

    if "python" in language:
        return ".py"

    if "gnu c++" in language or "g++" in language:
        return ".cpp"

    if "clang++" in language or "c++" in language:
        return ".cpp"

    if language.startswith("gnu c"):
        return ".c"

    if language == "c" or language.startswith("c "):
        return ".c"

    if "javascript" in language:
        return ".js"

    if "typescript" in language:
        return ".ts"

    if "rust" in language:
        return ".rs"

    if "go" in language:
        return ".go"

    return ".txt"


def safe_name(value):
    bad_chars = '<>:"/\\|?*'

    for char in bad_chars:
        value = value.replace(char, "")

    value = "_".join(value.split())

    return value[:100] or "unknown"


def make_api_signature(method, params):
    rand = str(random.randint(100000, 999999))

    sorted_params = sorted(params.items())

    query_string = urlencode(sorted_params)

    signature_string = (
        f"{rand}/{method}?{query_string}#{API_SECRET}"
    )

    hash_value = hashlib.sha512(
        signature_string.encode("utf-8")
    ).hexdigest()

    return rand + hash_value


def get_submissions():
    current_time = int(time.time())

    params = {
        "handle": HANDLE,
        "from": 1,
        "count": COUNT,
        "includeSources": "true",
        "apiKey": API_KEY,
        "time": current_time,
    }

    api_sig = make_api_signature(
        "user.status",
        params
    )

    params["apiSig"] = api_sig

    response = requests.get(
        API_URL,
        params=params,
        timeout=30
    )

    response.raise_for_status()

    data = response.json()

    if data.get("status") != "OK":
        raise RuntimeError(
            f"Codeforces API error: "
            f"{data.get('comment', 'unknown error')}"
        )

    return data["result"]


def main():
    print(f"Checking Codeforces submissions for: {HANDLE}")

    state = load_state()

    processed = set(
        str(x)
        for x in state.get(
            "processed_submission_ids",
            []
        )
    )

    submissions = get_submissions()

    print(f"Found {len(submissions)} submissions.")

    accepted = [
        submission
        for submission in submissions
        if submission.get("verdict") == "OK"
        and submission.get("contestId") is not None
    ]

    print(
        f"Found {len(accepted)} accepted submissions."
    )

    accepted.reverse()

    added = 0

    for submission in accepted:

        submission_id = str(submission["id"])

        if submission_id in processed:
            continue

        contest_id = submission["contestId"]
        problem_index = submission["problem"]["index"]
        problem_name = submission["problem"]["name"]
        language = submission.get(
            "programmingLanguage",
            "Unknown"
        )

        source = submission.get("source")

        if not source:
            source = submission.get("sourceCode")

        if not source:
            print(
                f"ERROR: Source code not returned "
                f"for submission {submission_id}"
            )
            continue

        folder_name = safe_name(
            f"{contest_id}{problem_index}_{problem_name}"
        )

        extension = get_extension(language)

        folder = ROOT / folder_name

        output_file = (
            folder / f"solution{extension}"
        )

        # One solution per problem.
        if folder.exists() and any(folder.iterdir()):
            print(
                f"Skipping existing problem: "
                f"{folder_name}"
            )

            processed.add(submission_id)
            continue

        print(
            f"Adding #{submission_id}: "
            f"{contest_id}{problem_index} - "
            f"{problem_name} [{language}]"
        )

        folder.mkdir(
            parents=True,
            exist_ok=True
        )

        output_file.write_text(
            source,
            encoding="utf-8"
        )

        metadata = {
            "submission_id": submission["id"],
            "contest_id": contest_id,
            "problem_index": problem_index,
            "problem_name": problem_name,
            "language": language,
            "verdict": submission.get("verdict"),
            "rating": submission["problem"].get("rating"),
            "tags": submission["problem"].get(
                "tags",
                []
            ),
        }

        metadata_file = folder / "metadata.json"

        metadata_file.write_text(
            json.dumps(
                metadata,
                indent=2
            ),
            encoding="utf-8"
        )

        processed.add(submission_id)

        added += 1

        # Codeforces API recommends limiting requests.
        time.sleep(2)

    state["processed_submission_ids"] = sorted(
        processed,
        key=int
    )

    save_state(state)

    print(
        f"Done. Added {added} new accepted solution(s)."
    )


if __name__ == "__main__":
    main()
