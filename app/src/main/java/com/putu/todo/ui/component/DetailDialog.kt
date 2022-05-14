package com.putu.todo.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.putu.todo.utils.fDate
import com.putu.todo.utils.fTime

@Composable
fun DetailDialog(todo: String, desc: String, date: String, time: String, onDismiss: () -> Unit){
    val scrollState = rememberScrollState()

    Dialog(onDismissRequest = onDismiss){
        Card(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.wrapContentHeight().padding(top = 20.dp, bottom = 20.dp)
        ){
            Column(
                modifier = Modifier.fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 14.dp)
            ) {
                // content
                Text(text = todo, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium))

                if (desc != ""){
                    Text(text = desc, style = TextStyle(fontSize = 14.sp), modifier = Modifier.padding(top = 10.dp))
                }

                if (date != "" && time != "") {
                    Text(
                        text = "Deadline: ${fDate(date)} - ${fTime(time)}",
                        style = TextStyle(fontSize = 14.sp),
                        modifier = Modifier.padding(top = 25.dp)
                    )
                }
            }
        }
    }
}