import java.util.*;
public class AWayTooLongWords {
    public static void main(String[] args){
        Scanner sc = new Scanner (System.in);
        int n = sc.nextInt();
        for (int i=0; i<n ; i++){
            String s = sc.next();
            int lengthOfString = s.length();
            if (lengthOfString>10){
                System.out.println(""+s.charAt(0)+(lengthOfString-2)+s.charAt(lengthOfString-1));
            }else{
                System.out.println(s);
            }
        }
    }
}
