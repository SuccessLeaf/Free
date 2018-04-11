package CodeIQ;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Yuto on 2017/07/15.
 */
public class Fibonacci {
    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);
        String line;
        ArrayList<Integer> list = new ArrayList<>();
        Fibonacci fibonacci = new Fibonacci();
        int[] array = new int[48];
        while (scan.hasNext()){ //読み込み
            line = scan.nextLine();
            list.add(Integer.valueOf(line));
        }

        for (int i = 0; i < array.length; i++){
            array[i] = fibonacci.fibonacci(i);
        }
        for (int i = 1; i < 100; i++){
            list.add(i);
        }


        for (int i = 0; i < list.size(); i++){

            int ans = list.get(i) % array.length;
//             if (ans % array.length == 0){
//                 System.out.println("-------------------");
//             }
//             System.out.print(i + 1 + " : " + main.fibonacci(i + 1) + ", ");
            System.out.println(array[ans]);

        }

    }

    int fibonacci(int n) {
        int temp1 = 1;
        int temp2 = 1;
        boolean flg = true;
        if (n <= 0){
            return 0;
        }else if (n <= 2){
            return 1;
        }
        for (int i = 1; i <= n-2; i++){
            if (flg){
                temp1 += temp2;
                temp1 %= 16;
                flg = false;
            }else{
                temp2 += temp1;
                temp2 %= 16;
                flg = true;
            }
        }


        if (flg){
            return temp2;
        }else{
            return temp1;
        }
    }
}
