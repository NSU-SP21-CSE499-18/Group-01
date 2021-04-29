package com.ece.cse499A.sec18.group01.kotlinforjavadevs.kotlinClasses

/**
 * Kotlin conversion of javaClasses/User.java class
 *
 * 'data' => auto generated getter, setters, equals(), hashCode(), toString() functions
 *           allows you to compare object value equality using '==' operator
 *           (referential equality can be checked with '===')
 *
 * 'var' => mutable, use for variables that would have both getters and setters in java
 *
 * 'val' => immutable, similar to final keyword in Java, use for variables that only have getters
 *
 * '?' => nullable, for variables that can be null
 *
 */

data class User(var firstName: String?, var lastName: String?)