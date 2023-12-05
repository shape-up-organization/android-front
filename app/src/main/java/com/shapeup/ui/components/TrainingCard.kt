package com.shapeup.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.viewModels.logged.ETrainingPeriod
import com.shapeup.ui.viewModels.logged.ETrainingStatus
import com.shapeup.ui.viewModels.logged.EUpdateTrainingType
import com.shapeup.ui.viewModels.logged.Training
import java.time.DayOfWeek

@Composable
fun TrainingCard(
    isLoading: Boolean = false,
    dayOfWeek: DayOfWeek,
    period: ETrainingPeriod,
    training: Training,
    type: ETrainingCardType = ETrainingCardType.ADD,
    updateTraining: ((
        trainingId: String,
        dayOfWeek: DayOfWeek,
        period: ETrainingPeriod,
        type: EUpdateTrainingType
    ) -> Unit)? = null
) {
    var expandTrainingDetails by remember { mutableStateOf(false) }
    val expandTrainingDuration = 400

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                expandTrainingDetails = !expandTrainingDetails
            }
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp,
                vertical = 24.dp
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                contentDescription = stringResource(training.category.value),
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp),
                painter = painterResource(training.category.icon),
                tint = training.classification.backgroundColor
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                if (training.status == ETrainingStatus.UNCOMPLETED) {
                    Text(
                        color = MaterialTheme.colorScheme.error,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall,
                        text = stringResource(R.string.txt_trainings_uncompleted)
                    )
                }

                Text(
                    color = training.classification.backgroundColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge,
                    text = training.name
                )
                Text(
                    color = MaterialTheme.colorScheme.tertiary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                    text = "${stringResource(R.string.txt_trainings_category)} ${
                        stringResource(
                            training.category.value
                        )
                    }"
                )
                Text(
                    color = MaterialTheme.colorScheme.tertiary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                    text = "${training.duration} ${stringResource(R.string.txt_trainings_minutes)}"
                )
            }


            Icon(
                contentDescription = stringResource(R.string.icon_minimal_arrow),
                modifier = Modifier
                    .height(16.dp)
                    .rotate(
                        rememberUpdatedState(
                            animateFloatAsState(
                                animationSpec = tween(durationMillis = expandTrainingDuration),
                                label = "Arrow rotation",
                                targetValue = when (expandTrainingDetails) {
                                    true -> -90f
                                    else -> 90f
                                }
                            )
                        ).value.value
                    )
                    .width(16.dp),
                painter = painterResource(R.drawable.icon_minimal_arrow),
                tint = MaterialTheme.colorScheme.tertiary
            )

            when (type) {
                ETrainingCardType.ADD -> AssistChip(
                    border = AssistChipDefaults.assistChipBorder(
                        borderWidth = 0.dp
                    ),
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        labelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    enabled = !isLoading,
                    label = {
                        Text(
                            style = MaterialTheme.typography.bodyMedium,
                            text = stringResource(R.string.txt_trainings_add)
                        )
                    },
                    modifier = Modifier.padding(0.dp),
                    onClick = {
                        if (updateTraining != null) {
                            updateTraining(
                                training.id,
                                dayOfWeek,
                                period,
                                EUpdateTrainingType.ADD
                            )
                        }
                    },
                    shape = RoundedCornerShape(24.dp)
                )

                ETrainingCardType.EDIT -> Checkbox(
                    checked = training.status == ETrainingStatus.FINISHED,
                    colors = CheckboxDefaults.colors(
                        disabledCheckedColor = when (training.status) {
                            ETrainingStatus.UNCOMPLETED -> MaterialTheme.colorScheme.errorContainer
                            else -> MaterialTheme.colorScheme.primary
                        }
                    ),
                    enabled = training.status == ETrainingStatus.PENDING && !isLoading,
                    onCheckedChange = {
                        if (updateTraining != null) {
                            updateTraining(
                                training.id,
                                dayOfWeek,
                                period,
                                EUpdateTrainingType.CHECK
                            )
                        }
                    },
                )
            }
        }

        ExpandableContent(
            transitionDuration = expandTrainingDuration,
            visible = expandTrainingDetails,
            content = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (training.exercises.isNotEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            training.exercises.map {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                color = training.classification.backgroundColor,
                                                shape = CircleShape
                                            )
                                            .height(8.dp)
                                            .width(8.dp)
                                    )
                                    Text(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        style = MaterialTheme.typography.bodySmall,
                                        text = it
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                color = training.classification.backgroundColor,
                                style = MaterialTheme.typography.bodyMedium,
                                text = stringResource(training.classification.value)
                            )
                            Text(
                                color = MaterialTheme.colorScheme.tertiary,
                                style = MaterialTheme.typography.bodySmall,
                                text = "${training.xp}xp"
                            )
                        }

                        if (type == ETrainingCardType.EDIT) {
                            AssistChip(
                                border = AssistChipDefaults.assistChipBorder(
                                    borderWidth = 0.dp
                                ),
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = MaterialTheme.colorScheme.error,
                                    labelColor = MaterialTheme.colorScheme.onError
                                ),
                                enabled = !isLoading,
                                label = {
                                    Text(
                                        style = MaterialTheme.typography.bodyMedium,
                                        text = stringResource(R.string.txt_trainings_delete)
                                    )
                                },
                                modifier = Modifier.padding(0.dp),
                                onClick = {
                                    if (updateTraining != null) {
                                        updateTraining(
                                            training.id,
                                            dayOfWeek,
                                            period,
                                            EUpdateTrainingType.REMOVE
                                        )
                                    }
                                },
                                shape = RoundedCornerShape(24.dp)
                            )
                        }
                    }
                }
            }
        )
    }
}

enum class ETrainingCardType {
    ADD,
    EDIT
}
