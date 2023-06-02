package Doteri

class Tigr(Name: String, age: Int) : Animal(Name, age) {
    override fun voice(): String {
        return "roar";
    }
}