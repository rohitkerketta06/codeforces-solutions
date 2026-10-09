import java.util.Scanner;

public class AElephant{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int base_answer = n/5;
        if (n%5==0){
            System.out.println(base_answer);
        }else{
            System.out.println(base_answer+1);
        }
    }
}