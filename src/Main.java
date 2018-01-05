import com.sun.java.swing.action.ExitAction;
import twitter4j.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Main extends JFrame{
    String searchWord = "リップル";
    int hours = 1;
    int wordPercent = 3;
    int tagPercent = 0;
    boolean isYesterday = false;
    JMenuBar menuBar;
    boolean isFirst = true;

    JTextField searchField, hourField, wordField, tagField;
    DefaultListModel tweetListModel, wordListModel, tagListModel;
    JList tweetList, wordList, tagList;
    JButton searchButton;
    JPanel pane;
    JLabel conditionLabel;
    GridBagLayout gridBagLayout;

    HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
    HashMap<String, Integer> tagMap = new HashMap<>();

    List<Map.Entry<String, Integer>> wordMapList;
    List<Map.Entry<String, Integer>> tagMapList;

    public static void main(String[] args) {
        UIManager.put("Label.font",new Font("Arial", Font.PLAIN, 20)); //Labelのフォント設定
        UIManager.put("List.font",new Font("Arial", Font.PLAIN, 20)); //Listのフォント設定
        JFrame w = new Main("Tweet Search System");
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setSize(1200,800);
        w.setVisible(true);
    }

    public  void startSearch(){
        int count = 0;
        int tweetCount = 100;
        final int YESTERDAY = 24;

        Morphological_Analysis analysis = new Morphological_Analysis();

        Twitter twitter = TwitterFactory.getSingleton();

        //初めてならリストを消さない（リストを初期化していない為）
        if (isFirst) {
            isFirst = false;
        }else {
            clearList();
        }

        //日付操作
        Date date = new Date();
        Date newlyDate = new Date();
        Date tweetDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        Calendar newlyCalender = Calendar.getInstance();
        calendar.setTime(date);
        newlyCalender.setTime(newlyDate);
//        calendar.add(Calendar.HOUR, -YESTERDAY);
        newlyCalender.add(Calendar.HOUR, -hours);
        date = calendar.getTime();
        newlyDate = newlyCalender.getTime();
        String dateString = simpleDateFormat.format(date) + "_JST";
        String newlyDateString = simpleDateFormat.format(newlyDate) + "_JST";

        Query query = new Query(searchWord);
//        if (isYesterday){
//            query.since(dateString);
//            System.out.println(calendar.getTime());
//        }else {
////            query.since(newlyDateString);
////            System.out.println(newlyCalender.getTime());
//        }
        query.setCount(tweetCount);
        QueryResult result = null;
        try {
            result = twitter.search(query);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        print(String.valueOf(result.getTweets().size()) + "件ヒット");
        for (Status status : result.getTweets()) {
            tweetDate = status.getCreatedAt();
            if (tweetDate.after(newlyDate)){
                count ++;
                String text = status.getText();
                text = analysis.analyze(text);
                String timeDiff = calTime(date, tweetDate);
                printTweet(text, timeDiff);
            }
        }
        wordMap = analysis.getWordMap();
        tagMap = analysis.getTagMap();
        wordMapList = new ArrayList<>(wordMap.entrySet());
        tagMapList = new ArrayList<>(tagMap.entrySet());
        Collections.sort(wordMapList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        Collections.sort(tagMapList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
//        System.out.println("All");
//        System.out.println("単語");
////        analysis.printAllWordMap();
//        System.out.println();
//        System.out.println("ハッシュタグ");
//        analysis.printAllTagMap();
        System.out.println("単語：" + wordPercent + "%以上");
        printPercentWordMap(result.getTweets().size() * wordPercent /100);
        System.out.println("ハッシュタグ：" + tagPercent + "%以上");
        printPercentTagMap(result.getTweets().size() * tagPercent /100);
//        System.out.println("\n直近のツイート数");
//        System.out.println("count : "+ count);
    }

    public String calTime(Date nowDate, Date tweetDate){
        String result;
        long nowTime = nowDate.getTime();
        long tweetTime = tweetDate.getTime();
        long secDiff = (nowTime - tweetTime) / 1000;
        if (secDiff < 60){
            result = secDiff + "秒前";
        }else if (secDiff < 3600){
            result = secDiff/60 + "分前";
        }else{
            result = secDiff/3600 + "時間" + secDiff%3600 + "分前";
        }
        return result;
    }


    //コンストラクタを一応用意
    public Main(){

    }

    //GUI
    public Main(String title){
        super(title);

        pane = (JPanel)getContentPane();
        pane.setLayout(new BorderLayout());

        //メニューバー
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar( menuBar );
        JMenu fileMenu = new JMenu( "終了" );
        menuBar.add( fileMenu );
        JMenuItem item;
        item = new JMenuItem( new ExitAction() );
        fileMenu.add( item );

        //検索条件
        JPanel header = new JPanel();
        JPanel conditionsPanel = new JPanel();
        header.setLayout(new BorderLayout());

        conditionsPanel.setLayout(new BoxLayout(conditionsPanel, BoxLayout.Y_AXIS));
        conditionLabel = new JLabel("<html><u>検索条件</u><html>");
        conditionsPanel.add(conditionLabel);
        conditionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.X_AXIS));
        JLabel timeLabel1, timeLabel2;
        timeLabel1 = new JLabel("時間：");
        hourField = new JTextField(String.valueOf(hours));
        timeLabel2 = new JLabel("時間以内");
        hourField.setMaximumSize(new Dimension(40,20));
        timePanel.add(timeLabel1);
        timePanel.add(hourField);
        timePanel.add(timeLabel2);
        timePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        conditionsPanel.add(timePanel);

        JPanel ratePanel = new JPanel();
        ratePanel.setLayout(new BoxLayout(ratePanel, BoxLayout.X_AXIS));
        JLabel rateLabel1, rateLabel2, rateLabel3;
        rateLabel1 = new JLabel("出現率：単語");
        wordField = new JTextField(String.valueOf(wordPercent));
        rateLabel2 = new JLabel("％　＃");
        tagField = new JTextField(String.valueOf(tagPercent));
        rateLabel3 = new JLabel("％以上");
        wordField.setMaximumSize(new Dimension(40,20));
        tagField.setMaximumSize(new Dimension(40,20));
        ratePanel.add(rateLabel1);
        ratePanel.add(wordField);
        ratePanel.add(rateLabel2);
        ratePanel.add(tagField);
        ratePanel.add(rateLabel3);
        ratePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        conditionsPanel.add(ratePanel);
        header.add(conditionsPanel, BorderLayout.NORTH);


        //検索欄
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchField = new JTextField(searchWord);
        searchField.setBorder(new TitledBorder("検索欄"));
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchButton = new JButton("検索");
        searchButton.addActionListener( new SearchTweet());
        searchPanel.add(searchButton, BorderLayout.EAST);
        header.add(searchPanel, BorderLayout.SOUTH);
        pane.add(header, BorderLayout.NORTH);

        //検索結果
        JPanel resultPanel = new JPanel();
//        gridBagLayout = new GridBagLayout();
        resultPanel.setLayout(new GridLayout(1,2));
        tweetListModel = new DefaultListModel();
        wordListModel = new DefaultListModel();
        tagListModel = new DefaultListModel();
        tweetList = new JList( tweetListModel );
        wordList = new JList(wordListModel);
        tagList = new JList(tagListModel);
        JScrollPane tweetScList = new JScrollPane( tweetList );
        JScrollPane wordScList = new JScrollPane(wordList);
        JScrollPane tagScList = new JScrollPane(tagList);
        tweetScList.setBorder( new TitledBorder( "ツイート一覧" ) );
        wordScList.setBorder(new TitledBorder("出現率？％以上の単語"));
        tagScList.setBorder(new TitledBorder("出現率？％以上のタグ"));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(2,1));
        rightPanel.add(wordScList);
        rightPanel.add(tagScList);
        resultPanel.add(tweetScList);
        resultPanel.add(rightPanel);
//        addList(tweetScList, resultPanel, 0,0,1,2);
//        addList(wordScList, resultPanel, 1,0,1,1);
//        addList(tagScList, resultPanel, 1,1,1,1);
        pane.add(resultPanel);

    }

    public void print(String string) {
        System.out.println(string);
        tweetListModel.addElement(string);
    }

    public void printTweet(String string, String timeDiff) {
        System.out.println(timeDiff + " " + string);
        System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
        tweetListModel.addElement(timeDiff + " " + string);
    }

    public void printWord(String string) {
        System.out.println(string);
        wordListModel.addElement(string);
    }

    public void printTag(String string) {
        System.out.println(string);
        tagListModel.addElement(string);
    }

    public void printPercentWordMap(int num) {
        for (Map.Entry<String, Integer> entry : wordMapList) {
            if (entry.getValue() > num) {
                printWord(entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    public void printPercentTagMap(int num) {
        for (Map.Entry<String, Integer> entry : tagMapList) {
            if (entry.getValue() > num) {
                printTag(entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    public void clearList(){
        tweetListModel.clear();
        wordListModel.clear();
        tagListModel.clear();
        wordMapList.clear();
        tagMapList.clear();
    }

    class SearchTweet implements ActionListener{
        public void actionPerformed(ActionEvent event){
            searchWord = searchField.getText();
            try {
                hours = Integer.parseInt(hourField.getText());
                wordPercent = Integer.parseInt(wordField.getText());
                tagPercent = Integer.parseInt(tagField.getText());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
            if (searchWord.equals("")){
                print("検索語を入れて下さい");
            }else {
                System.out.println(searchWord + "で検索");
            }
            startSearch();
        }
    }

    class ExitAction extends AbstractAction{
        ExitAction(){
            putValue(Action.NAME, "終了");
            putValue(Action.SHORT_DESCRIPTION, "終了");
        }
        public void actionPerformed(ActionEvent event){
            int ans = JOptionPane.showConfirmDialog(pane,"プログラムを終了しますか？","プログラム終了", JOptionPane.OK_CANCEL_OPTION);
            if (ans == 0){
                System.exit(0);
            }
        }
    }


//    public void addList(JScrollPane scrollPane,JPanel resultPanel, int x, int y, int w, int h){
//        GridBagConstraints gridBagConstraints = new GridBagConstraints();
//        gridBagConstraints.fill = GridBagConstraints.BOTH;
//        gridBagConstraints.gridx = x;
//        gridBagConstraints.gridy = y;
//        gridBagConstraints.gridheight = h;
//        gridBagConstraints.gridwidth = w;
////        gridBagConstraints.insets = new Insets(0,0,0,0);
//        gridBagLayout.setConstraints(scrollPane, gridBagConstraints);
//        resultPanel.add(scrollPane);
//    }
}
