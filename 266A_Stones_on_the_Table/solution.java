import java.util.*;
public class AStonesOnTheTable {
    public static void main(String[] args){
        Scanner sc = new Scanner (System.in);
        int n = sc.nextInt();
        String s = sc.next();
        int cnt = 0;
        for (int i=1; i<n; i++){
            if (s.charAt(i) == s.charAt(i-1)){
                cnt++;
            }
        }
        System.out.println(cnt);
    }
    
}
