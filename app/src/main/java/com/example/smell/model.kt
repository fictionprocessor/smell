package com.example.smell

import android.util.Log
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.encodeToJsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import java.util.*
import java.util.concurrent.ThreadLocalRandom

// organise the data here for display in the user interface


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
var masterBlah = mutableListOf<Blah>()

fun addBlahToMasterBlah(blah: Blah) {
    masterBlah.add(blah)

}


fun removeFromMasterBlah(id : Long){
    for (b in masterBlah){
        if (b.randomNumber == id){
            masterBlah.remove(b)
        }
    }
}

fun addAllToMasterBlah(a : Collection<Blah>){
    masterBlah.addAll(a)
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

    var r = mutableListOf<Blah>()
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

    var r = mutableListOf<Blah>()
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

    var r = mutableListOf<Blah>()
    r.clear()

    var yes: String = "#"
    var no: String = "##"

    val option = option.lowercase()

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


// how many hours old is the message
fun staleness(startTime: Long): Int {
    val nowTime: Long = Calendar.getInstance().timeInMillis
    val stale: Long = nowTime - startTime
    val HOUR: Long = 3600000
    val hourTime = stale / HOUR

    return hourTime.toInt()
}

// TODO makeBlah needs author, needs topics validation

fun makeBlah(top: String, text: String, prefix: String){

    val topicList = validateTopics(top, prefix)

    val randomInteger = (0..99).shuffled().first().toString()
    val name: String = "anon$randomInteger"

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

    var topicList = mutableListOf<String>()
    // get string
    // split string into single words
    // prefage words with #, ## or @
    // make into list
    // return
    Log.d(TAG, topicString)
    var splitTopicString = topicString.split(" ")

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
