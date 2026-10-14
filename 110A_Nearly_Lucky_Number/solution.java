import java.util.*;
public class ANearlyLuckyNumber {
    public static void main(String[] args){
        Scanner sc = new Scanner (System.in);
        String n = sc.next();
        int cnt = 0;
        for (int i=0; i<n.length(); i++){
            char digit = n.charAt(i);
            if (digit == '4' || digit == '7'){
                cnt++;
            }
        }
        if (cnt == 4 || cnt ==7){
            System.out.println("YES");
        }else{
            System.out.println("NO");
        }
    }
    
}
