package com.example.smell

import android.util.Log
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*
import java.util.concurrent.ThreadLocalRandom

// organise the data here for display in the user interface



// how many hours old is the message
fun staleness(startTime: Long): Int {
    val nowTime: Long = Calendar.getInstance().timeInMillis
    val stale: Long = nowTime - startTime
    val HOUR: Long = 3600000
    val hourTime = stale / HOUR

    return hourTime.toInt()
}


// ----------------------------------------------------------------------------------
// serialize
// ----------------------------------------------------------------------------------



fun serializeToSend(b: MutableList<Blah>): ByteArray{

    // Blah
    Log.d(TAG, "start: " + b.toString())

    // Blah to JSON String
    val jsonString = Json.encodeToString(b)
    Log.d(TAG, "Json.encodeToString: " + jsonString)

    // String to ByteArray
    val jsonB = jsonString.encodeToByteArray()

    return jsonB

}

fun fromSenderToMasterBlah(jsonB: ByteArray?){

    if (jsonB == null) {
        return
    }

    // String to Blah
    val jsonD = jsonB.toString(Charsets.UTF_8)
    Log.d(TAG, "decoded back to UTF8 string: " + jsonD)

    val jsonE = Json.decodeFromString<Collection<Blah>>(jsonD)

    masterBlah.addAll(jsonE)


}


// ---------------------------------------------------------
// sample data
// ---------------------------------------------------------
val mm: Blah = Blah(
    topics = mutableListOf("#public", "#party", "#police"),
    author = "author",
    body = "message",
    deliveryTime = Calendar.getInstance().timeInMillis,
    randomNumber = ThreadLocalRandom.current().nextLong()
)

val mm2: Blah = Blah(
    topics = mutableListOf("##sexy", "##movie", "##soccer"),
    author = "mf",
    body = " nothing nothing nothing nothing nothing nothing nothing nothing nothing nothing nothing",
    deliveryTime = 1645577544311,
    randomNumber = ThreadLocalRandom.current().nextLong()
)

val mm3: Blah = Blah(
    topics = mutableListOf("@joe"),
    "me",
    "here is the message",
    1635577544296,
    randomNumber = ThreadLocalRandom.current().nextLong()
)




// most important var: all the blah are in the masterBlah
var masterBlah = mutableListOf<Blah>(mm, mm2, mm3)



