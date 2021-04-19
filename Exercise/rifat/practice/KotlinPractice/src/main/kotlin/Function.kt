fun main()
{
    val value = 11
    if(isPrime(value)) println("Number is prime") else println("Number is not prime")

    val start = 1
    val end = 100000
    println("Number of Primes: ${countPrime(start,end)}")
}

fun isPrime(x:Int) : Boolean
{
    var res = true
    if(x<2)
        res =  false
    else{
        for(i in 2..x/2)
        {
            if(x%i==0) {
                res = false
                break
            }
        }
    }
    return res
}


//Simple CPU benchmark function
fun countPrime(start:Int, end:Int) : Int
{
    var count = 0

    for(num in start..end)
    {
        if(isPrime(num)) count++
    }

    return count
}