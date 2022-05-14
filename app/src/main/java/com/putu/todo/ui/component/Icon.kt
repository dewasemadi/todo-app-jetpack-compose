package com.putu.todo.ui.component

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DeleteIcon(modifier: Modifier = Modifier) {
    Icon(Icons.Default.Delete, "Delete", modifier = modifier, Color.White)
}

@Composable
fun MenuIcon(modifier: Modifier = Modifier) {
    Icon(Icons.Default.Menu, "Menu", modifier, Color.White)
}

@Composable
fun AddIcon(modifier: Modifier = Modifier) {
    Icon(Icons.Default.Add, "Add new todo", modifier, Color.White)
}