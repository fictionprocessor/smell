package com.example.smell

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class screen {
}


// ------------------------------------
// todoSctivityScreen
// ------------------------------------
@Composable
fun ComposeNavigation(stateViewModel: StateViewModel) {

    //val items = listOf<TodoItem>()
    val blahMaster = mutableListOf<Blah>()
    // var masterBlah = mutableListOf<Blah>(mm, mm2, mm3)


    Log.d(TAG, blahMaster.toString())

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "public_screen",


        ) {
        composable("public_screen") {
            PublicScreen(navController = navController, blahMaster)
        }
        composable("private_screen") {
            PrivateScreen(navController = navController)
        }
        composable("personal_screen") {
            PersonalScreen(navController = navController)
        }
    }
}


// ------------------------------------------------------------------------------------------
// PUBLIC SCREEN
// ------------------------------------------------------------------------------------------

@Composable
fun PublicScreen(navController: NavController, blahs: MutableList<Blah>) {

    Log.d(TAG, "blahMaster in PublicScreen: " + blahs)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        // --------------------------

        var publicTopics by remember { mutableStateOf("#") }

        OutlinedTextField(
            value = publicTopics,
            onValueChange = { publicTopics = it }
        )


        // -------------------------

        var publicText by remember { mutableStateOf("") }

        OutlinedTextField(
            value = publicText,
            onValueChange = { publicText = it }
        )


        // --------------------------

        Button(
            //onClick = { makeBlah(
            //  //
            //  publicTextEntry(),
            //  "#"
            //) },
            onClick = {  }

        )
        {
            // Inner content including an icon and a text label
            Icon(
                Icons.Filled.Place,
                contentDescription = "Face",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("searching...")
        }



        // --------------------------

        Divider()


        // --------------------------

        ButtonRow(getThePublicTopics())

        // --------------------------

        var click by remember { mutableStateOf("public") }
        NotesColumn(notesForColumn = blahs)

        // --------------------------

        Text(
            text = "First Screen\n" +
                    "Click me to go to Private Screen",
            color = Color.Green,
            style = TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier
                .padding(24.dp)
                .clickable(onClick = {
                    // this will navigate to second screen
                    navController.navigate("private_screen")
                })
        )
    }
}



// ------------------------------------------------------------------------------------------
// PRIVATE SCREEN
// ------------------------------------------------------------------------------------------

@Composable
fun PrivateScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // -------------------------

        var privateTopics by remember { mutableStateOf("##") }

        OutlinedTextField(
            value = privateTopics,
            onValueChange = { privateTopics = it },
        )


        // --------------------------


        var privateText by remember { mutableStateOf("") }

        OutlinedTextField(
            value = privateText,
            onValueChange = { privateText = it },
            label = { Text("message...") }
        )


        // --------------------------


        Button(
            onClick = { makeBlah(privateTopics, privateText, "##") },
            //enabled = onOff,

        )
        {
            // Inner content including an icon and a text label
            Icon(
                Icons.Filled.ThumbUp,
                contentDescription = "Place",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("searching...")
        }


        // --------------------------

        Divider()


        // --------------------------

        NotesColumn(notesForColumn = getFromMasterBlah("private"))

        // --------------------------

        Text(
            text = "Second Screen\n" +
                    "Click me to go to Personal Screen",
            color = Color.Magenta,
            style = TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier.clickable(onClick = {
                // this will navigate to third screen
                navController.navigate("personal_screen")
            })
        )
    }
}


// ------------------------------------------------------------------------------------------
// PERSONAL SCREEN
// ------------------------------------------------------------------------------------------

@Composable
fun PersonalScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {


        var personalTopics by remember { mutableStateOf("@") }

        OutlinedTextField(
            value = personalTopics,
            onValueChange = { personalTopics = it },
        )


        // --------------------------


        var personalText by remember { mutableStateOf("") }

        OutlinedTextField(
            value = personalText,
            onValueChange = { personalText = it },
            label = { Text("message...") }
        )


        // --------------------------


        Button(
            onClick = { makeBlah(personalTopics, personalText, "@") },
            //enabled = onOff,
        )
        {
            // Inner content including an icon and a text label
            Icon(
                Icons.Filled.Face,
                contentDescription = "Face",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("searching...")
        }


        // --------------------------

        Divider()

        // --------------------------



        // NotesColumn(notesForColumn = getFromMasterBlah("personal"))
        NotesColumn(notesForColumn = getFromMasterBlah("personal"))

        // --------------------------

        Text(
            text = "Third Screen\n" +
                    "Click me to go to Public Screen",

            color = Color.Red,
            style = TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier.clickable(onClick = {
                // this will navigate to first screen
                navController.navigate("public_screen")
            })
        )
    }
}
