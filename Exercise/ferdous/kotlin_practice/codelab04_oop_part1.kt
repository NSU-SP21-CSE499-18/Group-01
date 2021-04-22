/**
 * execution space for currently practicing codelab
 */

/**
 * Inheritance (& Polymorphism)
 */

// abstract superclass
public abstract class Product (open var name:String = "", open var price:Int = -1, open var sale: Int = 0) {

    init{
        // this block executes whenever constructor is called
        
        if(sale>100) sale = 100
    }

    open fun getPriceAfterSale(): Int{

        return price - (price*sale/100)
    }
}

// subclass
public class NecessityProduct (override var name:String = "", override var price:Int = -1, override var sale: Int = 0) : 
                Product(name = name, price = price, sale = sale) {

    override fun getPriceAfterSale(): Int{

        var necessityProductSale = sale + 20
        if(necessityProductSale > 100) necessityProductSale = 100

        return price - (price*necessityProductSale/100)
    }
}

/**
 * Interface (& Polymorphism)
 */
public interface NecessityProductDao{

    abstract fun writeNecessityProduct(necessityProduct: NecessityProduct)

    abstract fun readAllNecessityProducts() : List<NecessityProduct>

    abstract fun readAllNecessityProductsBelowPrice(price: Int): List<NecessityProduct> 
}

public class AWSNecessityProductDao: NecessityProductDao{

    var awsProducts = arrayListOf(NecessityProduct(name="Hand Sanitizer", price=100), 
                                    NecessityProduct(name="Surgical Mask", price=20), 
                                    NecessityProduct(name="Baby Food", price=350))

    override fun writeNecessityProduct(necessityProduct: NecessityProduct){

        awsProducts.add(necessityProduct)
    }

    override fun readAllNecessityProducts(): List<NecessityProduct> {

        return awsProducts
    }

    override fun readAllNecessityProductsBelowPrice(price: Int): List<NecessityProduct> {

        return awsProducts.filter{ it.price < price }
    }
}


fun main(args: Array<String>) {

    // inheritance & polymorphism
    var product: Product = NecessityProduct(name="Tooth Brush", price=20, sale = 20)
    println("${product.name} price down to ${product.getPriceAfterSale()} taka from ${product.price} taka!")

    println()

    // interface & polymorphism
    var awsDao: NecessityProductDao = AWSNecessityProductDao()
    
    var newProduct: NecessityProduct = NecessityProduct(name="Toilet Paper", price=50, sale=0)
    
    awsDao.writeNecessityProduct(newProduct)

    var productsInAws = awsDao.readAllNecessityProductsBelowPrice(120)
    println("Product List -> ")
    productsInAws.forEach{

        println("${it.name} (${it.price}taka)")
    }
}