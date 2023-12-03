package com.shapeup.ui.viewModels.logged

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.shapeup.R
import com.shapeup.api.services.trainings.AddTrainingsPayload
import com.shapeup.api.services.trainings.DeleteTrainingPayload
import com.shapeup.api.services.trainings.ETrainingsApi
import com.shapeup.api.services.trainings.FinishTrainingPayload
import com.shapeup.api.services.trainings.GenericTrainingUpdateStatement
import com.shapeup.api.services.trainings.GetTrainingsStatement
import com.shapeup.api.services.trainings.GetUserTrainingsStatement
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.utils.helpers.Navigator
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import java.time.DayOfWeek

class TrainingsViewModel : ViewModel() {
    lateinit var navigator: Navigator
    lateinit var sharedData: SharedData

    val userTrainings = mutableStateOf<List<UserTrainingDay>>(emptyList())

    private suspend fun getUserTrainings(): GetUserTrainingsStatement {
        val trainingsApi = ETrainingsApi.create(sharedData)

        val response = trainingsApi.getUserTrainings()

        println(response)

        if (response.status == HttpStatusCode.OK && response.data != null) {
            userTrainings.value = response.data
        }

        return response
    }

    private suspend fun getTrainingsPacks(): GetTrainingsStatement {
        val trainingsApi = ETrainingsApi.create(sharedData)

        val response = trainingsApi.getTrainings()

        println(response)

        return response
    }

    private suspend fun updateTraining(
        trainingId: String,
        dayOfWeek: DayOfWeek,
        trainingPeriod: ETrainingPeriod,
        type: EUpdateTrainingType
    ): GenericTrainingUpdateStatement {
        val trainingsApi = ETrainingsApi.create(sharedData)

        println("type: $type")

        val response = when (type) {
            EUpdateTrainingType.ADD -> trainingsApi.addTraining(
                AddTrainingsPayload(
                    dayOfWeek = dayOfWeek,
                    period = trainingPeriod,
                    trainingId = trainingId
                )
            )

            EUpdateTrainingType.CHECK -> trainingsApi.finishTraining(
                FinishTrainingPayload(
                    dayOfWeek = dayOfWeek,
                    period = trainingPeriod,
                    trainingId = trainingId
                )
            )

            EUpdateTrainingType.REMOVE -> trainingsApi.deleteTraining(
                DeleteTrainingPayload(
                    dayOfWeek = dayOfWeek,
                    period = trainingPeriod,
                    trainingId = trainingId
                )
            )
        }

        println(response)

        if (response.status == HttpStatusCode.OK || response.status == HttpStatusCode.NoContent) {
            getUserTrainings()
        }

        return response
    }

    val handlers = TrainingsHandlers(
        getTrainingsPacks = ::getTrainingsPacks,
        getUserTrainings = ::getUserTrainings,
        updateTraining = ::updateTraining
    )
}

data class TrainingsData(
    val userTrainings: MutableState<List<UserTrainingDay>>
)

val trainingsDataMock = TrainingsData(
    userTrainings = mutableStateOf(emptyList())
)

data class TrainingsHandlers(
    val getTrainingsPacks: suspend () -> GetTrainingsStatement,
    val getUserTrainings: suspend () -> GetUserTrainingsStatement,
    val updateTraining: suspend (
        trainingId: String,
        dayOfWeek: DayOfWeek,
        trainingPeriod: ETrainingPeriod,
        type: EUpdateTrainingType
    ) -> GenericTrainingUpdateStatement

)

val trainingsHandlersMock = TrainingsHandlers(
    getTrainingsPacks = {
        GetTrainingsStatement(
            status = HttpStatusCode.OK
        )
    },
    getUserTrainings = {
        GetUserTrainingsStatement(
            status = HttpStatusCode.OK
        )
    },
    updateTraining = { _, _, _, _ ->
        GenericTrainingUpdateStatement(
            status = HttpStatusCode.OK
        )
    }
)

enum class ETrainingCategory(
    val icon: Int,
    val value: Int
) {
    AEROBIC(
        icon = R.drawable.icon_air,
        value = R.string.txt_trainings_category_aerobic
    ),
    HEALTH(
        icon = R.drawable.icon_heart_filled,
        value = R.string.txt_trainings_category_health
    ),
    FIGHT(
        icon = R.drawable.icon_mma,
        value = R.string.txt_trainings_category_fight
    ),
    STRENGTH(
        icon = R.drawable.icon_fitness_center,
        value = R.string.txt_trainings_category_strength
    )
}

@SuppressLint("InvalidColorHexValue")
enum class ETrainingClassification(
    val backgroundColor: Color,
    val foregroundColor: Color,
    val value: Int
) {
    IRON(
        backgroundColor = Color(0xFF613302),
        foregroundColor = Color(0x0FFFFFFFF),
        value = R.string.txt_trainings_classification_iron
    ),
    BRONZE(
        backgroundColor = Color(0xFFEC8723),
        foregroundColor = Color(0x0FFFFFFFF),
        value = R.string.txt_trainings_classification_bronze
    ),
    SILVER(
        backgroundColor = Color(0xFF033F6D),
        foregroundColor = Color(0x0FF000000),
        value = R.string.txt_trainings_classification_silver
    ),
    GOLD(
        backgroundColor = Color(0x0FFFFEE00),
        foregroundColor = Color(0x0FF000000),
        value = R.string.txt_trainings_classification_gold
    ),
    DIAMOND(
        backgroundColor = Color(0xFF00F7FF),
        foregroundColor = Color(0x0FF000000),
        value = R.string.txt_trainings_classification_diamond
    ),
    EMERALD(
        backgroundColor = Color(0x0FF00FF00),
        foregroundColor = Color(0x0FF000000),
        value = R.string.txt_trainings_classification_emerald
    ),
    RUBY(
        backgroundColor = Color(0x0FFFF0000),
        foregroundColor = Color(0x0FFFFFFFF),
        value = R.string.txt_trainings_classification_ruby
    ),
    PLATINUM(
        backgroundColor = Color(0xFF3FA2F7),
        foregroundColor = Color(0x0FF000000),
        value = R.string.txt_trainings_classification_platinum
    )
}

enum class ETrainingStatus {
    FINISHED,
    PENDING,
    UNCOMPLETED
}

@Serializable
data class Training(
    val category: ETrainingCategory,
    val classification: ETrainingClassification,
    val description: String,
    val duration: Int,
    val exercises: List<String>,
    val id: String,
    val name: String,
    val status: ETrainingStatus? = null,
    val unlockXp: Int,
    val xp: Int
)

enum class ETrainingPeriod(val value: Int) {
    MORNING(value = R.string.txt_trainings_morning),
    AFTERNOON(value = R.string.txt_trainings_afternoon),
    NIGHT(value = R.string.txt_trainings_night)
}

@Serializable
data class UserTrainingPeriod(
    val period: ETrainingPeriod,
    val training: Training
)

@Serializable
data class UserTrainingDay(
    val trainings: List<UserTrainingPeriod>,
    val dayOfWeek: DayOfWeek
)

enum class EUpdateTrainingType {
    ADD,
    CHECK,
    REMOVE
}
