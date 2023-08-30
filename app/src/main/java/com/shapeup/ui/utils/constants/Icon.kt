package com.shapeup.ui.utils.constants

import androidx.annotation.DrawableRes
import com.shapeup.R

enum class Icon(@DrawableRes val value: Int) {
    ArrowBack(value = R.drawable.icon_arrow_back),
    EyeClosed(value = R.drawable.icon_eye_closed),
    EyeOpen(value = R.drawable.icon_eye_open),
    FitnessCenter(value = R.drawable.icon_fitness_center),
    Groups(value = R.drawable.icon_groups),
    SportsJoystick(value = R.drawable.icon_sports_joystick)
}
