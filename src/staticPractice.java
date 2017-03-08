/**
 * Created by Yuto on 2017/02/03.
 */
import java.util.*;
import java.io.*;
import java.util.Scanner.*;
public class staticPractice implements SampleInterface {
    static String hello = "Hello";
    static int num = 0;
    int count = 0;
    String world = "world";
    final String name = "otsuka";

    staticPractice(){
        num++;
        count++;
        Practice();
    }
    public static void main(String args[]) {
        staticPractice staticPractice = new staticPractice();
        staticPractice.HelloWorld();
        String str = "a";
        String string = str;
//        System.out.println(str);
        str = "b";
//        System.out.println(str);
//        System.out.println(string);
//       staticPractice p = new staticPractice();
//       System.out.print(p);
//       Practice();
//       staticPractice.Practice();
//       p.Reader();
    }

    @Override
    public void HelloWorld(){
        System.out.print("Hello!!!");
    }

    public  static void Practice(){
        System.out.println(staticPractice.hello);
        System.out.println(hello);
    }

    public void Sample(){

    }

    public void Reader(){
        Practice();
        Sample();
        try {
            System.out.println("値を入力して下さい");
            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(inputStreamReader);
            int string = inputStreamReader.read();
//            String str = br.readLine();
            System.out.println(string);
        }catch (IOException e){
        }
    }
}
