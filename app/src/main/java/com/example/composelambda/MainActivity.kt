package com.example.composelambda

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.Context
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.setContent
import androidx.ui.foundation.DrawImage
import androidx.ui.graphics.Image
import androidx.ui.graphics.imageFromResource
import androidx.ui.layout.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuildFrontPage(this)
        }
    }
}

@Composable
fun BuildFrontPage(context: Context) {
    val image: Image = imageFromResource(context.resources, R.drawable.trump_dump)
    Column(
    ) {
        Container(
            expanded = true,
            height = 300.dp,
            padding = EdgeInsets(all = 10.dp),
        ) {
            DrawImage(image)
        }
        Container(
            expanded = true,
            alignment = Alignment.Center,
        ) {
            Row(
                modifier = Spacing(top = 20.dp, bottom = 20.dp),
            )
            {
                Text(text = "This guy is occupying the Oval office.")
            }
        }
        Container(
            alignment = Alignment.Center,
            padding = EdgeInsets(left = 10.dp, right = 10.dp),
        ) {
            Text(text = "The former president of U.S is currently occupying the Oval office, although the president elected Biden has won the 2020 Pres election.")
        }
    }
}