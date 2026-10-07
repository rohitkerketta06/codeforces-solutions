import java.util.*;
public class ABit{
    public static void main(String[] args){
        Scanner sc = new Scanner (System.in);
        int n = sc.nextInt();
        int count = 0;
        for (int i=0; i<n; i++){
            String s = sc.next();
            if (s.equals("++X") || s.equals("X++")){
                count++;
            }else if (s.equals("--X" )|| s.equals("X--")){
                count--;
            }
        }
        System.out.println(count);
    }
}