package com.shapeup.ui.utils.helpers

import androidx.navigation.NavController
import com.shapeup.ui.utils.constants.Screen

class Navigator(private val navController: NavController? = null) {
    val navigate: (Screen) -> Unit = { screen -> navController?.navigate(screen.value) }
    val navigateClean: (Screen) -> Unit = { screen ->
        navController?.navigate(screen.value) {
            popUpTo(screen.value) {
                inclusive = true
            }
        }
    }
}

val NavController.navigator: Navigator
    get() = Navigator(this)
