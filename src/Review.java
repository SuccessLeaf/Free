import java.util.*;

public class Review {
    public static void main (String[] args) {
        ArrayList<HashMap<String, String>> posts = new ArrayList<HashMap<String, String>>();
        boolean review = true;
        while(review) {
            System.out.println("レビュー数： " + posts.size());
            System.out.println("[0]レビューを書く");
            System.out.println("[1]レビューを読む");
            System.out.println("[2]アプリを終了する");
            int input = new Scanner ( System.in ).nextInt ();
            if (input == 0) {
                postReview(posts);
            } else if (input == 1) {
                readReviews(posts);
            } else if (input == 2) {
                System.out.println("プログラムを終了します！");
                System.exit(0);
            } else {
//                review = exception();3
                exception();
            }
        }
    }
    public static void postReview(ArrayList<HashMap<String, String>> aPosts) {
        String line = "----------------------------------------------";
        HashMap<String, String> post = new HashMap<String, String>();
        System.out.println("ジャンルを入力してください：");
        String genre = new Scanner ( System.in ).nextLine ();
        System.out.println("タイトルを入力してください：");
        String title = new Scanner ( System.in ).nextLine ();
        System.out.println("感想を入力してください：");
        String review = new Scanner ( System.in ).nextLine ();
        post.put("genre", genre);
        post.put("title", title);
        post.put("review", review);

        System.out.println("ジャンル :" + post.get("genre")  +  "\n" + line);
        System.out.println("タイトル :" + post.get("title")  +  "\n" + line);
        System.out.println("感想 　　:" + post.get("review") + "\n" +  line );

        aPosts.add(post);
    }
    public static void readReviews(ArrayList<HashMap<String, String>> aPosts) {
        String line = "----------------------------------------------";
        for (int i = 0; i < aPosts.size(); i++){
            System.out.println("[" +i + "]　タイトル :" + aPosts.get(i).get("title"));
        }
        System.out.println("見たいレビューを選択してください");
        int num = new Scanner ( System.in ).nextInt ();

        System.out.println("ジャンル  :" + aPosts.get(num).get("genre") + "\n" + line);
        System.out.println("タイトル  :" + aPosts.get(num).get("title") + "\n" + line);
        System.out.println("感想     :" + aPosts.get(num).get("review") + "\n" + line);
    }
    public static void exception(){
        System.out.println("これは無効な値です！");
//        return true;
    }
}