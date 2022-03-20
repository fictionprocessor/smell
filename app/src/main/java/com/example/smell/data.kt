package com.example.smell

import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.Recomposer.*
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
    val author: String = R.string.anon.toString(),
    var body: String = "",
    val deliveryTime: Long = Calendar.getInstance().timeInMillis,
    val randomNumber: Long = ThreadLocalRandom.current().nextLong()
)

// TODO perhaps UUID.random
// TODO author name



