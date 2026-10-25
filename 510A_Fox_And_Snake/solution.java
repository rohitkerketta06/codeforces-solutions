import java.util.*;
public class AFoxAndSnake {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int i=1; i<=n; i++){
            if (i%2==1){
                for (int j=0; j<m; j++){
                    sb.append('#');
                }
            }else{
                int pos;
                if ((i/2)%2==1){
                    pos=m-1;
                }else{
                    pos=0;
                }

                for (int j=0; j<m; j++){
                    if (j==pos){
                        sb.append('#');
                    }else{
                        sb.append('.');
                    }
                }
            }
            sb.append('\n');
        }
        System.out.println(sb);
    }
}
