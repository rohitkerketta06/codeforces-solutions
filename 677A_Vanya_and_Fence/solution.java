import java.util.*;
public class AVanyaAndFence{
    public static void main(String[] args){
        Scanner sc = new Scanner (System.in);
        int n = sc.nextInt();
        int h = sc.nextInt();
        int width = 0;
        for (int i=0; i<n; i++){
            int x = sc.nextInt();
            if (x<=h){
                width+=1;
            }else{
                width+=2;
            }
        }
        System.out.println(width);
    }
}