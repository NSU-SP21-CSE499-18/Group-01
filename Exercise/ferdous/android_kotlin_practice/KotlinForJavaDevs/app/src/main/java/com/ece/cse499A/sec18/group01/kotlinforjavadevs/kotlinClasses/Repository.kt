package com.ece.cse499A.sec18.group01.kotlinforjavadevs.kotlinClasses

/**
 * Kotlin conversion of javaClasses/Repository.java class
 *
 * Singleton objects are specified with 'object' keyword
 *
 * static methods and variables are kept inside 'companion object{...}' block
 *
 * data classes can be destructed and looped over like- 'val/var (variable1, variable2) = object'
 *
 * x = y ?: 0, same as
 *  if(y!=null) x = y;
 *  else x = 0;
 *
 *  use list.map { listItem -> ... }  to generate another list by iterating over the original list
 */
object Repository {
    private val _users = mutableListOf<User>()

    // properly expose the _users list to outside
    val users: MutableList<User>
        get() = _users

    /**
     * bad idea to expose _users list like this,
     * because it allows the main list can be edited from outside

    fun getUsers(): List<User> {
        return _users
    }
     */

    val formattedUserNames: List<String>
        get() {

            return _users.map { user ->
                if(user.lastName!=null){
                    if(user.firstName!=null) "${user.firstName} ${user.lastName}"
                    else user.lastName ?: "Unknown"
                }
                else user.firstName ?: "Unknown"
            }

            /* generating formatted names using for loop

            val userNames = ArrayList<String>(users.size)
            for ((firstName, lastName) in users) {
                val name: String = if (lastName != null) {
                    if (firstName != null) {
                        "$firstName $lastName"
                    } else {
                        lastName
                    }
                } else {
                    firstName ?: "Unknown"
                }
                userNames.add(name)
            }
            return userNames
            */
        }

    // keeping the constructor private to enforce the usage of getInstance
    init {
        val user1 = User("Jane", "")
        val user2 = User("John", null)
        val user3 = User("Anne", "Doe")

        _users.add(user1)
        _users.add(user2)
        _users.add(user3)
    }
}


/**
 * (Kotlin allows to  add codes outside the class in the same file as a class)
 * Extension functions/properties
 * functions/properties that are added to the class on the go
 */

// extension function
fun User.getFormattedName(): String {
    return if (lastName != null) {
        if (firstName != null) {
            "$firstName $lastName"
        } else {
            lastName ?: "Unknown"
        }
    } else {
        firstName ?: "Unknown"
    }
}

// extension property
val User.userFormattedName: String
    get() {
        return if (lastName != null) {
            if (firstName != null) {
                "$firstName $lastName"
            } else {
                lastName ?: "Unknown"
            }
        } else {
            firstName ?: "Unknown"
        }
    }

fun main(){
    // usage:
    val user = User(firstName = null, lastName = "Snow")
    val name = user.getFormattedName()
    val formattedName = user.userFormattedName

    println(name)
    println()
    println(formattedName)
}