
fun main()
 {
     var value = 5

     if(value%2==0)
     {
         println("The value $value is Even!")
     }
     else{
         println("The value $value is Odd!")
     }

     //switch case
     when(value%2)
     {
         0->{
             println("The value $value is Even!")
         }
        1->{
             println("The value $value is Odd!")
         }
     }



     //Assignment operation
     val a = 5
     val b = 10
     val max = if (a > b) {
         println("Choose a")
         a
     } else {
         println("Choose b")
         b
     }
     println("Max: $max")


     //Switch Case + Range
     val x = 5
     val validNumbers = arrayOf(1,2,3,4,5,6)
     when (x) {
         in 1..10 -> print("x is in the range")
         in validNumbers -> print("x is valid")
         !in 10..20 -> print("x is outside the range")
         else -> print("none of the above")
     }
 }