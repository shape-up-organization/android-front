package com.shapeup.ui.screens.logged

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.shapeup.R
import com.shapeup.ui.components.FormField
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.utils.helpers.XPUtils
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyHandlers
import com.shapeup.ui.viewModels.logged.UserDataUpdate
import com.shapeup.ui.viewModels.logged.journeyDataMock
import com.shapeup.ui.viewModels.logged.journeyHandlersMock

@Preview
@Composable
fun EditProfileScreenPreview() {
    ShapeUpTheme {
        EditProfileScreen(
            journeyData = journeyDataMock,
            journeyHandlers = journeyHandlersMock,
            navigator = Navigator()
        )
    }
}

@Composable
fun EditProfileScreen(
    journeyData: JourneyData,
    journeyHandlers: JourneyHandlers,
    navigator: Navigator
) {
    var updatedFirstName by remember { mutableStateOf(journeyData.userData.value.firstName) }
    var updatedLastName by remember { mutableStateOf(journeyData.userData.value.lastName) }
    var updatedUsername by remember { mutableStateOf(journeyData.userData.value.username) }
    var updatedBio by remember { mutableStateOf(journeyData.userData.value.bio ?: "") }
    var updatedProfilePicture by remember {
        mutableStateOf<Uri?>(Uri.parse(journeyData.userData.value.profilePicture))
    }

    var openProfileImageDialog by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) updatedProfilePicture = uri
    }

    val focusManager = LocalFocusManager.current
    val expandedProfilePictureSize = LocalConfiguration.current.screenWidthDp * 0.8

    BackHandler {
        navigator.navigateBack()
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navigator.navigateBack() }) {
                Icon(
                    contentDescription = stringResource(Icon.ArrowBack.description),
                    painter = painterResource(Icon.ArrowBack.value),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .border(
                            brush = XPUtils.getBorder(journeyData.userData.value.xp),
                            shape = CircleShape,
                            width = 2.dp
                        )
                        .clip(CircleShape)
                        .clickable {
                            openProfileImageDialog = true
                        }
                        .height(104.dp)
                        .width(104.dp),
                    painter = rememberAsyncImagePainter(
                        model = updatedProfilePicture
                    )
                )
                TextButton(onClick = { launcher.launch("image/*") }) {
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = stringResource(R.string.txt_edit_profile_update_profile_pic)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    label = stringResource(R.string.txt_sign_up_screen_first_name_label),
                    onValueChange = { updatedFirstName = it },
                    supportingText = null,
                    value = updatedFirstName
                )
                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    label = stringResource(R.string.txt_sign_up_screen_last_name_label),
                    onValueChange = { updatedLastName = it },
                    supportingText = null,
                    value = updatedLastName
                )
                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    label = stringResource(R.string.txt_sign_up_screen_username_label),
                    onValueChange = { updatedUsername = it },
                    supportingText = null,
                    value = updatedUsername
                )
                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Default,
                        keyboardType = KeyboardType.Ascii
                    ),
                    label = stringResource(R.string.txt_sign_up_screen_bio_label),
                    maxLines = 4,
                    onValueChange = { updatedBio = it },
                    supportingText = null,
                    value = updatedBio
                )
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                journeyHandlers.updateUserData(
                    UserDataUpdate(
                        bio = updatedBio,
                        firstName = updatedFirstName,
                        lastName = updatedLastName,
                        profilePicture = updatedProfilePicture
                            ?: Uri.parse(journeyData.userData.value.profilePicture),
                        username = updatedUsername
                    )
                )
            }
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 12.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = "Update"
            )
        }
    }

    if (openProfileImageDialog) {
        Dialog(onDismissRequest = { openProfileImageDialog = false }) {
            Image(
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .border(
                        brush = XPUtils.getBorder(journeyData.userData.value.xp),
                        shape = CircleShape,
                        width = 2.dp
                    )
                    .clip(CircleShape)
                    .height(expandedProfilePictureSize.dp)
                    .width(expandedProfilePictureSize.dp),
                painter = rememberAsyncImagePainter(
                    model = updatedProfilePicture
                )
            )
        }
    }
}
