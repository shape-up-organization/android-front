package com.shapeup.ui.screens.logged

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.components.EPageButtons
import com.shapeup.ui.components.Navbar
import com.shapeup.ui.components.TrainingCard
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.logged.ETrainingPeriod
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.TrainingsHandlers
import com.shapeup.ui.viewModels.logged.journeyDataMock
import com.shapeup.ui.viewModels.logged.trainingsHandlersMock
import java.time.DayOfWeek

@Preview
@Composable
fun TrainingsScreenPreview() {
    ShapeUpTheme {
        TrainingsScreen(
            journeyData = journeyDataMock,
            navigator = Navigator(),
            trainingsHandlers = trainingsHandlersMock
        )
    }
}

@Composable
fun TrainingsScreen(
    journeyData: JourneyData,
    navigator: Navigator,
    trainingsHandlers: TrainingsHandlers
) {
    var daySelected by remember { mutableStateOf(DayOfWeek.MONDAY) }

    val trainings = trainingsHandlers
        .getUserTrainings()
        .find { it.dayOfWeek == daySelected }
        ?.trainings

    BackHandler {
        navigator.navigateBack()
    }

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
                selectedTabIndex = DayOfWeek.values().indexOf(daySelected)
            ) {
                DayOfWeek.values().forEach {
                    Tab(
                        onClick = { daySelected = it },
                        selected = daySelected == it,
                        text = {
                            Text(
                                color = MaterialTheme.colorScheme.primary,
                                overflow = TextOverflow.Ellipsis,
                                text = stringResource(
                                    when (it) {
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

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(ETrainingPeriod.values()) { period ->
                    val training = trainings?.find { training ->
                        training.period == period
                    }?.training

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            color = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.padding(vertical = 16.dp),
                            style = MaterialTheme.typography.labelSmall,
                            text = stringResource(period.value)
                        )

                        if (training != null) {
                            TrainingCard(training = training)
                        } else {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable {
                                        /* TODO: open trainings packs action */
                                    }
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 24.dp,
                                        vertical = 32.dp
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    contentDescription = stringResource(R.string.icon_add),
                                    modifier = Modifier
                                        .height(40.dp)
                                        .width(40.dp),
                                    painter = painterResource(R.drawable.icon_add),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
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
