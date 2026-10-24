import java.util.*;
public class ATranslation {
    public static void main(String[] args){
        Scanner sc = new Scanner (System.in);
        String s = sc.next();
        String t = sc.next();
        String reverse = new StringBuilder(s).reverse().toString();
        if (reverse.equals(t)){
            System.out.print("YES");
        }else{
            System.out.print("NO");
        }
    }
}
