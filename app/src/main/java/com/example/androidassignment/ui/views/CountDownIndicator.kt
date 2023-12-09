package com.example.androidassignment.ui.views

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.example.androidassignment.ui.theme.Green
import com.example.androidassignment.ui.theme.size14
import com.example.androidassignment.ui.theme.size180


@Composable
fun CountDownIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    time: String,
    size: Dp,
    stroke: Dp
) {

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = "Count down indicator",
    )

    Column(modifier = modifier) {
        Box {

            CircularProgressIndicatorBackGround(
                modifier = Modifier
                    .height(size)
                    .width(size),
            )

            CircularProgressIndicator(
                progress = animatedProgress.div(100),
                modifier = Modifier
                    .height(size)
                    .width(size),
                color = Green,
                strokeWidth = stroke,
            )

            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = time,
                    color = Green,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(21f, TextUnitType.Sp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCountDownIndicator() {
    CountDownIndicator(progress = 0.4f, time = "00:10:23", size = size180, stroke = size14)
}