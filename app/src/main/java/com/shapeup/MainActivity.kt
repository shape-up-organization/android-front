package com.shapeup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shapeup.ui.theme.GradientLight
import com.shapeup.ui.theme.ShapeUpTheme

@Preview
@Composable
fun AppPreview() {
    ShapeUpTheme {
        App()
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShapeUpTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GradientLight),
        contentAlignment = Alignment.Center
    ) {
        Logo()
    }
}

@Composable
fun Logo() {
    Text(
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.displaySmall,
        text = "ShapeUp"
    )
}
