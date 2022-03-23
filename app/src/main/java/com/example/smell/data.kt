package com.example.smell

import kotlinx.serialization.Serializable
import java.util.*
import java.util.concurrent.ThreadLocalRandom

// design how the data will be stored

// ---------------------------------------------------------
// data classes
// ---------------------------------------------------------

@Serializable
data class Blah(
    var topics: MutableList<String>,
    val author: String = authorName(),
    var body: String = "",
    val deliveryTime: Long = Calendar.getInstance().timeInMillis,
    val randomNumber: Long = ThreadLocalRandom.current().nextLong()
)



// TODO perhaps UUID.random instead of random number?
// TODO author name



