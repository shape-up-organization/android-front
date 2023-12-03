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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.components.BottomSheet
import com.shapeup.ui.components.BottomSheetModes
import com.shapeup.ui.components.EPageButtons
import com.shapeup.ui.components.ETrainingCardType
import com.shapeup.ui.components.ExpandableContent
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.FormFieldType
import com.shapeup.ui.components.Loading
import com.shapeup.ui.components.Navbar
import com.shapeup.ui.components.SnackbarHelper
import com.shapeup.ui.components.SnackbarType
import com.shapeup.ui.components.TrainingCard
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.logged.ETrainingPeriod
import com.shapeup.ui.viewModels.logged.EUpdateTrainingType
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyHandlers
import com.shapeup.ui.viewModels.logged.Training
import com.shapeup.ui.viewModels.logged.TrainingsData
import com.shapeup.ui.viewModels.logged.TrainingsHandlers
import com.shapeup.ui.viewModels.logged.journeyDataMock
import com.shapeup.ui.viewModels.logged.journeyHandlersMock
import com.shapeup.ui.viewModels.logged.trainingsDataMock
import com.shapeup.ui.viewModels.logged.trainingsHandlersMock
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.DayOfWeek

@Preview
@Composable
fun TrainingsScreenPreview() {
    ShapeUpTheme {
        TrainingsScreen(
            journeyData = journeyDataMock,
            journeyHandlers = journeyHandlersMock,
            navigator = Navigator(),
            trainingsData = trainingsDataMock,
            trainingsHandlers = trainingsHandlersMock
        )
    }
}

@Composable
fun TrainingsScreen(
    journeyData: JourneyData,
    journeyHandlers: JourneyHandlers,
    navigator: Navigator,
    trainingsData: TrainingsData,
    trainingsHandlers: TrainingsHandlers
) {
    var loadingUpdate by remember { mutableStateOf(false) }
    var loadingTrainings by remember { mutableStateOf(true) }
    var hasError by remember { mutableStateOf(false) }
    var daySelected by remember { mutableStateOf(DayOfWeek.MONDAY) }
    var chosenPeriod by remember { mutableStateOf(ETrainingPeriod.MORNING) }
    val openTrainingsListBottomSheet = remember { mutableStateOf(false) }
    var openSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    val coroutine = rememberCoroutineScope()

    val context = LocalContext.current

    val trainings = trainingsData
        .userTrainings.value
        .find { it.dayOfWeek == daySelected }
        ?.trainings

    fun openTrainingsModal(period: ETrainingPeriod) {
        openTrainingsListBottomSheet.value = true
        chosenPeriod = period
    }

    suspend fun getUserTrainings() {
        loadingTrainings = true
        val response = trainingsHandlers.getUserTrainings()

        when (response.status) {
            HttpStatusCode.OK -> {
                hasError = false
                openTrainingsListBottomSheet.value = false
            }

            else -> {
                hasError = true
                openSnackbar = true
                snackbarMessage = context.getString(R.string.txt_errors_generic)
            }
        }

        loadingTrainings = false
    }

    fun updateTraining(
        trainingId: String,
        dayOfWeek: DayOfWeek,
        period: ETrainingPeriod,
        type: EUpdateTrainingType
    ) {
        coroutine.launch {
            loadingUpdate = true
            val response = trainingsHandlers.updateTraining(
                trainingId,
                dayOfWeek,
                period,
                type
            )

            when (response.status) {
                HttpStatusCode.OK -> {
                    openTrainingsListBottomSheet.value = false
                    journeyHandlers.updateXp()
                }
                HttpStatusCode.NoContent -> {
                    openTrainingsListBottomSheet.value = false
                    journeyHandlers.updateXp()
                }

                else -> {
                    openSnackbar = true
                    snackbarMessage = context.getString(R.string.txt_errors_generic)
                }
            }

            loadingUpdate = false
        }
    }

    LaunchedEffect(key1 = true) {
        getUserTrainings()
    }

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

            if (loadingTrainings) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Loading()
                }
            } else if (hasError) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        text = stringResource(R.string.txt_errors_generic)
                    )
                }
            } else {
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
                                TrainingCard(
                                    isLoading = loadingUpdate,
                                    dayOfWeek = daySelected,
                                    period = period,
                                    training = training,
                                    type = ETrainingCardType.EDIT,
                                    updateTraining = { trainingId, dayOfWeek, period, type ->
                                        updateTraining(
                                            trainingId,
                                            dayOfWeek,
                                            period,
                                            type
                                        )
                                    }
                                )
                            } else {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .clickable {
                                            if (!loadingUpdate) {
                                                openTrainingsModal(period)
                                            }
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
                                        tint = when (loadingUpdate) {
                                            true -> MaterialTheme.colorScheme.tertiaryContainer
                                            false -> MaterialTheme.colorScheme.primary
                                        }
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
        }

        Navbar(
            activePage = EPageButtons.TRAININGS,
            data = journeyData,
            navigator = navigator
        )
    }

    TrainingsListBottomSheet(
        loadingUpdate = loadingUpdate,
        open = openTrainingsListBottomSheet,
        trainingsHandlers = trainingsHandlers,
        dayOfWeek = daySelected,
        period = chosenPeriod,
        updateTraining = { trainingId, dayOfWeek, period, type ->
            updateTraining(
                trainingId,
                dayOfWeek,
                period,
                type
            )
        }
    )

    SnackbarHelper(
        message = snackbarMessage,
        open = openSnackbar,
        openSnackbar = { openSnackbar = it },
        type = SnackbarType.ERROR
    )
}

