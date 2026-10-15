import java.util.*;
public class ASum{
    public static void main(String[] args){
        Scanner sc = new Scanner (System.in);
        int t = sc.nextInt();
        for (int i=0; i<t; i++){
            int x = sc.nextInt();
            int y = sc.nextInt();
            int z = sc.nextInt();
            if (x+y==z || x+z==y || y+z==x){
                System.out.println("YES");
            }else {
                System.out.println("NO");
            }
        }
    }
}