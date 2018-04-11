public class AIhomework2 {
    public static void main(String[] args){
        System.out.println("answer : " + cal(30));
    }

    public static double cal(int m){
        double result = 0;
        for(int i = 2; i <= m; i++){
            System.out.println("i : " + i);
            result += conb(m,i) * suffixCal(i);
            System.out.println("result : " + result);
        }
        return result;
    }

    public static double suffixCal(int k){
        double result = 1;
        for(int i = 0; i < k; i++){
            result *= 2.0;
        }
        System.out.println("suffixCal : " + result);
        return result - 2.0;
    }

    public static double conb(int m, int k){
        double sum1 = 1, sum2 = 1, count = 0, result = 0;
        for(int i = m; count < k; i--){
            sum1 *= i;
            count ++;
        }
        for(int i = k; i > 0; i--){
            sum2 *= i;
        }
        System.out.println("sum1 : " + sum1);
        System.out.println("sum2 : " + sum2);
        result = sum1 / sum2;
        System.out.println("conb : " + result);
        return result;
    }
}
// 処理 k=2Σm {mCk*(2^k-2)}

