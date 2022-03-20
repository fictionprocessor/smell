package com.example.smell

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState

class screen {
}

val mB = com.example.smell.masterBlah()
// PLAN
// 1. make screen stateless. store no variables here.
// 2. have a long list of arguments passed to each screen for display.
// Later: all the events trigger the downflow of those arguments from higher up.

// tabs and swiping from https://www.rockandnull.com/jetpack-compose-swipe-pager/
@ExperimentalPagerApi // 1.
@Composable
fun TabsWithSwiping() {
    var tabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Public", "Private", "Personal")
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
            Screen(tabIndex)
        }
    }
}

@Composable
fun Screen(num : Int){
    when (num){ 
        0 -> {
            ScreenPublic()}
        1 -> {
            ScreenPrivate()}
        2 -> {
            ScreenPersonal()}
    }
}

@Composable
fun ScreenPublic(){
    Column() {


    Button(onClick = {blahs.add(mm4)})
    {
        Text("jolene")
    }

    Text(
        blahs.toString(),
        modifier = Modifier
    )
}
}

@Composable
fun ScreenPrivate(){
    Text(
        privateBlahs.toString(),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
}

@Composable
fun ScreenPersonal(){
    Button(onClick = { blahs.add(mm4)}) {
        Text("Standard")
    }
    Text(
        personalBlahs.value.toString(),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
}



