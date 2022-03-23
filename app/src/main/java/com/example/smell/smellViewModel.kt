package com.example.smell

import androidx.lifecycle.ViewModel
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class smellViewModel : ViewModel() {

    fun addToMaster(shit: Blah) {
        // something
        return
    }

    // how many hours old is the message
    fun staleness(startTime: Long): Int {
        val nowTime: Long = Calendar.getInstance().timeInMillis
        val stale: Long = nowTime - startTime
        val hour: Long = 3600000
        val hourTime = stale / hour

        return hourTime.toInt()
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

    val mm4: Blah = Blah(
        topics = mutableListOf("@jolene", "#jolene", "##jolene"),
        author = authorName(),
        body = "jolene, jolene, jolene, jolene",
        deliveryTime = 1635577544296,
        randomNumber = ThreadLocalRandom.current().nextLong()
    )




// most important var: all the blah are in the masterBlah
// var masterBlah = mutableListOf(mm, mm2, mm3)



    fun authorName(): String {
        // should return the name the user asks for
        val randomInteger = (0..99).shuffled().first().toString()
        return "anon$randomInteger"
    }

}