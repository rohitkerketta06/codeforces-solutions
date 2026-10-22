import java.util.*;
public class AAntonAndDanik {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String s = sc.next();
        int dcnt = 0;
        int acnt = 0;
        for (int i=0; i<s.length(); i++ ){
            if (s.charAt(i) == 'A'){
                acnt++;
            }else{
                dcnt++;
            }
        }
        if (dcnt>acnt){
            System.out.println("Danik");
        }
        else if (dcnt==acnt){
            System.out.println("Friendship");
        }else{
            System.out.println("Anton");
        }
    }
    
}
