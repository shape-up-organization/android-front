package com.shapeup.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.utils.constants.Icon

@Composable
fun RowSettings(text: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .padding(all = 32.dp)
    ) {
        Text(text = text)

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                contentDescription = stringResource(R.string.icon_arrow_forward),
                painter = painterResource(id = Icon.ArrowForward.value),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
