package com.shapeup.ui.screens.auth

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.constants.Image
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator

@Preview
@Composable
fun WelcomeScreenPreview() {
    ShapeUpTheme {
        WelcomeScreen(
            navigator = Navigator()
        )
    }
}

@Composable
fun WelcomeScreen(
    navigator: Navigator
) {
    BackHandler {
        navigator.navigateBack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displaySmall,
            text = stringResource(R.string.app_name)
        )

        Image(
            painter = painterResource(id = Image.GirlWelcome.value),
            contentDescription = stringResource(R.string.img_girl_welcome)
        )

        Column {
            Button(
                onClick = { navigator.navigate(Screen.SignIn) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.txt_sign_in),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedButton(
                onClick = { navigator.navigate(Screen.SignUp) },
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = stringResource(R.string.txt_sign_up),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )
            }
        }
    }

    BottomSheet()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet() {
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContainerColor = MaterialTheme.colorScheme.primaryContainer,
        sheetPeekHeight = BottomSheetDefaults.SheetPeekHeight,
        sheetContent = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(64.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = stringResource(R.string.txt_welcome_drawer_title))

                Image(
                    painter = painterResource(id = Image.GirlMeditating.value),
                    contentDescription = stringResource(R.string.img_girl_meditating)
                )
                Spacer(Modifier.height(72.dp))

                RowIcon(
                    text = stringResource(R.string.txt_welcome_drawer_topic_1),
                    icon = Icon.Groups,
                    contentDescription = stringResource(R.string.icon_groups)
                )

                Spacer(modifier = Modifier.height(24.dp))

                RowIcon(
                    text = stringResource(R.string.txt_welcome_drawer_topic_2),
                    icon = Icon.Joystick,
                    contentDescription = stringResource(R.string.icon_joystick)
                )

                Spacer(modifier = Modifier.height(24.dp))

                RowIcon(
                    text = stringResource(R.string.txt_welcome_drawer_topic_3),
                    icon = Icon.FitnessCenter,
                    contentDescription = stringResource(R.string.icon_fitness_center)
                )
            }
        }
    ) {}
}

@Composable
fun RowIcon(text: String, icon: Icon, contentDescription: String? = "") {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painterResource(icon.value),
            contentDescription = contentDescription
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}
