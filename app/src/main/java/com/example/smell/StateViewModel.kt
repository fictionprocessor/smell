package com.example.smell

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// store the master list of messages (masterBlah) here in the ViewModel

class StateViewModel: ViewModel() {

    // blahMaster = THE record of maassages
    // private var _blahMaster = MutableLiveData(listOf<Blah>())
    //val blahMaster: LiveData<List<Blah>> = _blahMaster

    var blahMaster = mutableStateListOf<Blah>()
        private set
    //     var todoItems = mutableStateListOf<TodoItem>()
    //        private set


    // what to search blahMaster for
    private var currentSearch by mutableStateOf("")

    // event: addItem
    fun addItem(item: Blah) {
        /* ... */
        // blahMaster.add(item)
    }

    // event: removeItem
    fun removeItem(item: Blah) {
        /* ... */
    }

    fun removeFromBlahMaster(id : Long){
        for (b in blahMaster){
            if (b.randomNumber == id){
                blahMaster.remove(b)
            }
        }
    }





}


