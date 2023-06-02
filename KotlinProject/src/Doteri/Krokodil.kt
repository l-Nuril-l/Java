package Doteri

class Krokodil(Name: String, age: Int) : Animal(Name, age) {
    override fun voice(): String {
        return "kus";
    }
}