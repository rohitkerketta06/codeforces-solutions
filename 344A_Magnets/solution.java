import java.util.*;
public class AMagnets {
    public static void main(String[] args){
        Scanner sc = new Scanner (System.in);
        int n = sc.nextInt();
        int groups = 1;
        String previous = sc.next();
        for (int i=1; i<n; i++){
            String current = sc.next();
            if (!current.equals(previous)){
                groups++;
            }
            previous=current;
        }
        System.out.print(groups);
    }
}
