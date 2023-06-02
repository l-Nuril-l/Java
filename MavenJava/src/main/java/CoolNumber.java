public class CoolNumber {
    int VeryCoolNum;

    public CoolNumber(int veryCoolNum) {
        VeryCoolNum = veryCoolNum;
    }

    public int getVeryCoolNum() {
        return VeryCoolNum;
    }

    public void setVeryCoolNum(int veryCoolNum) {
        VeryCoolNum = veryCoolNum;
    }

    public String to8() {

        return Integer.toOctalString(VeryCoolNum);
    }
    public String to16() {
        return Integer.toHexString(VeryCoolNum);
    }
    public String to2() {
        return Integer.toBinaryString(VeryCoolNum);
    }
}
