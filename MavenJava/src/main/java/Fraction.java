public class Fraction {
    public int N;
    public int D;

    public Fraction(int numerator, int denominator)
    {
        N = numerator;
        D = denominator;
    }

    public Fraction(int num)
    {
        N = num;
        D = 1;
    }

    @Override
    public String toString() {
        return "Fraction{" +
                "N=" + N +
                ", D=" + D +
                '}';
    }

    public static Fraction add(Fraction f1, Fraction f2)
    {
        return new Fraction(f1.N * f2.D + f2.N * f1.D, f1.D * f2.D).Normalization();
    }

    public static Fraction mul(Fraction f1, Fraction f2)
    {
        return new Fraction(f1.N * f2.N, f1.D * f2.D).Normalization();
    }

    public static Fraction min(Fraction f1, Fraction f2)
    {
        return new Fraction(f1.N * f2.D - f2.N * f1.D, f1.D * f2.D).Normalization();
    }

    public static Fraction min(Fraction f1)
    {
        return new Fraction(-f1.N, f1.D).Normalization();
    }

    public static Fraction div(Fraction f1, Fraction f2)
    {
        return new Fraction(f1.N * f2.D, f1.D * f2.N).Normalization();
    }

    public Fraction Normalization()
    {
        var n = Math.abs(N);
        var d = Math.abs(D);
        var nod = NOD(n, d);
        var sign = (int)Math.signum(N*D);
        return new Fraction(sign * n / nod, d / nod);
    }

    static int NOD(int a, int b)
    {
        while (a > 0 && b > 0)
            if (a > b)
                a %= b;
            else
                b %= a;

        return a + b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fraction fraction = (Fraction) o;

        if (N != fraction.N) return false;
        return D == fraction.D;
    }

    @Override
    public int hashCode() {
        int result = N;
        result = 31 * result + D;
        return result;
    }
}
