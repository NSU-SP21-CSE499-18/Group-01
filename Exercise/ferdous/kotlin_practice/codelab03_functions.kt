/**
 * codelab-03 start
 */ 
import java.util.*

// constant, immutable variable
const val MY_MONEY : Int = 100

// immutable arrays
val items = arrayOf("Phone", "Book", "TV", "Sofa")
val prices = arrayOf(100, 50, 500, 350)

fun getRandomItem(): String {

    return items[Random().nextInt(items.size)]
}

// parameter has default value 
fun getPrice(item: String = items[0]) : Int {

    // almost every expression has a value in Kotlin
    // here 'when' takes the value of correspoding price
    return when(item){

        items[0] -> prices[0]

        items[1] -> prices[1]

        items[2] -> prices[2]

        items[3] -> prices[3]
        
        else -> -1
    }

}

// compact function
fun isPriceTooMuch(price: Int) = price > MY_MONEY

// void function
fun buyAnItem(){

    var pickedItem:String = getRandomItem()
    var pickedItemPrice:Int = getPrice(item=pickedItem)

    if(isPriceTooMuch(pickedItemPrice)){

        println("can't buy ${pickedItem}, need ${pickedItemPrice - MY_MONEY} taka more")
    }
    
    else println("bought a ${pickedItem} with ${pickedItemPrice} taka")
}

// filter a list
fun showItemsICanBuy(){

    val itemsICanBuy = items.filter{ getPrice(it) <= MY_MONEY }

    itemsICanBuy.forEach{

        print("${it}(${getPrice(it)}tk) ")
    }   
    println()

    //println(itemsICanBuy)
}

// lambas and passing function as parameter
fun putItemsOnSale(saleOperation: (Int) -> Int){

    items.forEach{

        val priceAfterSale : Int = saleOperation(getPrice(it))

        println("${it} for ${priceAfterSale} taka")
    }
}

fun main(args: Array<String>) {

    val saleOperation = { price : Int -> price/5 }
    putItemsOnSale(saleOperation)
}

/**
 * codelab-03 end
 */