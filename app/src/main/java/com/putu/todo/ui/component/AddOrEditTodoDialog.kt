package com.putu.todo.ui.component

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.putu.todo.R
import com.putu.todo.utils.fDate
import com.putu.todo.utils.fTime

@Composable
fun AddOrEditTodoDialog(
    todo: String,
    desc: String,
    date: String,
    time: String,
    isChecked: Boolean,
    confirmText: String,
    onCheckedChange: (Boolean) -> Unit,
    onTodoChange: (String) -> Unit,
    onDescChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onTimeChange: (String) -> Unit,
    onConfirmClicked: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var year = 0; var month = 0; var day = 0; var hour = 0; var minute = 0

    try {
        // index out of bound exception
        val initialDate = date.split("/")
        val initialTime = time.split(":")

        year = initialDate[2].toInt()
        month = initialDate[1].toInt() - 1
        day = initialDate[0].toInt()
        hour = initialTime[0].toInt()
        minute = initialTime[1].toInt()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    val datePickerDialog = DatePickerDialog(
        context,
        R.style.DialogTheme,
        { _: DatePicker, _year: Int, _month: Int, _day: Int ->
            onDateChange("$_day/${_month + 1}/$_year")
        }, year, month, day
    )

    val timePickerDialog = TimePickerDialog(
        context,
        R.style.DialogTheme,
        {_, _hour : Int, _minute: Int ->
            onTimeChange("$_hour:$_minute")
        }, hour, minute, true
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(shape = MaterialTheme.shapes.large, modifier = Modifier.wrapContentHeight()) {
            Column(modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 14.dp)) {

                // content
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        value = todo,
                        onValueChange = onTodoChange,
                        label = { Text(text = "To-do") }
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth().padding(top = 14.dp).height(85.dp),
                        maxLines = 2,
                        value = desc,
                        onValueChange = onDescChange,
                        label = { Text(text = "Description") }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        OutlinedTextField(
                            value = fDate(date),
                            onValueChange = onDateChange,
                            modifier = Modifier
                                .weight(1.4f)
                                .clickable {
                                   if (isChecked) {
                                       datePickerDialog.show()
                                   }
                                },
                            enabled = false,
                            leadingIcon = {
                                Icon(Icons.Default.CalendarToday, "Calendar")
                            },
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        OutlinedTextField(
                            value = fTime(time),
                            onValueChange = onTimeChange,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    if (isChecked) {
                                        timePickerDialog.show()
                                    }
                                },
                            enabled = false,
                            leadingIcon = {
                                Icon(Icons.Default.AccessTime, "Time")
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ){
                        Checkbox(checked = isChecked, onCheckedChange = onCheckedChange)
                        Text(text = "Deadline", style = TextStyle(color = Color.Gray))
                    }
                }

                // button
                Row(modifier = Modifier.fillMaxWidth().padding(top = 14.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceAround) {
                    OutlinedButton(modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent, contentColor = Color.Black),
                        onClick = onDismiss) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(modifier = Modifier.weight(1f), elevation = null, onClick = onConfirmClicked) {
                        Text(text = confirmText)
                    }
                }
            }
        }
    }
}