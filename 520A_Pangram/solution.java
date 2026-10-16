import java.util.*;
public class APangram {
    public static void main(String[] args){
        Scanner sc = new Scanner (System.in);
        int n = sc.nextInt();
        String word = sc.next().toLowerCase();
        boolean letters[] = new boolean[26];
        int differentletter = 0;
        for (int i=0; i<n; i++){
            int position = word.charAt(i)-'a';
            if (!letters[position]){
                letters[position] = true;
                differentletter++;
            }
        }
        if (differentletter==26){
            System.out.println("YES");
        }else{
            System.out.println("NO");
        }
    }
    
}