package com.example.smell

import android.content.res.Resources
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview

//class widget {
//
//}

//var click: String = ""
//var click by remember { mutableStateOf("") }



// ------------------------------------------------------------------------------------------
// BITS
// ------------------------------------------------------------------------------------------

@Composable
fun MessageCard(message: Blah) {


    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp,
        modifier = Modifier.padding(all = 10.dp)
    ) {

        Column(modifier = Modifier.padding(all = 10.dp)) {
            Text(message.topics.toString())
            Text(message.author, fontWeight = FontWeight.W700)
            Text(message.body)
            Text(staleness(message.deliveryTime).toString() + " hrs ago")
            Text("randomNumber: " + message.randomNumber.toString())
        }
    }
}


@Composable
fun NotesColumn(notesForColumn: MutableList<Blah>) {

    Column()
    {
        notesForColumn.forEach { item ->
            MessageCard(message = item)
        }
    }

}



@Composable
fun ButtonWithRoundCornerShape(label: String) {

    Button(onClick = { Log.d(TAG, label )  }, shape = RoundedCornerShape(20.dp)) {
        Text(text = label)
    }
}

//ButtonRow(topicsForRow = publicButtonList as MutableList<String>)
@Composable
fun ButtonRow(publicButtonList: MutableList<String>) {
    publicButtonList.add("#")

    Row()
    {
        publicButtonList.forEach { item ->
            ButtonWithRoundCornerShape(label = item)
        }
    }

}



// ---------------------------------



//-------------------------------------
// logic
// ====================================
// A stateless composable is a composable that cannot directly change any state.
// State hoisting is the pattern of moving state up to make a component stateless
//
// in the composable: add two parameters:
//
//    value: T – the current value to display
//    onValueChange: (T) -> Unit – an event that requests the value to change, where T is the proposed new value





















