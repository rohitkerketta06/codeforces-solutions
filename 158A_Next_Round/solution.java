import java.util.*;
public class ANextRound{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int score[] = new int[n];
        for (int i=0; i<n; i++){
            score[i] = sc.nextInt();
        }
        int target_score = score[k-1];
        int count = 0;
        for (int i=0; i<n; i++){
            if (score[i]>=target_score && score[i]>0){
                count++;
            }
        }
        System.out.println(count);
    }
}