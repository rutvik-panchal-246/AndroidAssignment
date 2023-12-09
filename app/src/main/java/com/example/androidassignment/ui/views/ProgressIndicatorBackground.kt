package com.example.androidassignment.ui.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.androidassignment.ui.theme.Grey
import com.example.androidassignment.ui.theme.size120
import com.example.androidassignment.ui.theme.size14

@Composable
fun CircularProgressIndicatorBackGround(
    modifier: Modifier = Modifier,
    color: Color = Grey,
    stroke: Dp = size14
) {
    val style = with(LocalDensity.current) { Stroke(stroke.toPx()) }

    Canvas(modifier = modifier, onDraw = {

        val innerRadius = (size.minDimension - style.width)/2

        drawArc(
            color = color,
            startAngle = 0f,
            sweepAngle = 360f,
            topLeft = Offset(
                (size / 2.0f).width - innerRadius,
                (size / 2.0f).height - innerRadius
            ),
            size = Size(innerRadius * 2, innerRadius * 2),
            useCenter = false,
            style = style
        )

    })
}


@Preview(showBackground = true)
@Composable
fun PreviewProgressIndicatorBackGround() {
    CircularProgressIndicatorBackGround(modifier = Modifier
        .width(size120)
        .height(size120))
}