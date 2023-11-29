package com.shapeup.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch

@Composable
fun SnackbarHelper(
    align: Alignment = Alignment.BottomCenter,
    open: Boolean = false,
    message: String,
    openSnackbar: (Boolean) -> Unit,
    type: SnackbarType = SnackbarType.DEFAULT
) {
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        SnackbarHost(
            hostState = snackState,
            modifier = Modifier.align(align)
        ) {
            Snackbar(
                containerColor = when (type) {
                    SnackbarType.DEFAULT -> MaterialTheme.colorScheme.primary
                    SnackbarType.ERROR -> MaterialTheme.colorScheme.error
                },
                contentColor = when (type) {
                    SnackbarType.DEFAULT -> MaterialTheme.colorScheme.onPrimary
                    SnackbarType.ERROR -> MaterialTheme.colorScheme.onError
                },
                dismissActionContentColor = Color.Blue,
                snackbarData = it
            )
        }

    }

    if (open) {
        LaunchedEffect(Unit) {
            snackScope.launch { snackState.showSnackbar(message) }
            openSnackbar(false)
        }
    }
}

enum class SnackbarType {
    DEFAULT,
    ERROR
}