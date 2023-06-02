public class BadList {
    int[] arr;

    public BadList(int[] arr) {
        this.arr = arr;
    }

    public int avg()
    {
        return sum() / arr.length;
    }

    public int sum()
    {
        int sum =0;
        for (var it: arr
        ) {
            sum += it;
        }
        return sum;
    }
    public int min()
    {
        int min = Integer.MAX_VALUE;
        for (var it: arr
        ) {
            min = Math.min(min,it);
        }
        return min;
    }
    public int max()
    {
        int max = Integer.MIN_VALUE;
        for (var it: arr
        ) {
            max = Math.max(max,it);
        }
        return max;
    }

    public int[] getArr() {
        return arr;
    }

    public void setArr(int[] arr) {
        this.arr = arr;
    }
}
