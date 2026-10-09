import java.util.*;
public class ABoyOrGirl{
    public static void main(String[] args){
        Scanner sc = new Scanner (System.in);
        String username = sc.next();
        HashSet<Character> distinSet = new HashSet<>();
        for (char c : username.toCharArray()){
            distinSet.add(c);
        }
        if (distinSet.size()%2==0){
            System.out.println("CHAT WITH HER!");
        }else{
            System.out.println("IGNORE HIM!");
        }

    }
}