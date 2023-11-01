package com.shapeup.ui.screens.logged

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.components.Carousel
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.Header
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.logged.PostCreation
import com.shapeup.ui.viewModels.logged.PostsHandlers
import com.shapeup.ui.viewModels.logged.postsHandlersMock
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch

@Preview
@Composable
fun PostFilesScreenPreview() {
    ShapeUpTheme {
        PostFilesScreen(
            navigator = Navigator(),
            postsHandlers = postsHandlersMock
        )
    }
}

@Composable
fun PostFilesScreen(
    navigator: Navigator,
    postsHandlers: PostsHandlers
) {
    var description by remember { mutableStateOf("") }
    var photoUrls by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }

    val coroutine = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        when {
            uris.isEmpty() && photoUrls.isEmpty() -> navigator.navigateBack()
            uris.isNotEmpty() -> photoUrls = uris
        }
    }

    val containerHeight = (LocalConfiguration.current.screenHeightDp * 0.6).dp
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    fun createPost() {
        coroutine.launch {
            loading = true
            val photos = photoUrls.map {
                context.contentResolver.openInputStream(it)
                    ?.use { new -> new.buffered().readBytes() }
            }

            val response = postsHandlers.createPost(
                PostCreation(
                    description = description,
                    photoUrls = photos
                )
            )

            when (response.status) {
                HttpStatusCode.Created -> {
                    navigator.navigateClean(Screen.Feed)
                }

                HttpStatusCode.OK -> {
                    navigator.navigateClean(Screen.Feed)
                }

                else -> loading = false
            }
        }
    }

    LaunchedEffect(Unit) {
        launcher.launch("image/*")
    }

    BackHandler {
        navigator.navigateBack()
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Header(
            navigateTo = { navigator.navigateBack() },
            text = stringResource(R.string.txt_post_title)
        )

        Column(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .height(containerHeight)
                .clickable {
                    launcher.launch("image/*")
                }
        ) {
            Carousel(
                data = photoUrls.map { it.toString() },
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            FormField(
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Default,
                    keyboardType = KeyboardType.Ascii
                ),
                label = stringResource(R.string.txt_post_form_field),
                maxLines = 24,
                modifier = Modifier.weight(1f),
                onValueChange = { description = it },
                supportingText = null,
                value = description
            )
        }

        Button(
            colors = ButtonDefaults.buttonColors(
                when (photoUrls.isNotEmpty()) {
                    true -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.tertiary
                }
            ),
            enabled = !loading && photoUrls.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            onClick = { createPost() }
        ) {
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(R.string.txt_post_button)
            )
        }
    }
}
