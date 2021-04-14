
fun sumTwoNumbers(a:Int, b:Int) : String?{

    return "$a + $b = " + (a+b)
}

fun arrayListPractice(){

    var numbers = arrayListOf(2, 1, 5, 6, 3, 4)

    numbers.sort()

    var lastTwoNumbers = arrayListOf<Int>()

    numbers.forEach {

        lastTwoNumbers.add(it)

        when(lastTwoNumbers.size){

            2 -> {

                println(sumTwoNumbers(lastTwoNumbers[0], lastTwoNumbers[1]))

                lastTwoNumbers.removeAt(0)
            }
        }
    }
}

fun main() {

    arrayListPractice()
}