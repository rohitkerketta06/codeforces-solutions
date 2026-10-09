import java.util.*;
public class AHelpfulMaths{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        String[] number = s.split("\\+");
        Arrays.sort(number);
        System.out.println(String.join("+",number));
    }
}