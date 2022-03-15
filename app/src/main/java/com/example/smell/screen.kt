package com.example.smell

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
            screenPublic()}
        1 -> {
            screenPrivate()}
        2 -> {
            screenPrivate()}
    }
}

@Composable
fun screenPublic(){
    Text(
        "public layout here",
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
}

@Composable
fun screenPrivate(){
    Text(
        "private",
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
}

@Composable
fun screenPersonal(){
    Text(
        "personal",
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
}



