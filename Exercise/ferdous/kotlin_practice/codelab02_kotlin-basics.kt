/**
 * codelab-02 start
 */
fun variablesAndTypes() {

    val CONSTANT_X : Int = 4; // val is immutable
    
    var x : Int = CONSTANT_X;
    x += 1 // var is mutable
    println("integer -> $x")
    
    var xString : String = x.toString()
    xString += "(str)"
    println("string -> " + xString)
    
    var xDouble : Double = x.toDouble()
    println("double -> ${xDouble + 2.5}")
}

fun nullability() {

    var nonNullVariable : Int; // cannot be null

    var nullableVariable : Int? = null; // Int? means variable can be assigned null

    // if(nullableVariable==null) nonNullVariable = 0 
    // else nonNullVariable = nullableVariable
    nonNullVariable = nullableVariable ?: 0 

    println("$nonNullVariable (not null)")
}

fun inRangeConditionals(n:Int) {

    if(n<0) {

        println("not even zero!")
        return
    }

    when(n){

        0 -> println("zero")

        in 1..100 -> println("between 1 to 100")
        
        else -> println("out of range")
    }
}

fun arrayList(){

    var numbers = arrayListOf(2, 1, 5, 6, 3, 4)
    numbers.sort()
    print("the list -> ")
    for((index, element) in numbers.withIndex()) {

        print("($index)$element ")
    }
    println()
    println()

    var lastTwoNumbers = arrayListOf<Int>()
    numbers.forEach {

        lastTwoNumbers.add(it)
        if(lastTwoNumbers.size==2){    
            println( "sum of last two numbers = ${lastTwoNumbers[0] + lastTwoNumbers[1]}")
            lastTwoNumbers.removeAt(0)
        }
    }
}

fun loops(){

    // 1 2 3 4 5
    for (i in 1..5) print("$i ")
    println()

    // 5 4 3 2 1
    for (i in 5 downTo 1) print("$i ")
    println()

    // 1 3 5 7 9
    for (i in 1 until 10 step 2) print("$i ")
    println()

    // d e f g
    for (i in 'd'..'g') print("$i ")
    println()
}
/**
 * codelab-02 end
 */