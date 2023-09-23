package com.shapeup.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    content: @Composable (ColumnScope.() -> Unit),
    mode: BottomSheetModes = BottomSheetModes.DEFAULT,
    open: MutableState<Boolean> = mutableStateOf(false),
    title: String? = null,
    peekHeight: Dp = if (title.isNullOrBlank()) 32.dp else 72.dp
) {
    val modalState = rememberModalBottomSheetState()
    val defaultState = rememberBottomSheetScaffoldState()

    if (mode == BottomSheetModes.MODAL && open.value) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            dragHandle = { BottomSheetDragHandler(title = title) },
            onDismissRequest = { open.value = false },
            shape = RoundedCornerShape(
                topEnd = 40.dp,
                topStart = 40.dp
            ),
            sheetState = modalState
        ) {
            content()
        }
    } else if (mode == BottomSheetModes.DEFAULT) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .background(
                    color = Color.Black.copy(
                        rememberUpdatedState(
                            animateFloatAsState(
                                animationSpec = tween(durationMillis = 300),
                                label = "BottomSheet background",
                                targetValue = when {
                                    remember {
                                        derivedStateOf {
                                            defaultState.bottomSheetState.targetValue ==
                                                SheetValue.Expanded
                                        }
                                    }.value -> 0.6f

                                    else -> 0f
                                }
                            ).value
                        ).value
                    )
                )
                .fillMaxSize()
        ) {
            BottomSheetScaffold(
                scaffoldState = defaultState,
                sheetContainerColor = MaterialTheme.colorScheme.primaryContainer,
                sheetContent = content,
                sheetDragHandle = { BottomSheetDragHandler(title = title) },
                sheetPeekHeight = peekHeight,
                sheetShape = RoundedCornerShape(
                    topEnd = 40.dp,
                    topStart = 40.dp
                )
            ) {}
        }
    }
}

@Composable
fun BottomSheetDragHandler(title: String?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Surface(
            color = MaterialTheme.colorScheme.tertiaryContainer,
            modifier = Modifier
                .padding(top = 16.dp)
                .semantics { contentDescription = title ?: "" },
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Box(
                Modifier
                    .size(
                        height = 6.dp,
                        width = 56.dp
                    )
            )
        }
        if (!title.isNullOrBlank()) {
            Text(
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
                text = title
            )
        }
        Divider(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
    }
}

enum class BottomSheetModes {
    DEFAULT,
    MODAL
}
