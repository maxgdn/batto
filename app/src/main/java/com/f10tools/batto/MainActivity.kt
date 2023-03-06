package com.f10tools.batto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.f10tools.batto.domain.BatteryEvent
import com.f10tools.batto.ui.theme.BattoTheme
import com.f10tools.batto.util.pollBattery
import com.f10tools.batto.viewmodel.BatteryEventViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observer

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val batteryEventViewModel by viewModels<BatteryEventViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BattoTheme {
                val context = LocalContext.current
                val allEventsState = batteryEventViewModel.allBatteryEventsState.subscribeAsState(initial = emptyList())

                LaunchedEffect(true) {
                    batteryEventViewModel.getAllEvents()
                }
                val allEvents = allEventsState.value
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        Button(
                            onClick = {
                                val sample = context.pollBattery()
                                batteryEventViewModel.insertBatteryEvent(sample)
//                                println(sample)
                            }
                        ) {
                            Text("Load")
                        }

                        if(allEvents.isNotEmpty()) {
                            val last = allEvents.last()

                            GeneratedBatteryIcon(modifier = Modifier, percentage = last.batteryPercentage)
                        }

                        /*allEvents.reversed().forEach {
                            Text(it.toString())
                        }*/
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
            .size(100.dp)
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