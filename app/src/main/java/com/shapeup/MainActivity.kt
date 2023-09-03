package com.shapeup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.shapeup.ui.navigation.Navigation
import com.shapeup.ui.theme.ShapeUpTheme

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

@Preview
@Composable
fun AppPreview() {
    ShapeUpTheme {
        App()
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    Navigation(navController)
}
