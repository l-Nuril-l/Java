package Why

class MyArray(var arr: Array<Int>) : ISort {


    override fun SortAsc() {
        arr.sort();
    }

    override fun SortDesc() {
        arr.sortDescending()
    }
}