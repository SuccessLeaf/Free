import java.util.ArrayList;
import java.util.Collections;

//must
// TODO: 2018/04/11 移動の部分のメソッド工夫
// TODO: 2018/04/11 プレイヤーが最短距離で動く様にする
// TODO: 2018/04/11 コメント増やす
// TODO: 2018/04/11 不要なコメントアウト消す
// TODO: 2018/04/11 クラス変数どうするか？

//need
// TODO: 2018/04/11 ぶつかって良いので敵が動く様にする

public class Level1 {
    private class PlayerEnemy{
        int x;
        int y;
        //プレイヤーかどうかを判断する変数で、これがtrueのオブジェクトは移動トラッキングする
        boolean isPlayer;
        //前回の位置を保存するかどうかの変数
        boolean isEnemeyCDE;

        public PlayerEnemy(){
            isPlayer = false;
            isEnemeyCDE = false;
        }
    }

    private class Node{
        //この変数要らなそう
        int x;
        int y;
        Map map;

        int cost;
        int enemy;
        int heuristicCost;
        int score;
        Node up;
        Node down;
        Node left;
        Node right;
        boolean isStart;
        boolean isGoal;
        boolean isOpen;
        boolean isClosed;
        boolean isPlayer;
        boolean isEnemy;
        boolean isItem;
        boolean isWall;
        public Node(){
            cost = 0;
            heuristicCost = 0;
            score = 0;
            isStart = false;
            isGoal = false;
            isOpen = false;
            isClosed = false;
            isPlayer = false;
            isEnemy = false;
            isItem = false;
            isWall = false;
        }
    }

