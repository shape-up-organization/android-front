package com.shapeup

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.navigation.Navigation
import com.shapeup.ui.theme.ShapeUpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShapeUpTheme {
                App(null)
            }
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    ShapeUpTheme {
        App(null)
    }
}

@Composable
fun App(sharedPreferences: SharedPreferences?) {
    val context = LocalContext.current
    val sharedData =
        SharedData(
            sharedPreferences ?: context.getSharedPreferences(
                "shapeup",
                Context.MODE_PRIVATE
            ))
    val navController = rememberNavController()
    Navigation(
        navController = navController,
        sharedData = sharedData
    )
}
