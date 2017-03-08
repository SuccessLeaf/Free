package QuestionClassInstance;
import java.util.*;
import java.io.*;

public class Food {
    static int allCallorie;
    static HashMap<String, Integer> map = new HashMap<String, Integer>();
    public static void main(String[] args){
        Food food = new Food();
        while(true){
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("[0]:カロリーを入力する");
                System.out.println("[1]:カロリーの合計を見る");
                int input = Integer.parseInt(reader.readLine());

                if (input == 0) {
                    food.input();           // カロリーの入力
                } else if (input == 1) {
                    showAllCalorie(); // カロリーの合計を表示
                    System.exit(0);
                }
            }catch (IOException e){
                e.printStackTrace();
            }catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public void input(){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String name;
            int calorie;
            System.out.println("料理名を入力して下さい");
            name = reader.readLine();
            System.out.println("カロリーを入力して下さい");
            calorie = Integer.parseInt(reader.readLine());
            allCallorie += calorie;
            map.put(name, calorie);
            //mapに入力した料理名とカロリーのセットを追加する
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    public static void showAllCalorie(){
        System.out.println("----------------------------------------------");
        for (Map.Entry<String, Integer> entry : map.entrySet()){
            System.out.println(entry.getKey() + "：" + entry.getValue());
            //この処理により料理名とカロリーのセットを出力する
        }
        System.out.println("----------------------------------------------");
        System.out.println("カロリー合計：" + allCallorie);
    }
}
