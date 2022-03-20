package com.example.smell

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

// Recomposition:
// The core concept is
// Recomposition happens only when an observable state change happens.
// a mutableList is NOT observable
// so, use the most useful data types when manipulating data, then...
// create results of an observable type
// supply observable type to stateless Compose ui


// TODO maybe blahs should be a set
var blahs = mutableSetOf<Blah>(mm, mm2, mm3)
var selfBlahs = getFromMasterBlah("personal")

var publicBlahs: MutableState<MutableList<Blah>> = mutableStateOf(getFromMasterBlah("public"))
var privateBlahs: MutableState<MutableList<Blah>> = mutableStateOf(getFromMasterBlah("private"))
var personalBlahs: MutableState<MutableList<Blah>> = mutableStateOf(getFromMasterBlah("personal"))



@Composable
fun addToBlahs(addThis: Blah) {
    blahs.add(addThis)
    updateAllTheBlahs()
}

//when blahs updates, update publicBlahs, privateBlahs, personalBlahs, selfBlahs
@Composable
fun updateAllTheBlahs() {

    makePublicBlahs()
    makePrivateBlahs()
    makePersonalBlahs()
    makeSelfBlahs()

}

@Composable
fun makePublicBlahs(): MutableState<MutableList<Blah>> {
    publicBlahs = remember {  mutableStateOf(getFromMasterBlah("public"))}
    return publicBlahs

}

@Composable
fun makePrivateBlahs(): MutableState<MutableList<Blah>> {
    privateBlahs = remember {  mutableStateOf(getFromMasterBlah("private"))}
    return privateBlahs
}

@Composable
fun makePersonalBlahs(): MutableState<MutableList<Blah>> {
    personalBlahs = remember {  mutableStateOf(getFromMasterBlah("personal"))}
    return personalBlahs
}

@Composable
fun makeSelfBlahs() {
    selfBlahs = getFromMasterBlah("personal")
}


// find blahs that have a chosen topic
fun getFromMasterBlah(searchFor: String): MutableList<Blah> {

    val yes: String = searchFor.lowercase().trim()

    val r = mutableListOf<Blah>()
    r.clear()

    if (yes == "public" || yes == "private" || yes == "personal") {
        return publicPrivateOrPersonal(yes)

    } else {
        return getExactTopic(yes)
    }
}


// used by getFromMasterBlah
fun getExactTopic(yes: String): MutableList<Blah> {

    val r = mutableListOf<Blah>()
    r.clear()

    for (b in blahs) {
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


    for (b in blahs) {
        for (t in b.topics) {
            if (t.startsWith(yes) and !(t.startsWith(no))) {
                r.add(b)
                break
            }
        }
    }
    return r
}


// ---------------------------
class masterBlah {}




