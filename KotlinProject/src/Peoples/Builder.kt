package Peoples

class Builder(Name: String, Surname: String, age: Int) : Human(Name, Surname, age) {

    fun work():String { return "buildit" };
}