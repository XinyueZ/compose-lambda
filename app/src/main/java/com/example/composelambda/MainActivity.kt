package com.example.composelambda

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.setContent
import androidx.ui.layout.Column
import androidx.ui.layout.MainAxisAlignment
import androidx.ui.layout.Row
import androidx.ui.layout.Spacing

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GetListOfText()
        }
    }
}

@Composable
fun GetListOfText() {
    Column(
        mainAxisAlignment = MainAxisAlignment.SpaceBetween,
    ) {
        Row(
            mainAxisAlignment = MainAxisAlignment.SpaceBetween,
            modifier = Spacing(all = 10.dp),
        )
        {
            Text(text = "Hello, compose 1.1")
            Text(text = "Hello, compose 1.2")
            Text(text = "Hello, compose 1.1")
        }
        Text(text = "Hello, compose 2")
        Text(text = "Hello, compose 3")
    }
}