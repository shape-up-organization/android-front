package com.shapeup.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Carousel(data: List<String>) {
    val pagerState = rememberPagerState(initialPage = 0) { data.size }
    val maxHeight = (LocalConfiguration.current.screenHeightDp * 0.6).dp

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            key = { data[it] },
            pageContent = { index ->
                Image(
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(maxHeight),
                    painter = rememberAsyncImagePainter(model = data[index])
                )
            },
            state = pagerState
        )
    }
}
