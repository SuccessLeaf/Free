import java.util.*;
public class Main {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int num;
        int count;
        int offset;
        for (;scan.hasNext();){
            count = 0;
            num = scan.nextInt();
            if (num % 2 != 0) {
                offset = 1999999 / num;
                for (int j = 3; j < 1999999; ) {
                    if (j % num == 0) {
                        count++;
                    }
//                    offset = num - (j % num);
//                    if (offset > 2){
//                         j += (offset/2)*2;
//                    }else {
//                        j += 2;
//                    }
                }
            }
            System.out.println(count);
        }
    }
}
