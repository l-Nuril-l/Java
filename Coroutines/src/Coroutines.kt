import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.io.*
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*

//suspend fun main(): Unit = coroutineScope {
//    val str : String = readLine().toString();
//    val arr = str.split(" ").map { x -> x.toInt() };
//    println(arr)
//    launch {
//        var min = Int.MAX_VALUE;
//        arr.forEach { x -> if (x < min) min = x }
//        println(min)
//    }
//    launch {
//        var max = Int.MIN_VALUE;
//        arr.forEach { x -> if (x > max) max = x }
//        println(max)
//    }
//    launch {
//        println(arr.reduce { x,y -> x + y })
//    }
//    launch {
//        println(arr.reduce { x,y -> x + y } / arr.size)
//    }
//}


 suspend fun main() = coroutineScope {
     val input =  File(readLine()).inputStream() ;
     val arr: List<Int> = String(input.readAllBytes()).split(" ").map { x -> x.toInt() };
     val evens: Deferred<Int> = async { even(arr) }
     val odds: Deferred<Int> = async { odd(arr) }
     println(evens.await().toString() + " " + odds.await());
}

fun even(arr: List<Int>) : Int {
    val output = PrintStream(File("even.txt").outputStream());
    var size: Int = 0;
    arr.forEach{x -> if (x % 2 == 0) { output.print("$x "); size++ }}
    return size
}

fun odd(arr: List<Int>) : Int {
    val output =  PrintStream(File("odd.txt").outputStream(),true);
    var size: Int = 0;
    arr.forEach{x -> if (x % 2 != 0) { output.print("$x "); size++; }}
    return size
}