@Composable
fun TrainingsListBottomSheet(
    loadingUpdate: Boolean,
    open: MutableState<Boolean>,
    trainingsHandlers: TrainingsHandlers,
    dayOfWeek: DayOfWeek,
    period: ETrainingPeriod,
    updateTraining: ((
        trainingId: String,
        dayOfWeek: DayOfWeek,
        period: ETrainingPeriod,
        type: EUpdateTrainingType
    ) -> Unit)? = null
) {
    var isLoading by remember { mutableStateOf(true) }
    var trainingsPack by remember { mutableStateOf(emptyList<Training>()) }
    var searchedTraining by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val filteredTrainings = trainingsPack.filter {
        it.name.contains(searchedTraining, ignoreCase = true) ||
                stringResource(it.category.value).contains(searchedTraining, ignoreCase = true)
    }

    suspend fun getTrainings() {
        isLoading = true
        val response = trainingsHandlers.getTrainingsPacks()

        when (response.status) {
            HttpStatusCode.OK -> {
                trainingsPack = response.data ?: emptyList()
                errorMessage = ""
            }

            else -> errorMessage = context.getString(R.string.txt_errors_generic)
        }

        withContext(Dispatchers.IO) {
            Thread.sleep(500)
            isLoading = false
        }
    }

    LaunchedEffect(key1 = open.value) {
        if (open.value) getTrainings()
    }

    BottomSheet(
        containerColor = MaterialTheme.colorScheme.background,
        mode = BottomSheetModes.MODAL,
        open = open,
        skipPartiallyExpanded = true,
        title = stringResource(R.string.txt_trainings_choose_training),
        content = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = 20.dp,
                            end = 24.dp,
                            start = 24.dp,
                            top = 8.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FormField(
                        focusManager = focusManager,
                        label = stringResource(R.string.txt_trainings_search_training),
                        modifier = Modifier.weight(1f),
                        onValueChange = { searchedTraining = it },
                        type = FormFieldType.SEARCH,
                        value = searchedTraining
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        ExpandableContent(
                            visible = isLoading,
                            content = {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Loading()
                                }
                            })
                    }
                    item {
                        ExpandableContent(
                            visible = errorMessage.isNotBlank(),
                            content = {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        color = MaterialTheme.colorScheme.error,
                                        text = errorMessage
                                    )
                                }
                            })
                    }
                    items(filteredTrainings) {
                        TrainingCard(
                            isLoading = loadingUpdate,
                            dayOfWeek = dayOfWeek,
                            period = period,
                            training = it,
                            updateTraining = updateTraining
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    )
}
