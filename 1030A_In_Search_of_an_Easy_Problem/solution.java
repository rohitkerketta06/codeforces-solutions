import java.util.*;
public class AInSearchOfAnEasyProblem {
    public static void main(String[] args){
        Scanner sc = new Scanner (System.in);
        int n = sc.nextInt();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0; i<n; i++){
            int x = sc.nextInt();
            list.add(x);
        }
        if (list.contains(1)){
            System.out.println("HARD");
        }else{
            System.out.println("easy");
        }
    }
}
