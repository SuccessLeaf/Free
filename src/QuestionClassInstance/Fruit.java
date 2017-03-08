package QuestionClassInstance;

/**
 * Created by Yuto on 2017/02/24.
 */
public class Fruit {
    static int sum;
    String name;
    int price;
    public static void main(String[] args){
        Fruit apple = new Fruit("apple", 120);
        Fruit orange = new Fruit("orange", 200);
        Fruit strawberry = new Fruit("strawberry", 60);
        //String型の名前と、int型の値段の引数を持たせた
        getSum();
    }

    public Fruit(String fruitName, int money){
        //String型の名前と、int型の値段の引数をインスタンス作成時に持たせる
        name = fruitName;
        price = money;
        sum += money;
    }

    public static void getSum(){
        System.out.println("合計の価格は" + sum + "円です");
    }
}
