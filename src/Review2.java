import java.util.*;

public class Review2{
    String title;
    String genre;
    String impression;
    static int reviewCount = 0;
    public  static void main(String[] args){
        while(true){
            // メニューの表示
            System.out.println("書いたレビューの数" + Review2.getReviewCount());
            System.out.println("[0]レビューを書く/読む");
            System.out.println("[1]アプリを終了");
            int input = new Scanner(System.in).nextInt();

            if(input == 0){    // レビューを書く
                Review2 review = new Review2();
                review.showReview();
            }else if(input == 1){ // アプリを終了
                System.exit(0);
            }else{             // その他の入力
                System.out.println("入力された値は無効な値です");
            }
        }
    }

    public Review2(){
        System.out.println("タイトルを入力してください");
        title = new Scanner ( System.in ).nextLine ();
        System.out.println("ジャンルを入力してください");
        genre = new Scanner ( System.in ).nextLine ();
        System.out.println("感想を入力してください");
        impression = new Scanner ( System.in ).nextLine ();
        reviewCount = reviewCount++;
    }

    public static int getReviewCount(){
        return reviewCount;
    }

    public void showReview(){
        String line = "----------------------------------------------";
        System.out.println("ジャンル : " + genre + "\n" + line);
        System.out.println("タイトル : " + title + "\n" + line);
        System.out.println("感想 :" + impression + "\n" + line);
    }
}
