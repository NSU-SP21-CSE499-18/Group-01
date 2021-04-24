package oop

open class Aquarium (open var length: Int = 100,open var width: Int = 20, open var height: Int = 40){ //Primary Constructor

    init { println("Aquarium Initializing") }


    // Define them with var, they are mutable, that is, readable and writable.
    // Define them with val, they are read-only after initialization.
    open var volume: Int  //new property to the class
        public get() = width * height * length / 1000 //getter
        public set(value) {                            //setter
            height = (value*1000) / (width*length)
        }

    open val shape = "rectangle"

    open var water: Double = 0.0
        get() = volume * .9

    constructor(numberOfFish: Int) : this() { //Secondary Constructor
        val tank = numberOfFish*2000*1.1
        val newHeight = (tank / (length * width)).toInt()
        height = newHeight
    }

    public fun printSize(){
        println("Width=$width, Height=$height, Length=$length")
        println("Volume: $volume l Water: $water l (${water/volume*100.0}% full)")
    }

    override fun toString(): String {
        return "Aquarium(width=$width, height=$height, length=$length)"
    }

}