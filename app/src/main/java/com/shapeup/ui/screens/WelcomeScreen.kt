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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.theme.ShapeUpTheme

@Preview
@Composable
fun WelcomeScreenPreview() {
    ShapeUpTheme {
        WelcomeScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen() {
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
            painter = painterResource(id = R.drawable.girl_welcome),
            contentDescription = "Girl"
        )

        Column() {
            Button(
                onClick = { /*Todo*/ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign In")
            }

            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign Up")
            }
        }
    }
    val scope = rememberCoroutineScope()
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
                    painter = painterResource(id = R.drawable.big_foot),
                    contentDescription = "Big_foot"
                )
                Spacer(Modifier.height(72.dp))

                Row(verticalAlignment = Alignment.CenterVertically ) {
                    Icon(Icons.Outlined.Share, modifier = Modifier.background(color = MaterialTheme.colorScheme.primary), contentDescription = "Icon Share groups")
                    Text(text = "Share your progress with your friends")
                }

                Row {
                    Icon(Icons.Outlined.Star, contentDescription = "Icon Game mode")
                    Text(text = "A gamified social network")
                }

                Row {
                    Icon(Icons.Outlined.CheckCircle, contentDescription = "Icon training")
                    Text(text = "Plan your trainings")
                }
            }
        }
    ) {}
}

@Composable
fun RowIcon(text:String,icon:ImageVector){

}
