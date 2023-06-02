package NoPls

class MyArray(var arr: Array<Int>) : IMath {

    override fun Max(): Int {
        return  arr.maxOf { x -> x }
    }

    override fun Min(): Int {
        return  arr.minOf { x -> x }
    }

    override fun Avg(): Float {
        return  arr.sumOf { x -> x } / arr.size.toFloat()
    }
}