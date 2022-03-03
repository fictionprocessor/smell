package com.example.smell

import android.util.Log
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.decodeFromJsonElement

// design how the data will be stored

// ---------------------------------------------------------
// data classes
// ---------------------------------------------------------

@Serializable
data class BlahList (
    var blahList: Collection<Blah>
)

@Serializable
data class Blah(
    var topics: MutableList<String>,
    val author: String = R.string.anon.toString(),
    var body: String = "",
    val deliveryTime: Long = Calendar.getInstance().timeInMillis,
    val randomNumber: Long = ThreadLocalRandom.current().nextLong()
)



// Blah: stores the message created by the user
// val color: Color = Color.Black,

