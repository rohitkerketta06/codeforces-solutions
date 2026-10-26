import java.util.*;
public class AHitTheLottery{
    public static void main(String[] args){
        Scanner sc = new Scanner (System.in);
        int n = sc.nextInt();
        int cnt =0; 
        cnt+=n/100;
        n%=100;

        cnt+=n/20;
        n%=20;

        cnt+=n/10;
        n%=10;

        cnt+=n/5;
        n%=5;

        cnt+=n;

        System.out.println(cnt);
    }
}