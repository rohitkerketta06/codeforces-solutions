import java.util.*;
public class APetyaAndStrings{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s1=sc.next().toLowerCase();
        String s2=sc.next().toLowerCase();
        int result = s1.compareTo(s2);
        if (result<0){
            System.out.println(-1);
        }else if (result>0){
            System.out.println(1);
        }else{
            System.out.println(0);
        }
    }
}