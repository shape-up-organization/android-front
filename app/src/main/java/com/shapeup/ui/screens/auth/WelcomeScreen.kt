package com.shapeup.ui.screens.auth

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            text = "ShapeUp"
        )

        Image(
            painter = painterResource(id = Image.GirlWelcome.value),
            contentDescription = "Girl"
        )

        Column {
            Button(
                onClick = { navigator.navigateClean(Screen.SignIn) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Sign in",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedButton(
                onClick = { navigator.navigateClean(Screen.SignUp) },
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Sign up",
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
                Text(text = "About")

                Image(
                    painter = painterResource(id = Image.BigFoot.value),
                    contentDescription = "Big_foot"
                )
                Spacer(Modifier.height(72.dp))

                RowIcon(
                    text = "Share your progress",
                    icon = Icon.Groups,
                    contentDescription = "Icon groups"
                )

                Spacer(modifier = Modifier.height(24.dp))

                RowIcon(
                    text = "A gamified social network",
                    icon = Icon.SportsJoystick,
                    contentDescription = "Icon joystick"
                )

                Spacer(modifier = Modifier.height(24.dp))

                RowIcon(
                    text = "Plan your trainings",
                    icon = Icon.FitnessCenter,
                    contentDescription = "Icon fitness center"
                )
            }
        }
    ) {}
}

@Composable
fun RowIcon(text: String, icon: Icon, contentDescription: String? = "") {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painterResource(id = icon.value),
            contentDescription = contentDescription
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}
