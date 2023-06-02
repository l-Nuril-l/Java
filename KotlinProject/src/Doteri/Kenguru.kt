package Doteri

class Kenguru(Name: String, age: Int) : Animal(Name, age) {
    override fun voice(): String {
        return "ia";
    }
}