package com.example.smell

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState

class screen {
}

// PLAN
// 1. make screen stateless. store no variables here.
// 2. have a long list of arguments passed to each screen for display.
// Later: all the events trigger the downflow of those arguments from higher up.

// tabs and swiping from https://www.rockandnull.com/jetpack-compose-swipe-pager/
@ExperimentalPagerApi // 1.
@Preview
@Composable
fun tabsWithSwiping() {
    var tabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Public", "Personal", "Private")
    val pagerState = rememberPagerState() // 2.
    Column {
        TabRow(selectedTabIndex = tabIndex,
            indicator = { tabPositions -> // 3.
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(
                        pagerState,
                        tabPositions
                    )
                )
            }) {
            tabTitles.forEachIndexed { index, title ->
                Tab(selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    text = { Text(text = title) })
            }
        }
        HorizontalPager(
            // 4.
            count = tabTitles.size,
            state = pagerState,
        )
        { tabIndex ->
            screen(tabIndex)
        }
    }
}

@Composable
fun screen(num : Int){
    when (num){ 
        0 -> {
            screenPublic(num = num, masterBlah)}
        1 -> {
            screenPrivate(num = num, masterBlah)}
        2 -> {
            screenPrivate(num = num, masterBlah)}
    }
}

@Composable
fun screenPublic(num : Int, blahs : MutableList<Blah>){
    Text(
        "public layout here",
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
}

@Composable
fun screenPrivate(num : Int, blahs : MutableList<Blah>){
    Text(
        blahs.toString(),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
}

@Composable
fun screenPersonal(num : Int, blahs : MutableList<Blah>){
    Text(
        blahs.toString(),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
}



/*
@Composable
fun ComposeNavigation(stateViewModel: StateViewModel) {

    //val items = listOf<TodoItem>()
    // val blahMaster = mutableListOf<Blah>()
    // var masterBlah = mutableListOf<Blah>(mm, mm2, mm3)



    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "public_screen",


        ) {
        composable("public_screen") {
            PublicScreen(navController = navController)
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
fun PublicScreen(navController: NavController) {

    //Log.d(TAG, "blahMaster in PublicScreen: " + blahs)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

/*
*/
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
/*
*/
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

/*
*/
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


*/
