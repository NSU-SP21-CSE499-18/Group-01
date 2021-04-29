package com.ece.cse499A.sec18.group01.kotlinforjavadevs.kotlinClasses

import java.util.*

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
 */
object Repository {
    private val users = mutableListOf<User>()
    fun getUsers(): List<User> {
        return users
    }

    val formattedUserNames: List<String>
        get() {
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
        }

    // keeping the constructor private to enforce the usage of getInstance
    init {
        val user1 = User("Jane", "")
        val user2 = User("John", null)
        val user3 = User("Anne", "Doe")

        users.add(user1)
        users.add(user2)
        users.add(user3)
    }
}