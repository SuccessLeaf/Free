/**
 * Created by Yuto on 2017/02/03.
 */
public class staticPractice {
    static String hello = "Hello";
    static int num = 0;
    int count = 0;
    String world = "world";

    staticPractice(){
        num++;
        count++;
    }
    public static void main(String args[]) {
        staticPractice practice = new staticPractice();
        staticPractice practice2 = new staticPractice();
        staticPractice practice3 = new staticPractice();
        System.out.println(practice.count);
        System.out.println(practice2.count);
        System.out.println(practice3.count);
        System.out.println(num);
        practice.Practice();
    }

    public void Practice(){
        System.out.println(staticPractice.hello);
        System.out.println(world);
    }
}
