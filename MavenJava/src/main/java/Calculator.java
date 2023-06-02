import java.util.Scanner;

public class  Calculator {

    public static int addition(int x, int y) {
        return x + y;
    }

    public static int subtraction(int x, int y) {
        return x - y;
    }

    public static int multiplication(int x, int y) {
        return x * y;
    }

    public static int division(int x, int y) {
        return x / y;
    }

    public static int maximum(int x, int y) {
        return Math.max(x,y);
    }

    public static int minimum(int x, int y) {
        return Math.min(x,y);
    }

    public static double percent(int n, int p) {
        return (double)n / 100 * p;
    }
    public static double pow(int a, int b) {
        return Math.pow(a,b);
    }
}