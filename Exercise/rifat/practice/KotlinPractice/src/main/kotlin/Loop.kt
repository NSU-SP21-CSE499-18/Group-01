fun main()
{
    val arra = arrayOf<Int>(1,2,3,4,5,6,7,8,9)

    //Traditional for loop
    for(i in 0..arra.size-1)
    {
        print(arra[i].toString() + "  ")
    }
    println()

    //Or
    for(value in arra)
    {
        print(value.toString() + "...")
    }
    println()

    //while loop
    var x = 5
    while(x!=0)
    {
        print("$x  ")
        x--
    }
}
