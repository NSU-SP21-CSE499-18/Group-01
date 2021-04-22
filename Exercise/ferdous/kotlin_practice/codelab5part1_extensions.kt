/**
 * Extension function & properties
 */
class A(var n: Int)

// extension function
fun A.nPlusOne(): Int {

    return n+1
}

// extension property(must be 'val')
val A.isNPositive: Boolean
    get() = n > 0

fun main(args: Array<String>) {

    var a: A = A(n=5)

    println(a.nPlusOne())
    
    println(a.isNPositive)

    a.n = -10
    println(a.isNPositive)
}