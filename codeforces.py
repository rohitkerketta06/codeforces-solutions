import html
import json
import os
import re
import time
from pathlib import Path

import requests
from bs4 import BeautifulSoup

HANDLE = os.environ.get("CF_HANDLE", "Rohitkerketta484")
COUNT = int(os.environ.get("CF_COUNT", "100"))

ROOT = Path(__file__).resolve().parent
STATE_FILE = ROOT / "data" / "submissions.json"

API_URL = "https://codeforces.com/api/user.status"

session = requests.Session()
session.headers.update({
    "User-Agent": "Codeforces-GitHub-Sync/1.0"
})


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


def get_submissions():
    response = session.get(
        API_URL,
        params={"handle": HANDLE, "from": 1, "count": COUNT},
        timeout=30
    )
    response.raise_for_status()

    data = response.json()

    if data.get("status") != "OK":
        raise RuntimeError(
            f"Codeforces API error: {data.get('comment', 'unknown error')}"
        )

    return data["result"]


def safe_name(value):
    value = re.sub(r'[<>:"/\\|?*]', "", value)
    value = re.sub(r"\s+", "_", value.strip())
    value = re.sub(r"_+", "_", value)
    return value[:100].strip("_") or "unknown"


def get_extension(language):
    language = language.lower()

    if "java" in language:
        return ".java"
    if "kotlin" in language:
        return ".kt"
    if "python" in language:
        return ".py"
    if "gnu c++" in language or "g++" in language or "clang++" in language:
        return ".cpp"
    if "c++" in language:
        return ".cpp"
    if language.startswith("gnu c"):
        return ".c"
    if language.startswith("c ") or language == "c":
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


def get_source(contest_id, submission_id):
    url = (
        f"https://codeforces.com/contest/"
        f"{contest_id}/submission/{submission_id}"
    )

    response = session.get(url, timeout=30)
    response.raise_for_status()

    soup = BeautifulSoup(response.text, "html.parser")
    source = soup.find("pre", id="program-source-text")

    if source is None:
        raise RuntimeError(
            f"Could not find source code for submission {submission_id}. "
            f"Codeforces may have changed its page format."
        )

    # BeautifulSoup decodes HTML entities for us.
    return html.unescape(source.get_text())


def main():
    print(f"Checking Codeforces submissions for: {HANDLE}")

    state = load_state()
    processed = set(state.get("processed_submission_ids", []))

    submissions = get_submissions()

    # user.status normally returns newest submissions first.
    accepted = [
        s for s in submissions
        if s.get("verdict") == "OK"
        and s.get("contestId") is not None
    ]

    # Reverse so older submissions are processed first when building
    # the repository for the first time.
    accepted.reverse()

    added = 0

    for submission in accepted:
        submission_id = submission["id"]

        if str(submission_id) in processed:
            continue

        contest_id = submission["contestId"]
        index = submission["problem"]["index"]
        name = submission["problem"]["name"]
        language = submission.get("programmingLanguage", "Unknown")

        folder_name = safe_name(f"{contest_id}{index}_{name}")
        extension = get_extension(language)

        folder = ROOT / folder_name
        output_file = folder / f"solution{extension}"

        # Keep one solution per problem. Since submissions are processed
        # oldest -> newest, the first accepted solution wins.
        if folder.exists() and any(folder.iterdir()):
            print(f"Skipping existing problem: {folder_name}")
            processed.add(str(submission_id))
            continue

        print(
            f"Downloading #{submission_id}: "
            f"{contest_id}{index} - {name} [{language}]"
        )

        try:
            source = get_source(contest_id, submission_id)
        except Exception as exc:
            print(f"  ERROR: {exc}")
            # Do not mark it processed; the next run can retry it.
            continue

        folder.mkdir(parents=True, exist_ok=True)

        output_file.write_text(source, encoding="utf-8")

        metadata = {
            "submission_id": submission_id,
            "contest_id": contest_id,
            "problem_index": index,
            "problem_name": name,
            "language": language,
            "verdict": submission.get("verdict"),
            "rating": submission["problem"].get("rating"),
            "tags": submission["problem"].get("tags", []),
        }

        (folder / "metadata.json").write_text(
            json.dumps(metadata, indent=2),
            encoding="utf-8"
        )

        processed.add(str(submission_id))
        added += 1

        # Be polite to Codeforces.
        time.sleep(1)

    state["processed_submission_ids"] = sorted(processed, key=int)
    save_state(state)

    print(f"Done. Added {added} new accepted solution(s).")


if __name__ == "__main__":
    main()
