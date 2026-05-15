import java.math.BigInteger;

public class Main {
    static int go(int x) {
        if(x<1)
            return 1;
        else
            return x+go(x-1) + go(x-2);
    }
    public static void main(String[] args) {
        System.out.println(245 / (2*3*4*5));
    }
}
