package com.waqas.testingmodulecompose.util

import androidx.navigation.NavController
import com.waqas.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}