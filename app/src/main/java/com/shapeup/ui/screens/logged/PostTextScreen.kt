package com.shapeup.ui.screens.logged

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.Header
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.logged.PostCreation
import com.shapeup.ui.viewModels.logged.PostsHandlers
import com.shapeup.ui.viewModels.logged.postsHandlersMock

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PostTextPreview() {
    ShapeUpTheme {
        PostTextScreen(
            navigator = Navigator(),
            postsHandlers = postsHandlersMock
        )
    }
}

@Composable
fun PostTextScreen(
    navigator: Navigator,
    postsHandlers: PostsHandlers
) {
    var description by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val fieldHeight = (LocalConfiguration.current.screenHeightDp * 0.6).dp

    BackHandler {
        navigator.navigateBack()
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Header(
            navigateTo = { navigator.navigateBack() },
            text = stringResource(R.string.txt_post_title)
        )

        Column(modifier = Modifier.weight(1f)) {
            FormField(
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Default,
                    keyboardType = KeyboardType.Ascii
                ),
                label = stringResource(R.string.txt_post_form_field),
                maxLines = 24,
                modifier = Modifier.height(fieldHeight),
                onValueChange = { description = it },
                supportingText = null,
                value = description
            )
        }

        Button(
            colors = ButtonDefaults.buttonColors(
                when (description.isNotBlank()) {
                    true -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.tertiary
                }
            ),
            enabled = description.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                postsHandlers.createPost(PostCreation(description = description))
            }
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 12.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(R.string.txt_post_button)
            )
        }
    }
}
