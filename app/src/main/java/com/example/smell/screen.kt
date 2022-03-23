package com.example.smell

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState

// Jetpack Compose SUCKS
// You can't use mutable data types. Doesn't that make it useless when my data is a mutableSet?
// But what if i wrap a mutable in a state<>, or is a mutable observable inside a viewModel or LiveData?
// Maybe use mutables in the data, then create an immutable result for Compose to display?
// But you can only call composables from composables...
// But state needs twisted logic to function...
// But remember only holds a single val...
// But classes can't be composable...
// Make the ui stateless. Then move the logic somewhere else and put the data in the new place...
// Failed again.
// But maybe if I try again...
// Is it remember I need? State Hoisting? What if I pass that data as an argument?
// No. Erratic results, failures. Useless feedback. Convoluted problem. Interacting flaws.
// Did I use remember correctly? The viewModel? Is that type observable?
// Can I convert between those types? Can I wrap it in something observable? Can i iterate through that now?
// The logic needs that data, I can't just change it for Compose...
// Why did it work last time and not now? Mutables.
// Which workaround should I try?
// How many ideas do i have to test?
// Am I wrong? Maybe try again?
// i need to give up.
// can I have my month back, please?



//class screen {
//}

// Recomposition:
// The core concept is:
// Recomposition happens only when an observable state change happens.
// a mutableList is NOT observable
// so, use the most useful data types when manipulating data, then...
// create results of an observable type
// then supply observable type to stateless Compose ui

// tabs and swiping from https://www.rockandnull.com/jetpack-compose-swipe-pager/
@ExperimentalPagerApi // 1.
@Composable
fun TabsWithSwiping(smellModel: smellViewModel) {

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
            Screen(tabIndex,smellModel)
        }
    }
}

@Composable
fun Screen(num: Int, smellModel: smellViewModel){

    when (num){
        0 -> ScreenPublic(smellModel)
        1 -> ScreenPrivate(smellModel)
        2 -> ScreenPersonal(smellModel)
    }
}

// recompose when the var changes
// that only happens when the function is called and one of the parameters is the var
@Composable
fun ScreenPublic(smellModel: smellViewModel) {

    Column {

        Button(onClick = { smellModel.addToMaster(mm4)}){ //com.example.smell.sVM.addToMasterBlah(mm)}) {
            Text("jolene")
        }

        Text(
            "s.blahSetHolder.component1().toString()",
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        )
    }
}

@Composable
fun ScreenPrivate(smellModel: smellViewModel) {
    Column {
        Button(onClick = {smellModel.addToMaster(mm4)}){ //com.example.smell.sVM.addToMasterBlah(mm)}) {
            Text("jolene")
        }
        Text(
            "sVM.blahSet.toString()",
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        )
    }
}

@Composable
fun ScreenPersonal(smellModel: smellViewModel) {
    Column {
        Button(onClick = {smellModel.addToMaster(mm4)}) { // com.example.smell.sVM.addToMasterBlah(mm4)}) {
            Text("jolene")
        }
        Text(
            "add master",
            //personalBlahs.toString(),
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        )
    }
}



