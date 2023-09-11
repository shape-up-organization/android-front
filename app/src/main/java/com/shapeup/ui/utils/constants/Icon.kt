package com.shapeup.ui.utils.constants

import androidx.annotation.DrawableRes
import com.shapeup.R

enum class Icon(@DrawableRes val value: Int) {
    ArrowBack(value = R.drawable.icon_arrow_back),
    Calendar(value = R.drawable.icon_calendar),
    Close(value = R.drawable.icon_close),
    EyeClosed(value = R.drawable.icon_eye_closed),
    EyeOpen(value = R.drawable.icon_eye_open),
    FitnessCenter(value = R.drawable.icon_fitness_center),
    Groups(value = R.drawable.icon_groups),
    Phone(value = R.drawable.icon_phone),
    SportsJoystick(value = R.drawable.icon_sports_joystick),
    ArrowForward(value = R.drawable.icon_arrow_forward),
    Accept(value = R.drawable.icon_accept),
    Decline(value = R.drawable.icon_decline)
}
