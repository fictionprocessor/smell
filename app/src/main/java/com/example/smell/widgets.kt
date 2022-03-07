package com.example.smell

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class widget {

}





@Composable
fun ComposeNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "public_screen"
    ) {
        composable("public_screen") {
            var publicBlahs: Collection<Blah> = getFromMasterBlah("public")

            PublicScreen(navController = navController, publicBlahs )
        }
        composable("private_screen") {
            var privateBlahs: Collection<Blah> = getFromMasterBlah("private")

            PrivateScreen(navController = navController, privateBlahs)
        }
        composable("personal_screen") {
            var personalBlahs: Collection<Blah> = getFromMasterBlah("personal")

            PersonalScreen(navController = navController, personalBlahs)
        }
    }
}


// ------------------------------------------------------------------------------------------
// PUBLIC SCREEN
// ------------------------------------------------------------------------------------------

@Composable
fun PublicScreen(navController: NavController, publicBlahs: Collection<Blah>) {

    var publicFocus: String = "public"

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --------------------------

        var publicTopics by remember { mutableStateOf("") }

        OutlinedTextField(
            value = publicTopics,
            onValueChange = { publicTopics = it }
        )

        // -------------------------

        var publicText by remember { mutableStateOf("") }

        OutlinedTextField(
            value = publicText,
            onValueChange = { publicText = it },
            label = { Text("message...") }
        )


        // --------------------------

        Button(
            onClick = { makeBlah(publicTopics, publicText, "#") },
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

        ButtonWithRoundCornerShape("#")
        //var publicButtonList = mutableListOf("#see", "#do", "#wait")
        var publicButtonList = getPublicTopics()
        ButtonRow(publicButtonList)

        // --------------------------

        //NotesColumn(notesForColumn = getFromMasterBlah("public"))
        NotesColumn(notesForColumn = publicBlahs as MutableList<Blah>)

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
fun PrivateScreen(navController: NavController, privateBlahs: Collection<Blah>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        var privateTopics by remember { mutableStateOf("") }

        OutlinedTextField(
            value = privateTopics,
            onValueChange = { privateTopics = it }
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

        ButtonWithRoundCornerShape("##")

        // --------------------------
        var privateNotesColumnWord by remember { mutableStateOf("public") }
        NotesColumn(notesForColumn = getFromMasterBlah(privateNotesColumnWord))

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
fun PersonalScreen(navController: NavController, personalBlahs: Collection<Blah>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {


        var personalTopics by remember { mutableStateOf("") }

        OutlinedTextField(
            value = personalTopics,
            onValueChange = { personalTopics = it }
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

        ButtonWithRoundCornerShape("@")

        // --------------------------

        // NotesColumn(notesForColumn = getFromMasterBlah("personal"))
        NotesColumn(notesForColumn = personalBlahs  as MutableList<Blah>)

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
    Button(onClick = {}, shape = RoundedCornerShape(20.dp)) {
        Text(text = label)
    }
}

//ButtonRow(topicsForRow = publicButtonList as MutableList<String>)
@Composable
fun ButtonRow(publicButtonList: MutableList<String>) {

    Row()
    {
        publicButtonList.forEach { item ->
            ButtonWithRoundCornerShape(label = item)
        }
    }

}




















