package com.example.smell


// store the main data here
// add functions to manipulate the main data here
// give variables values here
// have the results flow down to stateless ui


class masterBlah {

    var blahs = mutableListOf<Blah>(mm, mm2, mm3)


    fun add(noteToAdd: Blah){
        blahs.add(noteToAdd)
    }

    fun addAll(listNotesToAdd: List<Blah>){
        blahs.addAll(listNotesToAdd)
    }

    var publicBlahs = getFromMasterBlah("public")
    var privateBlahs = getFromMasterBlah("private")
    var personalBlahs = getFromMasterBlah("personal")

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




}