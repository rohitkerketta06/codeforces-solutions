import java.util.*;
public class APoliceRecruits{
    public static void main(String[] args){
        Scanner sc = new Scanner (System.in);
        int n = sc.nextInt();
        int officers = 0;
        int untreated = 0;
        for (int i=0 ; i<n; i++){
            int x =  sc.nextInt();
            if (x>0){
                officers+=x;
            }else if (officers>0){
                officers--;
            }else{
                untreated++;
            }
        }
        System.out.println(untreated);
    }
}