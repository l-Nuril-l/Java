import kotlin.math.abs

class Fraction(var ch: Int, var zn:Int) {

    operator fun plus(fr: Fraction):Fraction
    {
        val newZn = zn * fr.zn;
        val newCh = ch * (newZn / zn) + fr.ch * (newZn / fr.zn)

        val newFr = Fraction(newCh,newZn);
        newFr.reduce();
        return newFr;
    }

    operator fun minus(fr: Fraction):Fraction
    {
        val newZn = zn * fr.zn;
        val newCh = ch * (newZn / zn) - fr.ch * (newZn / fr.zn)
        val newFr = Fraction(newCh,newZn);
        newFr.reduce();
        return newFr;
    }

    operator fun times(fr: Fraction):Fraction
    {
        val newZn = zn * fr.zn;
        val newCh = ch * fr.ch;

        val newFr = Fraction(newCh,newZn);
        newFr.reduce();
        return newFr;
    }

    operator fun div(fr: Fraction):Fraction
    {
        val newZn = zn * fr.ch;
        val newCh = ch * fr.zn;

        val newFr = Fraction(newCh,newZn);
        newFr.reduce();
        return newFr;
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Fraction) return false;
        val d1 = Fraction(ch,zn);
        val d2 = Fraction(other.ch,other.zn);
        val newZn = d1.zn * d2.zn;
        return d1.ch * (newZn / d1.zn) == d2.ch * (newZn / d2.zn);

    }

    operator fun compareTo(fr: Fraction):Int
    {
        val d1 = Fraction(ch,zn);
        val d2 = Fraction(fr.ch,fr.zn);

        val newZn = d1.zn * d2.zn;
        return d1.ch * (newZn / d1.zn) - d2.ch * (newZn / d2.zn);
    }

    fun reduce() {
        val nod = NOD(ch, zn)
        ch /= nod
        zn /= nod
    }

    fun NOD(vl: Int, v2: Int): Int {
        var vl = abs(vl)
        var v2 = abs(v2)
        while (v2 > 0) {
            val temp = v2
            v2 = vl % v2
            vl = temp
        }
        return vl
    }

    override fun toString(): String {
        return "Fraction(ch=$ch, zn=$zn)"
    }


}