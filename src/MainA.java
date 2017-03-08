import java.util.*;
public class MainA {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        ArrayList<Integer> list = new ArrayList();
        String line;
        Boolean flg = true;
        for (;scan.hasNext();){
            line = scan.nextLine();
            flg = true;
            for (int j = 0; j < 20; j++) {
                list.add(Integer.valueOf(String.valueOf(line.charAt(j))));
            }
            for (;flg;) {
                flg = false;
                for (int i = 0; i < list.size() -1; i++) {
                    int j = i+1;
                    if (list.get(i) - list.get(j) == 1 || list.get(i) - list.get(j) == -1){
                        list.remove(i);
                        list.remove(i);
                        flg = true;
                        break;
                    }
                }
            }
            String num = "";
            for (int a : list){
                num += String.valueOf(a);
            }
            System.out.println(num);
            list.clear();
        }

    }
}