//public class PlayerEnemy {
//    int x;
//    int y;
//    //プレイヤーかどうかを判断する変数で、これがtrueのオブジェクトは移動トラッキングする
//    boolean isPlayer = false;
//    //前回の位置を保存するかどうかの変数
//    boolean isEnemeyCDE = false;


    // TODO: 2018/04/04 下のメソッド使えた方が格好いいか？ 

//    public boolean moveUp(boolean[][] map) {
//        if (map[x][y - 1]) {
//            y = y - 1;
//            return true;
//        }
//        return false;
//    }
//
//    public boolean moveDown(PlayerEnemy playerEnemy) {
//        if (getMap()[playerEnemy.x][playerEnemy.y + 1]) {
//            playerEnemy.y = playerEnemy.y + 1;
//            return true;
//        }
//        return false;
//
//    }
//
//    public boolean moveRight(PlayerEnemy playerEnemy) {
//        if (getMap()[playerEnemy.x + 1][playerEnemy.y]) {
//            playerEnemy.x = playerEnemy.x + 1;
//            return true;
//        }
//        return false;
//    }
//
//    public boolean moveLeft(PlayerEnemy playerEnemy) {
//        if (getMap()[playerEnemy.x - 1][playerEnemy.y]) {
//            playerEnemy.x = playerEnemy.x - 1;
//            return true;
//        }
//        return false;
//    }
//}