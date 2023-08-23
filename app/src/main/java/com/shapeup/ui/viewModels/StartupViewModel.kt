package com.shapeup.ui.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class StartupViewModel() : ViewModel() {
    private val states = SavedStateHandle()

    val textCustom = states.getStateFlow("textCustom", "defaultValue")

    fun updateText(value: String) {
        states["textCustom"] = value
    }

}