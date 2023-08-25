package com.shapeup.ui.utils.constants

import androidx.annotation.StringRes
import com.shapeup.R

enum class Screens(@StringRes val title: Int) {
    Splash(title = R.string.splash_screen),
    SignIn(title = R.string.sign_in_screen),
    Welcome(title = R.string.welcome_screen),
    SignUp(title = R.string.sign_up_screen)
}
