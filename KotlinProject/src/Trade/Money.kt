package Trade

class Money(var money: Int,var coins: Int) {
    override fun toString(): String {
        return "Money(money=$money, coins=$coins)"
    }
    fun add(money: Int,coins: Int)
    {
        this.coins += coins;
        if(this.coins > 99) {
            val res = this.coins / 100;
            this.money+= res;
            this.coins -= res * 100;
        }
        this.money += money;


    }
    fun min(money: Int,coins: Int)
    {
        this.coins -= coins;
        if(this.coins < 0) {
            val res = this.coins / -100;
            this.money -= res + 1;
            this.coins = 100 + this.coins % 100;
        }
        this.money -= money;
    }
}