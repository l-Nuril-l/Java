package Trade

class Product(var name:String, var money: Money) {
    fun min(money: Int,coins: Int)
    {
        this.money.coins -= coins;
        if(this.money.coins < 0) {
            val res = this.money.coins / -100;
            this.money.money -= res + 1;
            this.money.coins = 100 + this.money.coins % 100;
        }
        this.money.money -= money;
    }
}