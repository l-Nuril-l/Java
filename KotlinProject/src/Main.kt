fun Int.fraction(zn: Int):Fraction
{
    return Fraction(this,zn);
}

fun main()
{
    println(Fraction(2,4).plus(Fraction(4  ,2)))
    println(Fraction(2,4).minus(Fraction(4  ,2)))
    println(Fraction(2,4).times(Fraction(4  ,2)))
    println(Fraction(2,4).div(Fraction(4  ,2)))
    println(Fraction(2,4) > (Fraction(4  ,2)))
    println(Fraction(2,4) < (Fraction(4  ,2)))

    println(Fraction(2,4) < (Fraction(2  ,4)))
    println(Fraction(2,4) > (Fraction(2  ,4)))

    println(Fraction(2,4) == (Fraction(2  ,4)))

    val n : Int = 6;
    val f : Fraction =  n.fraction(8);
    println(f)
}