    private class Map{
        int x;
        int y;
        public Map(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    private class MapInteger{
        int x;
        int y;
        int num;
        Map map;
        public MapInteger(int x, int y, int num){
            this.x = x;
            this.y = y;
            this.num = num;
            this.map = new Map(x, y);
        }
    }

    //クラス変数

    //マップのサイズ
    final int mapX = 11;
    final int mapY = 7;

    //スタートとゴールの位置
    final Map start = new Map(1,5);

    final Map goal = new Map(9,1);

    //マップの形状
    Node[][] nodes = new Node[mapX][mapY];

    //map[x][y]が動けるかどうか
    Boolean[][] map = new Boolean[getMapX()][getMapY()];

    //敵Eの状態を管理する為の変数
    int count = 0;

    //移動ログ
    String moveLog;

    //制限時間
    int time = 50;

    //直近で目指すべきゴール
    Map itemGoal;

    //目指すべきゴールかあるかどうか
    boolean isItemGoal = false;

    //プレイヤーのオブジェクト
    PlayerEnemy player = new PlayerEnemy();

    //敵キャラのオブジェクト
    //引数0がAであり、引数1がBである
    PlayerEnemy[] enemies = new PlayerEnemy[2];
    final int A = 0;
    final int B = 1;

    //方向の定数
    final int UP = 0;
    final int DOWN = 1;
    final int RIGHT = 2;
    final int LEFT = 3;

    //itemの格納
    ArrayList<Map> itemList = new ArrayList();

    //直近のゴール（アイテムorゴール）したかどうか
    boolean isGoal = false;

    //まだアイテムがあるか
    boolean isItem = true;

    //ゴール地点までたどり着いてクリアしたかどうか
    boolean isEnd = false;

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public int getCount() {
        return count;
    }

    public Boolean[][] getMap() {
        return map;
    }

    public String getMoveLog() {
        return moveLog;
    }

    //プレイヤーの移動を記録するメソッド
    //動いたアルファベットをここにString型で送る
    public void addMoveLog(String moveLog) {
        this.moveLog += moveLog;
    }

    // TODO: 2018/04/04 コメントをもっと書いていく
    // TODO: 2018/04/06 getter setterをしっかりやる
    public static void main(String[] args) {
        Level1 level1 = new Level1();
        level1.initialize();
        level1.drawMap();
        while (!level1.isEnd){
            level1.play();
        }
        level1.gameEnd();


    }

    public void updateMap(){
        //mapの状況を更新
        Node node;
        for (int i = 0; i < getMapY(); i++) {
            for (int j = 0; j < getMapX(); j++) {
                node = nodes[j][i];
                node.isEnemy = false;
                node.isPlayer = false;
            }
        }

        nodes[player.x][player.y].isPlayer = true;
        nodes[enemies[A].x][enemies[A].y].enemy = A;
        nodes[enemies[B].x][enemies[B].y].enemy = B;
        nodes[enemies[A].x][enemies[A].y].isEnemy = true;
        nodes[enemies[B].x][enemies[B].y].isEnemy = true;



//        //isItemがfalseになっているのものがあればitemListから削除
//        int deleteArg = 999;
//        for (int i = 0; i < itemList.size(); i++){
//            if (!nodes[itemList.get(i).x][itemList.get(i).y].isItem){
//                deleteArg = i;
//                break;
//            }
//        }
//        itemList.remove(deleteArg);
    }

    public void drawMap(){
        updateMap();
        Node node;
        for (int i = 0; i < getMapY(); i++) {
            for (int j = 0; j < getMapX(); j++) {
                node = nodes[j][i];
                if (node.isEnemy) {
                    if (node.enemy == 0) {
                        System.out.print("A");
                    } else if (node.enemy == 1) {
                        System.out.print("B");
                    }
                }else if (node.isPlayer){
                    System.out.print("P");
                }else if (node.isWall){
                    System.out.print("#");
                }else  if (node.isItem){
                    System.out.print("o");
                }else if (node.isStart){
                    System.out.print("S");
                }else if (node.isGoal){
                    System.out.print("G");
                }else{
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    //初期化するためのメソッド
    public void initialize(){
        for (int i = 0; i < getMapX(); i++){
            for (int j = 0; j < getMapY(); j++){
                map[i][j] = new Boolean(true);
                nodes[i][j] = new Node();
                nodes[i][j].x = i;
                nodes[i][j].y = j;
                nodes[i][j].map = new Map(i,j);
                if (i == 0 || i == getMapX()-1 || j == 0 || j == getMapY()-1){
                    nodes[i][j].isWall = true;
                }
            }
        }

        for (int i = 0; i < getMapX(); i++){
            for (int j = 0; j < getMapY(); j++){
                if (i != 0 && i != getMapX()-1 && j != 0 && j != getMapY()-1){
//                    System.out.println("node : (" + nodes[i][j].x + ", " + nodes[i][j].y + ")");
                    nodes[i][j].up = nodes[i][j-1];
//                    System.out.println("node up : (" + nodes[i][j].up.x + ", " + nodes[i][j].up.y + ")");
                    nodes[i][j].down = nodes[i][j+1];
//                    System.out.println("node down : (" + nodes[i][j].down.x + ", " + nodes[i][j].down.y + ")");
                    nodes[i][j].right = nodes[i+1][j];
                    nodes[i][j].left = nodes[i-1][j];
                }
            }
        }

        for (int i = 0; i < 2; i++){
            enemies[i] = new PlayerEnemy();
        }

        player.x = 1;
        player.y = 5;

        enemies[A].x = 4;
        enemies[A].y = 1;

        enemies[B].x = 6;
        enemies[B].y = 1;

        itemList.add(new Map(1,1));
        itemList.add(new Map(4,3));
        itemList.add(new Map(6,3));
        itemList.add(new Map(5,5));
        itemList.add(new Map(9,5));

        for (Map map : itemList){
            nodes[map.x][map.y].isItem = true;
        }

        nodes[start.x][start.y].isStart = true;
        nodes[goal.x][goal.y].isGoal = true;

        nodes[5][1].isWall = true;
        nodes[2][2].isWall = true;
        nodes[3][2].isWall = true;
        nodes[7][2].isWall = true;
        nodes[8][2].isWall = true;
        nodes[2][3].isWall = true;
        nodes[5][3].isWall = true;
        nodes[2][4].isWall = true;
        nodes[4][4].isWall = true;
        nodes[4][4].isWall = true;
        nodes[5][4].isWall = true;
        nodes[6][4].isWall = true;
        nodes[8][4].isWall = true;

        player.isPlayer  = true;

//        for (int i = time; i >= 0; i--) {
//            play();
//        }
    }

    //1ターンの挙動
    public void play(){
        movePlayer();
        System.out.println(getMoveLog());
//        moveA(player, enemies[A]);
//        moveB(player, enemies[B]);
    }

    public ArrayList<Map> searchRoute(Map start, Map goal){
        start = new Map(start.x,start.y);
        goal = new Map(goal.x, goal.y);
        //プレイヤーの通った通路
        ArrayList<Map> mapList = new ArrayList();
        //A*アルゴリズムで最短経路検索
        aStar(start, goal, mapList);
        //ノードに書き込まれたコストなどの情報をリセットする
        clearCost();
        //余計な要素をそぎ落とす
        Map pos = goal;
        boolean isSame = false;
        ArrayList<Integer> deleteArg = new ArrayList<>();
        for (int i = 0; i < mapList.size(); i++) {
            Map map = mapList.get(i);
            if (!isSame){
//                    System.out.println(("(" + map.x + ", " + map.y + ")" + " (" + pos.x + ", " + pos.y + ")"));
                if (map.x == pos.x && map.y == pos.y){
                    isSame = true;
                }else {
                    deleteArg.add(i);
                }
            }else {
                int dx = Math.abs(map.x - pos.x);
                int dy = Math.abs(map.y - pos.y);
                if (dx + dy == 1) {
                    pos.x = map.x;
                    pos.y = map.y;
                } else {
                    deleteArg.add(i);
                }
                //スタートと同じ値でも削除
                if (start.x == map.x && start.y == map.y){
                    deleteArg.add(i);
                }
            }
        }

        int i = 0;
        for (int num : deleteArg){
            num -= i;
            mapList.remove(num);
            i++;
        }

        Collections.reverse(mapList);

        System.out.println();

        return mapList;
    }

    //最短経路のA*アルゴリズムを実装したメソッド
    public ArrayList<Map> aStar(Map start, Map goal, ArrayList<Map> mapList) {

        updateMap();
        if (start.x == goal.x && start.y == goal.y) {
            isGoal = true;
        }
        if (!isGoal){
//            System.out.println("now : (" + start.x + ", " + start.y + ")");
            int dx = Math.abs(start.x - goal.x);
            int dy = Math.abs(start.y - goal.y);
            Node node = nodes[start.x][start.y];
    //        System.out.println("now : (" + node.x + ", " + node.y + ")");
            node.isOpen = true;
            node.heuristicCost = dx + dy;
            node.score = node.heuristicCost + node.cost;
    //        System.out.println("node up : (" + node.up.x + ", " + node.up.y + ")");
            if (!node.up.isWall && !node.up.isEnemy) {
                node.up.isOpen = true;
                calCost(node.up.map, goal, node.cost++);
            }
    //        System.out.println("node down : (" + node.down.x + ", " + node.down.y + ")");
            if (!node.down.isWall && !node.down.isEnemy) {
                node.down.isOpen = true;
                calCost(node.down.map, goal, node.cost++);
            }
            if (!node.right.isWall && !node.right.isEnemy) {
                node.right.isOpen = true;
                calCost(node.right.map, goal, node.cost++);
            }
            if (!node.left.isWall && !node.left.isEnemy) {
                node.left.isOpen = true;
                calCost(node.left.map, goal, node.cost++);
            }
            node.isClosed = true;

            int minScore = 999;
            ArrayList<Map> nodeList = new ArrayList<>();

            for (int i = 0; i < getMapX(); i++) {
                for (int j = 0; j < getMapY(); j++) {
                    node = nodes[i][j];
                    if (node.isOpen && !node.isClosed) {
                        if (node.score < minScore) {
                            minScore = node.score;
                            nodeList.clear();
                            nodeList.add(new Map(i, j));
                        } else if (node.score == minScore) {
                            nodeList.add(new Map(i, j));
                        }
                    }
                }
            }

            if (nodeList.size() == 1) {
                aStar(nodeList.get(0), goal, mapList);
            } else if (nodeList.size() > 1) {
                //実スコア比較
                int minCost = 999;
                ArrayList<Map> costList = new ArrayList<>();
                for (Map map : nodeList) {
                    node = nodes[map.x][map.y];
                    if (node.cost < minCost) {
                        minCost = node.cost;
                        costList.clear();
                        costList.add(map);
                    } else if (node.cost == minCost) {
                        costList.add(map);
                    }
                }
                for (Map map : costList) {
                    aStar(map, goal, mapList);
                }
            }
        }
        if (isGoal){
            mapList.add(start);
//            System.out.println("route : (" + start.x + ", " + start.y + ")" );
            return mapList;
        }
        return null;
    }

    //ノードのコスト計算
    public void calCost(Map start, Map goal, int cost){
        int dx = Math.abs(start.x - goal.x);
        int dy = Math.abs(start.y - goal.y);
        Node node = nodes[start.x][start.y];
        node.heuristicCost  =  dx + dy;
        node.score = node.heuristicCost + cost;
    }

    //計算したコストをリセットする
    public void clearCost(){
        isGoal = false;
        for (int i = 0; i < getMapX(); i++) {
            for (int j = 0; j < getMapY(); j++) {
                Node node = nodes[i][j];
                node.isOpen = false;
                node.isClosed = false;
                node.cost = 0;
                node.heuristicCost = 0;
                node.score = 0;
            }
        }
    }

    //一番近いアイテムを探して、そのアイテムに近づく為に動く方向とアイテムの引数を返す
    public int[] nearestItem(){
        int distance;
        int bestArg = 100;
        int minDistance = 999;
        ArrayList<Map> tempList;
        ArrayList<Map> bestList = new ArrayList<>();
        System.out.println("isItem : " + isItem);
        if (isItem) {
            for (int i = 0; i < itemList.size(); i++) {
                Map goal = itemList.get(i);
                tempList = searchRoute(new Map(player.x, player.y), new Map(goal.x, goal.y));
//                for (Map map : tempList){
//                    System.out.println("(" + map.x + ", " + map.y + ")");
//                }
                distance = tempList.size();

//            System.out.println("distance : " + distance + " (" +itemList.get(i).x + ", " +  itemList.get(i).y + ")");
                if (distance < minDistance) {
                    System.out.println("dis : " + distance + ", min : " + minDistance);
                    minDistance = distance;
                    bestArg = i;
//                    bestList = tempList;
                    bestList.clear();
                    for (Map map : tempList){
                        bestList.add(map);
                    }
                }

//                for (Map map : tempList){
//                    System.out.println("temp (" + map.x + ", " + map.y + ")");
//                }

            }

            System.out.println("Nearest item is : (" + itemList.get(bestArg).x + ", " + itemList.get(bestArg).y + ")");

        }else {
            bestList = searchRoute(new Map(player.x, player.y), goal);
            System.out.println("Goal is : (" + goal.x + ", " + goal.y + ")");
        }
//        for (Map map : bestList){
//            System.out.println("best (" + map.x + ", " + map.y + ")");
//        }

        //どの方向に動くのか計算
        int direction = -1;
        int dx = player.x - bestList.get(0).x;
        int dy = player.y - bestList.get(0).y;
        if (dy == 1){
            direction = UP;
        }else if (dy == -1){
            direction = DOWN;
        }else if (dx == -1){
            direction = RIGHT;
        }else if (dx == 1){
            direction = LEFT;
        }

        int[] result = {direction, bestArg};

        return result;
    }

    //プレイヤーの挙動
    public void movePlayer(){
        // ダイクストラで最短経路問題を解決したいが、動的にマップが変わる（敵が動く）
        // 巡回セールスマン問題ではないのか？とりあえず近くのアイテムをゴールとして、毎回ゴールを延長していく。
        // A*アルゴリズムを採用
        // Tとりあえず実装
        // 敵が来たら止まって待機でもいいか？
        int direction;
        int itemListArg;

        int[] returnValue = nearestItem();
        direction = returnValue[0];
        itemListArg = returnValue[1];



//            itemGoal = itemList.get(itemListArg);
//            isItemGoal = true;


//            System.out.println("itemGoal : (" + itemGoal.x + ", " + itemGoal.y + ")");

//        clearCost();


//            mapList = searchRoute(new Map(player.x, player.y), itemGoal);

//        mapList = aStar(new Map(player.x, player.y), itemGoal);

//            for (Map map : mapList) {
//                System.out.println(map.x + ", " +  map.y);
//            }

//            for (Map map : itemList){
//                System.out.println("item (" + map.x + ", " + map.y + ")");
//            }





//            for (Map map : mapList){
//                System.out.println("map : (" + map.x + ", " + map.y + ")");
//                player.x = map.x;
//                player.y = map.y;
//                drawMap();
//                count++;
//            }

//        System.out.println("map : (" + directionMap.x + ", " + directionMap.y + ")");

        System.out.println(direction);
        switch (direction){
            case UP:
                moveUp(player);
                System.out.println("map : (" + player.x + ", " + (player.y + 1) + ")");
                break;
            case DOWN:
                moveDown(player);
                System.out.println("map : (" + player.x + ", " + (player.y - 1) + ")");
                break;
            case RIGHT:
                moveRight(player);
                System.out.println("map : (" + (player.x  + 1) + ", " + player.y + ")");
                break;
            case LEFT:
                moveLeft(player);
                System.out.println("map : (" + (player.x  - 1) + ", " + player.y + ")");
                break;
        }
        drawMap();
        count++;


//            System.out.println("isGoal " + nodes[9][1].isGoal);
//            System.out.println("!goal(" + goal.x + ", "+ goal.y + ")");

//            System.out.println("route : (" + map.x + ", " + map.y + ")");

        System.out.println();
        if (nodes[player.x][player.y].isItem){
            nodes[player.x][player.y].isItem = false;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("item Get");
            System.out.println("itemList contains : " + itemList.contains(new Map(player.x, player.y)) + " (" + player.x + ", " + player.y + ")");
            itemList.remove(itemListArg);
            System.out.println("Left item is " + itemList.size());
        }

        if (itemList.size() == 0){
            isItem = false;

            System.out.println("NO ITEM");
//            System.out.println("player (" + player.x + ", " + player.y + ") goal(" + goal.x + ", "+ goal.y + ")");
//                if (player.x == goal.x && player.y == goal.y){
            //goal.yがなぜか5に変わっちゃって動かせない
            if (nodes[player.x][player.y].isGoal){
                isEnd = true;
                System.err.println("END");
            }
        }

        if (count > 50){
            isEnd = true;
        }
//            System.out.println("*goal(" + goal.x + ", "+ goal.y + ")");

        //もし動かなかったらaddMoveLogでwを出力させる
    }

    //敵の挙動
    //A
    public void moveA(PlayerEnemy player, PlayerEnemy enemy){
        int dx = player.x - enemy.x;
        int dy = player.y - enemy.y;
        //①dyが0でない時
        if (dy != 0){
            //符号方向下
            if (dy > 0){
                //下方向に動けるか確認、動ければ動いてAのターンは終了
                //動けなければ次の処理を行う
                if (moveDown(enemy)){
                    return;
                }
            //符号方向上
            }else {
                if (moveUp(enemy)){
                    return;
                }
            }
        }
        //②　①の状況で動けずに、dxが0でない時
        if (dx != 0){
            if (dx > 0){
                if (moveRight(enemy)){
                    return;
                }
            }else {
                if (moveLeft(enemy)){
                    return;
                }
            }
        }
        //③　①、②の状況で動けない場合、上、左、下、右
        if (moveDown(enemy)){
            return;
        }
        if (moveLeft(enemy)){
            return;
        }
        if (moveUp(enemy)){
            return;
        }
        if (moveRight(enemy)){
            return;
        }

    }

    //B
    public void moveB(PlayerEnemy player, PlayerEnemy enemy){
        int dx = player.x - enemy.x;
        int dy = player.y - enemy.y;
        //dyが0でない時
        if (dx != 0){
            if (dx > 0){
                if (moveRight(enemy)){
                    return;
                }
            }else {
                if (moveLeft(enemy)){
                    return;
                }
            }
        }

        if (dy != 0){
            //符号方向下
            if (dy > 0){
                if (moveDown(enemy)){
                    return;
                }
            //符号方向上
            }else {
                if (moveUp(enemy)){
                    return;
                }
            }
        }

        if (moveUp(enemy)){
            return;
        }
        if (moveLeft(enemy)){
            return;
        }
        if (moveDown(enemy)){
            return;
        }
        if (moveRight(enemy)){
            return;
        }
    }

    //ゲーム終了時の処理
    public void gameEnd(){
        System.out.println("End with " + count + " move");
        System.out.println("Move Log : " + getMoveLog());
    }

    //上に移動する時の挙動（プレイヤーの挙動ログを取れる様になっている）
    //上
    public boolean moveUp(PlayerEnemy playerEnemy){
        if (playerEnemy.isPlayer){
            player.y --;
            addMoveLog("u");
        }else {
            if (getMap()[playerEnemy.x][playerEnemy.y - 1]) {
                playerEnemy.y = playerEnemy.y - 1;
                return true;
            }
        }
        return false;
    }

    //下
    public boolean moveDown(PlayerEnemy playerEnemy){
        if (playerEnemy.isPlayer){
            player.y ++;
            addMoveLog("d");
        }else {
            if (getMap()[playerEnemy.x][playerEnemy.y + 1]) {
                playerEnemy.y = playerEnemy.y + 1;
                return true;
            }
        }
        return false;

    }

    //右
    public boolean moveRight(PlayerEnemy playerEnemy){
        if (playerEnemy.isPlayer){
            player.x ++;
            addMoveLog("r");
        }else {
            if (getMap()[playerEnemy.x + 1][playerEnemy.y]) {
                playerEnemy.x = playerEnemy.x + 1;
                return true;
            }
        }
        return false;
    }

    //左
    public boolean moveLeft(PlayerEnemy playerEnemy){
        if (playerEnemy.isPlayer){
            player.x --;
            addMoveLog("l");
        }else {
            if (getMap()[playerEnemy.x - 1][playerEnemy.y]) {
                playerEnemy.x = playerEnemy.x - 1;
                return true;
            }
        }
        return false;
    }
}
