fun main(){

    //Python type lists (non-mutable)
    var foodList = listOf("Bread" , "Rice", "Vegetables", "Fruits")
    println("List-> $foodList")

    //Python type lists (mutable) // Kind of ArrayList of Java (Arraylist also available in kotlin)
    var fruitList = mutableListOf<String>()
    fruitList.add("Apple")
    fruitList.add("Orange")
    fruitList.add("Grapes")
    fruitList.add("Banana")
    fruitList.add("Tomato")

    println("Fruit List-> $fruitList")
    
    fruitList.remove("Tomato")
    for(fruit in fruitList)
    {
        print("$fruit   ")
    }
    println()

    //Array
    var ara = arrayOf("Hello", "World", "!", 123) //Homogeneous array like JS
    for(x in ara)
    {
        print("$x   ")
    }

    //Type specific array
    var intAra = intArrayOf()
    var doubleAra = doubleArrayOf()
}