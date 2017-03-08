import java.util.*;
class Sample{
    public static void main(String[] args) {
try {
    Sample sample = new Sample();
    sample.input();
}catch (InputMismatchException e){
    e.printStackTrace();
}
    }

 public void input() throws InputMismatchException {
     System.out.println("数字を入力して下さい");
     int num = new Scanner(System.in).nextInt();
     System.out.println(num);
 }
}


