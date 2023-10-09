package com.shapeup.service.trainings

import com.shapeup.ui.viewModels.logged.ETrainingCategory
import com.shapeup.ui.viewModels.logged.ETrainingClassification
import com.shapeup.ui.viewModels.logged.ETrainingPeriod
import com.shapeup.ui.viewModels.logged.ETrainingStatus
import com.shapeup.ui.viewModels.logged.Training
import com.shapeup.ui.viewModels.logged.UserTrainingDay
import com.shapeup.ui.viewModels.logged.UserTrainingPeriod
import java.time.DayOfWeek

val getTrainingsMock = listOf(
    Training(
        category = ETrainingCategory.AEROBIC,
        classification = ETrainingClassification.IRON,
        description = "Aerobic training description",
        duration = 30,
        exercises = listOf("Jumping Jacks", "Running in place"),
        name = "Morning Aerobics",
        id = "12",
        unlockXp = 50,
        xp = 76
    ),
    Training(
        category = ETrainingCategory.HEALTH,
        classification = ETrainingClassification.BRONZE,
        description = "Health training description",
        duration = 45,
        exercises = emptyList(),
        name = "Midweek Health",
        id = "2",
        unlockXp = 75,
        xp = 44
    ),
    Training(
        category = ETrainingCategory.FIGHT,
        classification = ETrainingClassification.DIAMOND,
        description = "Fight training description",
        duration = 60,
        exercises = listOf("Boxing", "Kickboxing", "MMA drills"),
        name = "Friday Fight Night",
        id = "3",
        unlockXp = 100,
        xp = 40
    ),
    Training(
        category = ETrainingCategory.STRENGTH,
        classification = ETrainingClassification.RUBY,
        description = "Strength training description",
        duration = 45,
        exercises = listOf("Deadlifts", "Squats", "Bench Press"),
        name = "Weekend Strength Workout",
        id = "4",
        unlockXp = 90,
        xp = 12
    ),
    Training(
        category = ETrainingCategory.AEROBIC,
        classification = ETrainingClassification.PLATINUM,
        description = "Fun aerobic exercises for a healthy start to the day",
        duration = 30,
        exercises = listOf("Dancing", "Jump Rope"),
        name = "Sunday Funday Aerobics",
        id = "5",
        unlockXp = 60,
        xp = 26
    )
)

val getUserTrainingsMock = listOf(
    UserTrainingDay(
        dayOfWeek = DayOfWeek.MONDAY,
        trainings = listOf(
            UserTrainingPeriod(
                period = ETrainingPeriod.MORNING,
                training = Training(
                    category = ETrainingCategory.AEROBIC,
                    classification = ETrainingClassification.IRON,
                    description = "Aerobic training in the morning",
                    duration = 30,
                    exercises = listOf("Jumping Jacks", "Running in place"),
                    name = "Morning Aerobics Morning Aerobics Morning Aerobics",
                    id = "1",
                    status = ETrainingStatus.UNCOMPLETED,
                    unlockXp = 50,
                    xp = 86
                )
            ),
            UserTrainingPeriod(
                period = ETrainingPeriod.AFTERNOON,
                training = Training(
                    category = ETrainingCategory.HEALTH,
                    classification = ETrainingClassification.BRONZE,
                    description = "Health training in the afternoon",
                    duration = 45,
                    exercises = emptyList(),
                    name = "Midweek Health",
                    id = "2",
                    status = ETrainingStatus.FINISHED,
                    unlockXp = 75,
                    xp = 66
                )
            ),
            UserTrainingPeriod(
                period = ETrainingPeriod.NIGHT,
                training = Training(
                    category = ETrainingCategory.FIGHT,
                    classification = ETrainingClassification.SILVER,
                    description = "Fight training at night",
                    duration = 60,
                    exercises = listOf("Boxing", "Kickboxing", "MMA drills"),
                    name = "Friday Fight Night",
                    id = "3",
                    status = ETrainingStatus.PENDING,
                    unlockXp = 100,
                    xp = 45
                )
            )
        )
    ),
    UserTrainingDay(
        dayOfWeek = DayOfWeek.TUESDAY,
        trainings = listOf(
            UserTrainingPeriod(
                period = ETrainingPeriod.MORNING,
                training = Training(
                    category = ETrainingCategory.STRENGTH,
                    classification = ETrainingClassification.GOLD,
                    description = "Strength training in the morning",
                    duration = 45,
                    exercises = listOf("Deadlifts", "Squats", "Bench Press"),
                    name = "Weekend Strength Workout",
                    id = "4",
                    status = ETrainingStatus.PENDING,
                    unlockXp = 90,
                    xp = 130
                )
            ),
            UserTrainingPeriod(
                period = ETrainingPeriod.NIGHT,
                training = Training(
                    category = ETrainingCategory.AEROBIC,
                    classification = ETrainingClassification.EMERALD,
                    description = "Fun aerobic exercises for a healthy start to the day",
                    duration = 30,
                    exercises = emptyList(),
                    name = "Sunday Funday Aerobics",
                    id = "5",
                    status = ETrainingStatus.PENDING,
                    unlockXp = 60,
                    xp = 80
                )
            )
        )
    )
)
