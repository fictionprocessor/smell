package com.example.smell

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// store the master list of messages (masterBlah) here in the ViewModel

class StateViewModel : ViewModel() {

    var masterBlah = mutableStateListOf<Blah>()
        private set


}

//    var todoItems = mutableStateListOf<TodoItem>()
//        private set


