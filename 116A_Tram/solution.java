import java.util.*;
public class ATram {
    public static void main(String[] args){
        Scanner sc = new Scanner (System.in);
        int stops = sc.nextInt();
        int peopleinside = 0;
        int maxpeople = 0;
        for (int i=0; i<stops; i++){
            int exit = sc.nextInt();
            int enter = sc.nextInt();
            peopleinside = peopleinside-exit+enter;
            maxpeople = Math.max(maxpeople,peopleinside);
        }
        System.out.println(maxpeople);
    }
}
