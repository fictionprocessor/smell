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

/*


fun removeFromMasterBlah(id : Long){
    for (b in masterBlah){
        if (b.randomNumber == id){
            masterBlah.remove(b)
        }
    }
}

fun removeStaleBlah(){
    for (a in masterBlah){
        if (staleness(a.deliveryTime) > 23){
            masterBlah.remove(a)
        }
    }
}


// find blahs that have a chosen topic
fun getFromMasterBlah(searchFor: String): MutableList<Blah> {


    val yes: String = searchFor.lowercase().trim()

    val r = mutableListOf<Blah>()
    r.clear()


    if (yes == "public" || yes == "private" || yes == "personal"){
        return publicPrivateOrPersonal(yes)

    }
    else{
         return getExactTopic(yes)
    }
}


// used by getFromMasterBlah
fun getExactTopic(yes: String): MutableList<Blah>{

    val r = mutableListOf<Blah>()
    r.clear()

    for (b in masterBlah) {
        for (t in b.topics) {
            if (t.equals(yes)) {
                r.add(b)
                break
            }

        }
    }
    return r
}


// used by getFromMasterBlah
fun publicPrivateOrPersonal(option: String): MutableList<Blah> {

    val r = mutableListOf<Blah>()
    r.clear()

    var yes = "#"
    var no = "##"

    option.lowercase()

    if (option.contains("personal")) {
        yes = "@"
        no = "#"
    }

    if (option.contains("private")) {
        yes = "##"
        no = "###"
    }

    if (option.contains("public")) {
        yes = "#"
        no = "##"
    }


    for (b in masterBlah) {
        for (t in b.topics) {
            if (t.startsWith(yes) and !(t.startsWith(no))) {
                r.add(b)
                break
            }
        }
    }
    return r
}




// TODO makeBlah needs author, needs topics validation

fun makeBlah(top: String, text: String, prefix: String){

    val topicList = validateTopics(top, prefix)

    val randomInteger = (0..99).shuffled().first().toString()
    val name = "anon$randomInteger"

    val shit = Blah(
        topics = topicList,
        author = name,
        body = text,
        deliveryTime = Calendar.getInstance().timeInMillis,
        randomNumber = ThreadLocalRandom.current().nextLong()

    )

    masterBlah.add(shit)

}

fun validateTopics(topicString: String, prefix: String): MutableList<String>{

    val topicList = mutableListOf<String>()
    // get string
    // split string into single words
    // prefage words with #, ## or @
    // make into list
    // return
    Log.d(TAG, topicString)
    val splitTopicString = topicString.split(" ")

    for (t in splitTopicString){
        if (t.startsWith("#") || t.startsWith("@")) {
            topicList.add(t)
        }
        else
        {
            topicList.add(prefix + t)
        }

    }
    return topicList
}


fun getThePublicTopics():MutableList<String>{

    val r = mutableListOf<String>()
    r.clear()

    val yes = "#"
    val no = "##"

    for (b in masterBlah) {
        for (t in b.topics) {
            if (t.startsWith(yes) and !(t.startsWith(no))) {
                r.add(t)

            }
        }
    }
    return r
}
*/
// ----------------------------------------------------------------
// -------------- STATE FOR COMPOSE -------------------------------
// ----------------------------------------------------------------
//State hoisting
//
//State hoisting in Compose is a pattern of moving state to a composable's caller to make a composable stateless.
// The general pattern for state hoisting in Jetpack Compose is to replace the state variable with two parameters:
//
//    value: T: the current value to display
//    onValueChange: (T) -> Unit: an event that requests the value to change, where T is the proposed new value
//



// By hoisting the state out of HelloContent, it's easier to reason about the composable,
// reuse it in different situations, and test. HelloContent is decoupled from how its state is stored.
// Decoupling means that if you modify or replace HelloScreen, you don't have to change how
// HelloContent is implemented.



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

    // ByteArray back to String
    //val jsonC = jsonB.contentToString()
    //Log.d(TAG, "ByteArray: " + jsonC)

    // String to Blah
    val jsonD = jsonB.toString(Charsets.UTF_8)
    Log.d(TAG, "decoded back to UTF8 string: " + jsonD)

    val jsonE = Json.decodeFromString<Collection<Blah>>(jsonD)
    // Blah
    masterBlah.addAll(jsonE)

    //val jsonE = Json.decodeFromString()

    //val jsonE = Json.decodeFromString(jsonB.toString(Charsets.UTF_8))
    //val jsonE: BlahList = Json.decodeFromString(jsonD)
    //val jsonE: BlahList  = Json.decodeFromString(jsonD)
    //Log.d(TAG, "back to start: " + jsonE.toString())

    // println(byteArray.contentToString()) // [72, 101, 108, 108, 111]
    //println(byteArray.toString(charset)) // Hello

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



