package com.putu.todo.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun Banner(greeting: String, motivation: String, image: Int) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (banner, greeting_const, motivation_const) = createRefs()
        val topGuideline = createGuidelineFromTop(0.01f)

        Image(
            painter = painterResource(image),
            contentDescription = "Banner",
            modifier = Modifier.constrainAs(banner) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = greeting,
            color = Color.White,
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
            modifier = Modifier.constrainAs(greeting_const) {
                top.linkTo(topGuideline)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = motivation,
            color = Color.White,
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Light),
            modifier = Modifier.constrainAs(motivation_const) {
                top.linkTo(greeting_const.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}