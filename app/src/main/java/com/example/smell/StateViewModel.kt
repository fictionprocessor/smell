package com.example.smell

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

// store the master list of messages (masterBlah) here in the ViewModel

class StateViewModel : ViewModel() {

    var masterBlah = mutableStateListOf<Blah>(mm)
        private set




}




