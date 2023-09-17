package com.shapeup.ui.utils.constants

import androidx.annotation.DrawableRes
import com.shapeup.R

enum class Icon(@DrawableRes val value: Int, val description: Int) {
    Accept(
        description = R.string.icon_accept,
        value = R.drawable.icon_accept
    ),
    Add(
        description = R.string.icon_add,
        value = R.drawable.icon_add
    ),
    ArrowBack(
        description = R.string.icon_arrow_back,
        value = R.drawable.icon_arrow_back
    ),
    ArrowForward(
        description = R.string.icon_arrow_forward,
        value = R.drawable.icon_arrow_forward
    ),
    Calendar(
        description = R.string.icon_calendar,
        value = R.drawable.icon_calendar
    ),
    Chats(
        description = R.string.icon_chats,
        value = R.drawable.icon_chats
    ),
    Close(
        description = R.string.icon_close,
        value = R.drawable.icon_close
    ),
    EyeClosed(
        description = R.string.icon_eye_closed,
        value = R.drawable.icon_eye_closed
    ),
    Decline(
        description = R.string.icon_decline,
        value = R.drawable.icon_decline
    ),
    EyeOpen(
        description = R.string.icon_eye_open,
        value = R.drawable.icon_eye_open
    ),
    FitnessCenter(
        description = R.string.icon_fitness_center,
        value = R.drawable.icon_fitness_center
    ),
    Groups(
        description = R.string.icon_groups,
        value = R.drawable.icon_groups
    ),
    HeartFilled(
        description = R.string.icon_heart_filled,
        value = R.drawable.icon_heart_filled
    ),
    HeartOutlined(
        description = R.string.icon_heart_outlined,
        value = R.drawable.icon_heart_outlined
    ),
    Home(
        description = R.string.icon_home,
        value = R.drawable.icon_home
    ),
    Joystick(
        description = R.string.icon_joystick,
        value = R.drawable.icon_joystick
    ),
    More(
        description = R.string.icon_more,
        value = R.drawable.icon_more
    ),
    Notifications(
        description = R.string.icon_notifications,
        value = R.drawable.icon_notifications
    ),
    Pencil(
        description = R.string.icon_pencil,
        value = R.drawable.icon_pencil
    ),
    Phone(
        description = R.string.icon_phone,
        value = R.drawable.icon_phone
    ),
    Plus(
        description = R.string.icon_plus,
        value = R.drawable.icon_plus
    ),
    Rank(
        description = R.string.icon_rank,
        value = R.drawable.icon_rank
    ),
    Search(
        description = R.string.icon_search,
        value = R.drawable.icon_search
    ),
    Settings(
        description = R.string.icon_settings,
        value = R.drawable.icon_settings
    )
}
