package com.shapeup.ui.screens.logged

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.components.EPageButtons
import com.shapeup.ui.components.Navbar
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.journeyDataMock
import java.time.DayOfWeek

@Preview
@Composable
fun TrainingsScreenPreview() {
    ShapeUpTheme {
        TrainingsScreen(
            journeyData = journeyDataMock,
            navigator = Navigator()
        )
    }
}

@Composable
fun TrainingsScreen(
    journeyData: JourneyData,
    navigator: Navigator
) {
    var daySelected by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                    text = stringResource(R.string.txt_trainings_title)
                )

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.labelSmall,
                        text = stringResource(R.string.txt_trainings_your_xp)
                    )
                    Text(
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyLarge,
                        text = "${journeyData.userData.value.xp}xp"
                    )
                }
            }

            ScrollableTabRow(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                edgePadding = 24.dp,
                selectedTabIndex = daySelected
            ) {
                DayOfWeek.values().forEachIndexed { index, dayOfWeek ->
                    Tab(
                        onClick = { daySelected = index },
                        selected = daySelected == index,
                        text = {
                            Text(
                                color = MaterialTheme.colorScheme.primary,
                                overflow = TextOverflow.Ellipsis,
                                text = stringResource(
                                    when (dayOfWeek) {
                                        DayOfWeek.MONDAY -> R.string.txt_trainings_monday
                                        DayOfWeek.TUESDAY -> R.string.txt_trainings_tuesday
                                        DayOfWeek.WEDNESDAY -> R.string.txt_trainings_wednesday
                                        DayOfWeek.THURSDAY -> R.string.txt_trainings_thursday
                                        DayOfWeek.FRIDAY -> R.string.txt_trainings_friday
                                        DayOfWeek.SATURDAY -> R.string.txt_trainings_saturday
                                        DayOfWeek.SUNDAY -> R.string.txt_trainings_sunday
                                    }
                                )
                            )
                        }
                    )
                }
            }
        }

        Navbar(
            activePage = EPageButtons.TRAININGS,
            data = journeyData,
            navigator = navigator
        )
    }
}
