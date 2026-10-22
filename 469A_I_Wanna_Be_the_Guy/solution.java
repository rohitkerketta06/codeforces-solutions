
import java.util.*;

public class AIWannaBeTheGuy {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] levels = new int[n + 1];
        int p = sc.nextInt();
        for (int i = 0; i < p; i++) {
            int x = sc.nextInt();
            levels[x] = 1;
        }
        int q = sc.nextInt();
        for (int i = 0; i < q; i++) {
            int y = sc.nextInt();
            levels[y] = 1;
        }
        for (int i = 1; i <= n; i++) {
            if (levels[i] == 0) {
                System.out.println("Oh, my keyboard!");
                sc.close();
                return;
            }
        }
        System.out.println("I become the guy.");
    }
}
