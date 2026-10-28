import java.util.*;
public class APresents {
    public static void main(String[] args){
        Scanner sc = new Scanner (System.in);
        int n = sc.nextInt();
        int gifts[] = new int[n];
        for (int i=0; i<n; i++){
            int friend = sc.nextInt();
            gifts[friend-1] = i+1;
        }
        for (int i=0; i<n; i++){
            System.out.print(gifts[i] + " ");
        }
    }
}
