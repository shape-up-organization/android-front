package com.shapeup.ui.utils.constants

import androidx.annotation.DrawableRes
import com.shapeup.R

enum class Icon(@DrawableRes val value: Int) {
    Add(value = R.drawable.icon_add),
    ArrowBack(value = R.drawable.icon_arrow_back),
    Calendar(value = R.drawable.icon_calendar),
    Close(value = R.drawable.icon_close),
    EyeClosed(value = R.drawable.icon_eye_closed),
    EyeOpen(value = R.drawable.icon_eye_open),
    FitnessCenter(value = R.drawable.icon_fitness_center),
    Groups(value = R.drawable.icon_groups),
    Home(value = R.drawable.icon_home),
    Phone(value = R.drawable.icon_phone),
    Rank(value = R.drawable.icon_rank),
    SportsJoystick(value = R.drawable.icon_sports_joystick)
}
