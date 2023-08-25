package com.shapeup.ui.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.theme.ShapeUpTheme

@Preview
@Composable
fun WelcomeScreenPreview() {
    ShapeUpTheme {
        WelcomeScreen(
            navigateToSignIn = {},
            navigateToSignUp = {}
        )
    }
}

@Composable
fun WelcomeScreen(
    navigateToSignIn: () -> Unit,
    navigateToSignUp: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
            painter = painterResource(id = R.drawable.img_girl_welcome),
            contentDescription = "Girl"
        )

        Column() {
            Button(
                onClick = navigateToSignIn,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign In")
            }

            OutlinedButton(
                onClick = navigateToSignUp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign Up")
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
                    painter = painterResource(id = R.drawable.img_big_foot),
                    contentDescription = "Big_foot"
                )
                Spacer(Modifier.height(72.dp))

                RowIcon(
                    text = "Share your progress",
                    icon = R.drawable.icon_groups,
                    contentDescription = "Icon groups"
                )

                Spacer(modifier = Modifier.height(24.dp))

                RowIcon(
                    text = "A gamified social network",
                    icon = R.drawable.icon_sports_joystick,
                    contentDescription = "Icon joystick"
                )

                Spacer(modifier = Modifier.height(24.dp))

                RowIcon(
                    text = "Plan your trainings",
                    icon = R.drawable.icon_fitness_center,
                    contentDescription = "Icon fitness center"
                )
            }
        }
    ) {}
}

@Composable
fun RowIcon(text: String, icon: Int, contentDescription: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painterResource(id = icon),
            contentDescription = contentDescription
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}
