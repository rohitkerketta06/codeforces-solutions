import java.util.*;

public class ASumOfRoundNumbers {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        while (t-- > 0) {
            int n = sc.nextInt();
            int place = 1;
            ArrayList<Integer> list = new ArrayList<>();

            while (n > 0) {
                int digit = n % 10;

                if (digit != 0) {
                    list.add(digit * place);
                }

                n /= 10;
                place = place * 10;
            }


            System.out.println(list.size());

            for (int num : list) {
                System.out.print(num + " ");
            }

            System.out.println();
        }
    }
}