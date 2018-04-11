package CodeIQ;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        InputStream inputStream = System.in;

        String line;
        ArrayList<Integer> sz = new ArrayList<>();
        ArrayList<Integer> h = new ArrayList<>();
        Main main = new Main();
        while(scanner.hasNext()){
            line = scanner.nextLine();
            String[] split = line.split(",", 0);
            sz.add(Integer.parseInt(split[0]));
            h.add(Integer.parseInt(split[1]));
        }

        for (int i = 0; i < sz.size(); i++) {
            int[] array = main.genBoard(sz.get(i), h.get(i));
//            System.out.println(Arrays.toString(array)); //デバッグ用出力
            int ans = 1;
            if (sz.get(i) != 1) {
                ans = main.countBoard(array, sz.get(i), h.get(i));
//                main.printBoard(array, sz.get(i));
            }
            System.out.println(ans);

        }

    }

    int countBoard(int[] array,int sz, int h){
        int count = 1, max, x = 0, y = 0;
        boolean isContinue = false;
        boolean[] isSquare = new boolean[array.length];
        max = count;
        for (int high = 1; high <= h; high++) {
//            System.out.println("high : " + high);
            for (int i = 0; i < isSquare.length; i++){
                isSquare[i] = false;
            }
            for (int i = 0; i < array.length; i++) { //繋がっているところを印付け
                if (high == array[i]){
                    if ((i + 1) % sz == 0){
                        if (isContinue){
                            isSquare[i] = true;
                            isContinue = false;
                        }
                    }else if (high == array[i+1]){
                        isContinue = true;
                        isSquare[i] = true;
                    }else if (isContinue){
                        isSquare[i] = true;
                        isContinue = false;
                    }else {
                        isContinue = false;
                    }
                }
            }
            int xCount = 0, yCount = 1, temp = 0, j = 0;
            boolean isEndLine = false, isBreak = false;
            int minXCount = 0;
            for (int i = 0; i < array.length - sz; i++){ //実際に面積を数えていく
                x = i % sz;
                y = i / sz;
                if (sz - x <= minXCount){
                    continue;
                }
                if (sz - y <= minXCount){
                    break;
                }
                if (isSquare[i] && !isContinue){
                    isContinue = true;
                    temp = i;
                }else if ((i + 1) % sz == 0){
                    if (isContinue){
                        xCount++;
                    }
                    isEndLine = true;
                }
                if (!isSquare[i] || isEndLine){
                    if (isContinue && minXCount < xCount){    //横の繋がり終了時に縦を数えて面積求める
//                        System.out.println("y, x, hight : " + temp/sz + ", " + temp%sz + ", " + high);
                        j = temp + sz;
//                        System.out.println("xCount : " + xCount);
                        while (isSquare[j]){
                            yCount++;
//                            System.out.println("yCount : " + yCount);
                            for (int k = j; k - j <= xCount  -1; k++){
//                                System.out.println("isSquare : " + isSquare[k]);
                                if (!isSquare[k]){
                                    if (k-j > yCount -1){
                                        xCount = k - j;
//                                        System.out.println("k-j : " + xCount);
                                    }else {
                                        yCount--;
                                        isBreak = true;
//                                        System.out.println("yCount : " + yCount);
                                    }
                                    break;
                                }
                            }
                            if (yCount >= xCount || isBreak){
                                isBreak = false;
                                break;
                            }
                            j += sz;
                            if (j >= array.length){
                                break;
                            }
                        }
                        if (yCount >= xCount){
                            count = xCount * xCount;
                        }else {
                            count = yCount * yCount;
                        }
//                        System.out.println("result : " + count);
                        if (count > max){
//                            System.out.println("update : " + count);
                            minXCount = xCount;
                            max = count;
                        }
                    }
                    xCount = 0;
                    yCount = 1;
                    temp = 0;
                    isContinue = false;
                    isEndLine = false;
                }
                if (isContinue){
                    xCount++;
//                    System.out.println("XcountUP y, x, hight : " + y + ", " + x + ", " + h);
                }
            }
        }
        return max;
    }

    void printBoard(int[] array, int sz){
        String black   = "\u001b[38;5;";
        String red     = "\u001b[0m";
//        String green   = "\u001b[38;5;";
//        String yellow  = "\u001b[38;5;";
//        String blue    = "\u001b[38;5;";
//        String magenta = "\u001b[38;5;";
//        String cyan    = "\u001b[38;5;";
//        String white   = "\u001b[38;5;";
        String color;
        for (int i = 0; i < sz; i++){
            for (int j = 0; j < sz; j++){

                System.out.print(black+array[i*sz + j] + "m" + array[i*sz + j] + red);
            }
            System.out.println();
        }
    }

    int[] genBoard(int sz, int h) {
        int[] array = new int[sz*sz];
        for (int i = 0; i < sz * sz; i++) {
                 array[i] = 1;
        }
        int r = 1, r0, r1, r2, r3, r4, max;
        if (sz < 100) {
            max = sz;
        } else {
            max = 100;
        }
        for (int i = 0; i < max; i++) {
            r0 = (r % 10009) * 99991;
            r = r0;
            r1 = (r % 10009) * 99991;
            r = r1;
            r2 = (r % 10009) * 99991;
            r = r2;
            r3 = (r % 10009) * 99991;
            r = r3;
            r4 = (r % 10009) * 99991;
            r = r4;

            int sqrX = r0 % sz;
            int sqrY = r1 % sz;
            int sqrW = r2 % (sz - sqrX) % 100;
            int sqrH = r3 % (sz - sqrY) % 100;
            int brdH = (r4 % h) + 1;
            for (int x = sqrX; x < sqrX + sqrW; x++) {
                for (int y = sqrY; y < sqrY + sqrH; y++) {
                    array[x + y * sz] = brdH;
                }
            }
        }
        return array;
    }


}



