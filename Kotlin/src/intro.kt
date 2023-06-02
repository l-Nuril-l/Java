
//fun sqr(x: Int): Int {
//    return x * x
//}

fun sqr(x: Int) = x * x

//fun max(a: Int, b: Int): Int {
//    if (a > b) {
//        return a
//    }
//    return b
//}

fun max(a: Int, b: Int) = if (a > b) a else b

fun max3(a: Int, b: Int, c: Int) = if (a > b && a > c) a else if (b > c) b else c

fun dayNameWhen(number: Int): String {
    return when (number) {
        1 -> "Monday"
        2 -> "Tuesday"
        else -> "UNKNOWN"
    }
}

fun factorial(n: Int): Double {

    for (i in 10 downTo 1 step 2) {

    }


    var result = 1.0
    for (i in 1..n) {
        result *= i
    }
    return result
}

fun lists() {
//    var list = listOf<Int>()
//    list.plus(10)
//    list += 10

//    list[0] = 10

//    val mlist = mutableListOf<Int>()
//    mlist[0] = 1

//    listOf<Int>(10, 20)

    val list = listOf<Int>(10,5,3,10,2,3,4,5)
//    val result = list.filter(fun(x: Int) = x < 5)
//    val result = list.filter({x -> x < 5})
    val result = list.filter { it < 5 }.map { it * 10 }
    for (i in result) {
        println(i)
    }
}

fun isPrime(n: Int) = n >= 2 && (2..n/2).all { n % it != 0 }


fun read() {
 //   val line = readLine()
//    if (line != null) {
//        for(word in line.split(" ")) {
//            println("$word")
//        }
//    }

    val a = readLine()
    val b =  readLine()
    val c = (a?.toIntOrNull()?: 0) + (b?.toIntOrNull() ?: 0)
    //val c = a!!.toInt() + b!!.toInt()

    println(c)
}


fun main(args: Array<String>) {
    read()
    //lists()
//    println (sqr(10))
//    println(kotlin.math.sqrt(16.0))
//    var a = 5
//    var b: Int = 10
//
//    val pi = 3.14
//
//    println("a = $a\nb = $b\nresult=${pi + 3}")
}