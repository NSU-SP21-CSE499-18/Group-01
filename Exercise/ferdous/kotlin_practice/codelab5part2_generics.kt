/**
 * Basic Generics
 */
    // <T extends Number>
    class Sum<T: Number>(var one: T, var two: T) {

        fun perform(): String{
    
            return "sorry cannot calculate '${one} + ${two}', because Kotlin sucks"
        }
    
    }
    
    fun main(args: Array<String>) {
    
        var integerSum: Sum<Int> = Sum<Int>(2, 3)
        println(integerSum.perform())
    
        println()
    
        var doubleSum: Sum<Double> = Sum<Double>(2.75, 3.08)
        println(doubleSum.perform())
    }