package com.putu.todo.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun SimpleDialog(title: String, desc: String, confirmText: String, onConfirmClicked: () -> Unit, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(shape = MaterialTheme.shapes.large, modifier = Modifier.wrapContentHeight()) {
            Column(modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 14.dp)) {

                // content
                Text(text = title, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium))
                Text(text = desc, style = TextStyle(fontSize = 14.sp), modifier = Modifier.padding(top = 10.dp))

                // button
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 30.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    OutlinedButton(modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent, contentColor = Color.Black),
                        onClick = onDismiss) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(modifier = Modifier.weight(1f),
                        elevation = null,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red, contentColor = Color.White),
                        onClick = onConfirmClicked) {
                        Text(text = confirmText)
                    }
                }
            }
        }
    }
}