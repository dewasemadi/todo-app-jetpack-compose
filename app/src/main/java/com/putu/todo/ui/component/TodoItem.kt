package com.putu.todo.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.putu.todo.ui.theme.Grey500
import com.putu.todo.utils.fDate
import com.putu.todo.utils.fTime

@Composable
fun TodoItem(
    todo: String,
    desc: String,
    date: String,
    time: String,
    isChecked: Boolean,
    onChecked: (Boolean) -> Unit,
    onEdited: () -> Unit,
) {
    val isShowDetail = remember { mutableStateOf(false) }

    if (isShowDetail.value) {
        DetailDialog(todo, desc, date, time) {
            isShowDetail.value = false
        }
    }

    val myTextDecorationStyle = when {
        isChecked -> TextDecoration.LineThrough
        else -> TextDecoration.None
    }

    val myColorStyle = when {
        isChecked -> Grey500
        else -> Color.Black
    }

    Box(modifier = Modifier.background(Color.White).clickable { isShowDetail.value = true }) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp, end = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onChecked,
                modifier = Modifier.padding(start = 4.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (date != "" && time != "") {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            text = todo, style = TextStyle(
                                textDecoration = myTextDecorationStyle,
                                color = myColorStyle
                            ),
                        )

                        Text(
                            text = "Deadline: ${fDate(date)} - ${fTime(time)}", style = TextStyle(
                                textDecoration = myTextDecorationStyle,
                                color = myColorStyle
                            )
                        )
                    }
                } else {
                    Text(
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        text = todo, style = TextStyle(
                            textDecoration = myTextDecorationStyle,
                            color = myColorStyle
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                IconButton(onClick = onEdited) {
                    Icon(Icons.Default.Edit, "Edit", tint = Color.Gray)
                }
            }
        }
    }
}