public class Level1Temp {

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
            int x;
            int y;
            int cost;
            //        int heuristicCost;
            Node up;
            Node down;
            Node left;
            Node right;
            boolean isStart;
            boolean isGoal;
            //        boolean isOpen;
//        boolean isClosed;
            boolean isPlayer;
            boolean isEnemy;
            boolean isItem;
            boolean isWall;
            public Node(){
                cost = 0;

            }


        }

        //マップのサイズ
        final int mapX = 11;
        final int mapY = 7;

        //マップの形状
        Node[][] nodes = new Node[mapX][mapY];

        //map[x][y]が動けるかどうか
        Boolean[][] map = new Boolean[getMapX()][getMapY()];

        //敵Eの状態を管理する為の変数
        int count = 0;

        //移動ログ
        String moveLog;

        int time = 50;
        PlayerEnemy player = new PlayerEnemy();
        PlayerEnemy[] enemies = new PlayerEnemy[5];
    /*
        @pram
        0 A
        1 B
        2 C
        3 D
        4 E
    */

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
        // TODO: 2018/04/04 mainに処理を書きすぎない様にする
        // TODO: 2018/04/06 getter setterをしっかりやる
        public static void main(String[] args) {
            Level1 level1 = new Level1();
            level1.initialize();
//        level1.play();
            level1.drawMap();
        }

        public void drawMap(){
            Node node = new Node();
            for (int i = 0; i < getMapY(); i++) {
                for (int j = 0; j < getMapX(); j++) {
                    node = nodes[j][i];
                    if (node.isWall){
                        System.out.print("#");
                    }else  if (node.isItem){
                        System.out.print("o");
                    }else if (node.isEnemy){
                        System.out.print("Z");
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
        }

        //初期化するためのメソッド
        // TODO: 2018/04/04 mainにある処理をもっとこのメソッドに任せる
        public void initialize(){
            for (int i = 0; i < getMapX(); i++){
                for (int j = 0; j < getMapY(); j++){
                    map[i][j] = new Boolean(true);
                    nodes[i][j] = new Node();
                    if (i == 0 || i == getMapX()-1 || j == 0 || j == getMapY()-1){
                        nodes[i][j].isWall = true;
                    }else {
                        nodes[i][j].x = i;
                        nodes[i][j].y = j;

                        nodes[i][j].up = nodes[i][j-1];
                        nodes[i][j].down = nodes[i][j+1];
                        nodes[i][j].right = nodes[i+1][j];
                        nodes[i][j].left = nodes[i-1][j];
                    }
                }
            }


            nodes[1][1].isItem = true;
            nodes[4][3].isItem = true;
            nodes[6][3].isItem = true;
            nodes[5][5].isItem = true;
            nodes[9][5].isItem = true;
            nodes[1][5].isStart = true;
            nodes[9][1].isGoal = true;
            nodes[4][1].isEnemy = true;
            nodes[6][1].isEnemy = true;

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

            for (int i = 0; i < 5; i++){
                enemies[i] = new PlayerEnemy();
            }

            player.isPlayer  = true;

//        for (int i = time; i >= 0; i--) {
//            play();
//        }
        }

        //プレイヤーの挙動
        public void play(){
            final int A = 0;
            final int B = 1;
            final int C = 2;
            final int D = 3;
            final int E = 4;
            movePlayer();
            moveA(player, enemies[A]);
            moveB(player, enemies[B]);
            moveC(enemies[C]);
            moveD(enemies[D]);
            moveE(enemies[E]);
        }

        //プレイヤーの挙動
        public void movePlayer(){
            // TODO: 2018/04/03 ダイクストラで最短経路問題を解決したいが、動的にマップが変わる（敵が動く）
            // TODO: 2018/04/04 巡回セールスマン問題ではないのか？とりあえず近くのアイテムをゴールとして、毎回ゴールを延長していく。
            // TODO: 2018/04/09 A*アルゴリズムを採用
            // TODO: 2018/04/09 とりあえず実装
            // TODO: 2018/04/09 敵が来たら止まって待機でもいいか？


            // TODO: 2018/04/06 もし動かなかったらaddMoveLogでwを出力させる


            // TODO: 2018/04/06 Goalしたらゲーム終了関数を呼ぶ
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

        //C
        public void moveC(PlayerEnemy enemy){
            //
            if (moveLeft(enemy)){
                return;
            }
            if (moveUp(enemy)){
                return;
            }
            if (moveRight(enemy)){
                return;
            }
            if (moveDown(enemy)){
                return;
            }
        }

        //D
        public void moveD(PlayerEnemy enemy){
            //
            if (moveRight(enemy)){
                return;
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
        }

        //E
        public void moveE(PlayerEnemy enemy){
            //現在の状態に応じて敵Cと敵Dの行動を交互に繰り返す
            if (getCount() % 2 == 0){
                moveC(enemy);
            }else {
                moveD(enemy);
            }
            count++;
        }

        //ゲーム終了時の処理
        public void gameEnd(){
            System.out.println(getMoveLog());
        }

        //上に移動する時の挙動（プレイヤーの挙動ログを取れる様になっている）
        //上
        public boolean moveUp(PlayerEnemy playerEnemy){
            if (getMap()[playerEnemy.x][playerEnemy.y - 1]){
                playerEnemy.y = playerEnemy.y - 1;
                if (playerEnemy.isPlayer){
                    addMoveLog("u");
                }
                return true;
            }
            return false;
        }

        //下
        public boolean moveDown(PlayerEnemy playerEnemy){
            if (getMap()[playerEnemy.x][playerEnemy.y + 1]){
                playerEnemy.y = playerEnemy.y + 1;
                if (playerEnemy.isPlayer){
                    addMoveLog("d");
                }
                return true;
            }
            return false;

        }

        //右
        public boolean moveRight(PlayerEnemy playerEnemy){
            if (getMap()[playerEnemy.x + 1][playerEnemy.y]){
                playerEnemy.x = playerEnemy.x + 1;
                if (playerEnemy.isPlayer){
                    addMoveLog("r");
                }
                return true;
            }
            return false;
        }

        //左
        public boolean moveLeft(PlayerEnemy playerEnemy){
            if (getMap()[playerEnemy.x - 1][playerEnemy.y]){
                playerEnemy.x = playerEnemy.x - 1;
                if (playerEnemy.isPlayer){
                    addMoveLog("l");
                }
                return true;
            }
            return false;
        }
    }

