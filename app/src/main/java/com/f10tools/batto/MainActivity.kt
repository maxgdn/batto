package com.f10tools.batto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.f10tools.batto.domain.BatteryEvent
import com.f10tools.batto.domain.HistoricDate
import com.f10tools.batto.ui.screens.BatteryEventScreen
import com.f10tools.batto.ui.screens.BatteryEventsScreen
import com.f10tools.batto.ui.screens.HomeScreen
import com.f10tools.batto.ui.screens.StatisticsScreen
import com.f10tools.batto.ui.t.BattoTheme
import com.f10tools.batto.util.pollBattery
import com.f10tools.batto.viewmodel.BatteryEventViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val batteryEventViewModel by viewModels<BatteryEventViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BattoTheme {
                val context = LocalContext.current
                val allEventsState = batteryEventViewModel.allBatteryEventsState.subscribeAsState(initial = emptyList())
                val latestBatteryEventState = batteryEventViewModel.latestBatteryEventsState.subscribeAsState(null)

                LaunchedEffect(true) {
                    batteryEventViewModel.getAllEvents()
                }
                val allEvents = allEventsState.value

                val latestBatteryEvent = latestBatteryEventState.value

                val statisticsState = batteryEventViewModel.statisticsState.subscribeAsState(initial = null)
                val statistics = statisticsState.value

                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        Button(
                            onClick = {
                                val sample = context.pollBattery()
                                println("EVENT ${sample}")
                                batteryEventViewModel.insertBatteryEvent(sample)
//                                println(sample)
                            }
                        ) {
                            Text("Load")
                        }

                        Button(
                            onClick = {
                                //batteryEventViewModel.getBatteryEventById("")

//                                println(sample)
                            }
                        ) {
                            Text("Test By ID")
                        }

                        Button(
                            onClick = {
                                batteryEventViewModel.getLatestBatteryEvent()
                            }
                        ) {
                            Text("Load Latest")
                        }

                        var bound by remember { mutableStateOf(HistoricDate.oneWeekAgo()) }

                        Button(
                            onClick = {
                                bound = HistoricDate.oneWeekAgo()
                            }
                        ) {
                            Text("Week")
                        }

                        Button(
                            onClick = {
                                bound = HistoricDate.oneDayAgo()
                            }
                        ) {
                            Text("Day")
                        }

                        Button(
                            onClick = {
                                batteryEventViewModel.getBatteryEventStatistics(bound)
                            }
                        ) {
                            Text("Load Stats")
                        }

//                        if(allEvents.isNotEmpty()) {
//                            val last = allEvents.last()
//
//                            //GeneratedBatteryIcon(modifier = Modifier, percentage = last.batteryPercentage)
//                            Text(last.toString())
//                        }

                        if(latestBatteryEvent != null) {

                            //GeneratedBatteryIcon(modifier = Modifier, percentage = last.batteryPercentage)
                            Text(latestBatteryEvent.toString())
                        }

                        if(statistics != null) {
                            Text(statistics.toString())
                        }



//                        allEvents.reversed().forEach {
//                            Text(it.toString())
//                        }

                    }
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") { HomeScreen() }
                        composable("batteryevent") { BatteryEventScreen() }
                        composable("batteryevents") { BatteryEventsScreen() }
                        composable("stastistics") { StatisticsScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun BatteryInfoItem(batteryEvent: BatteryEvent) {

}

@Composable
fun LiveBatteryInfo() {

}

@Composable 
fun GeneratedBatteryIcon(
    modifier: Modifier,
    percentage: Float
) {
    Canvas(
        modifier = modifier
            .defaultMinSize(100.dp)
            .padding(16.dp),
    ) {
        val width = this.size.width
        val height = this.size.height

        val stroke = 4f
        val batteryPillWidth = 50f
        val batteryPillHeight = 100f

        val batteryNibWidth = 20f
        val batteryNibHeight = 10f

        val totalHeight = batteryPillHeight + batteryNibHeight

        val pillCornerRadiusSize = 5f
        val pillCornerRadius = CornerRadius(pillCornerRadiusSize,pillCornerRadiusSize)
        val pillStroke = Stroke(width = stroke, cap = StrokeCap.Round)

        val translateX = width - batteryPillWidth

        rotate(-180f) {
            translate(left = translateX, top = 0f) {
                drawRoundRect(
                    color = Color.Green,
                    topLeft = Offset(x = 0f, y = totalHeight - batteryNibHeight - stroke),
                    cornerRadius = pillCornerRadius,
                    size = Size(width = batteryPillWidth, batteryPillHeight * percentage/100)
                )
            }
        }

        drawRect(
            color = Color.Black,
            topLeft = Offset(x= (batteryPillWidth/2 - batteryNibWidth/2), y = 0f),
            size = Size(width = batteryNibWidth, height = batteryNibHeight)
        )
        drawRoundRect(
            color = Color.Black,
            topLeft = Offset(x = 0f, y= batteryNibHeight),
            cornerRadius = pillCornerRadius,
            style = pillStroke,
            size = Size(width = batteryPillWidth, height = batteryPillHeight)
        )


    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BattoTheme {
        Greeting("Android")
    }
}