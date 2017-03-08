package QuestionClassInstance;

/**
 * Created by Yuto on 2017/02/24.
 */
public class Dog {
    static String type = "犬";
    String name = "マロン";
    String dogType = "トイプードル";
    public static void main(String[] args){
        Dog dog = new Dog();
        say(); //クラスメソッドを呼び出す
        dog.sayType(); //インスタンスメソッドを呼び出す
        dog.selfIntroduction();
    }

    public static void say(){ //クラスメソッド
        System.out.println("ワンワン");
    }

    public void sayType(){ //インスタンスメソッド
        System.out.println(type);
    }

    public void selfIntroduction(){ //インスタンスメソッド
        System.out.println(name);
        System.out.println(dogType);
    }

}
