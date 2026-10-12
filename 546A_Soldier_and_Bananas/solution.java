import java.util.*;
public class ASoldierAndBananas {
    public static void main(String[] args){
        Scanner sc = new Scanner (System.in);
        int k = sc.nextInt();
        int n = sc.nextInt();
        int w = sc.nextInt();
        int totalcost = k*w*(w+1)/2;
        int borrow = totalcost - n;
        if (borrow<0){
            borrow=0;
        }
        System.out.println(borrow);
    }
    
}
