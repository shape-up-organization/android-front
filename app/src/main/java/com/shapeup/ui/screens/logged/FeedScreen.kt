package com.shapeup.ui.screens.logged

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.helpers.Navigator

@Preview
@Composable
fun FeedPreview() {
    ShapeUpTheme {
        FeedScreen(
            navigator = Navigator()
        )
    }
}

@Composable
fun FeedScreen(navigator: Navigator) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
//                    contentDescription = stringResource(R.string.icon_arrow_back),
                    contentDescription = "Icon",
                    painter = painterResource(Icon.Groups.value),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
//                    contentDescription = stringResource(R.string.icon_arrow_back),
                            contentDescription = "Icon",
                            painter = painterResource(Icon.Groups.value),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
//                    contentDescription = stringResource(R.string.icon_arrow_back),
                    contentDescription = "Icon",
                    painter = painterResource(Icon.Groups.value),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}
