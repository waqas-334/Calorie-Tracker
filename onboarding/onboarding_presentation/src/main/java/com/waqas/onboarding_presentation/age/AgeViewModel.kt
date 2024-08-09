package com.waqas.onboarding_presentation.age

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waqas.core.compose.R
import com.waqas.core.domain.preferences.Preferences
import com.waqas.core.domain.usecase.FilterOutDigits
import com.waqas.core.navigation.Route
import com.waqas.core.util.UiEvent
import com.waqas.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgeViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {

    var age by mutableStateOf("20")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAgeEnter(age: String) {
        if (age.length <= 3) {
            this.age = filterOutDigits(age)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val ageDigit = age.toIntOrNull() ?: kotlin.run {
                _uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(com.waqas.core.R.string.error_age_cant_be_empty)))
                return@launch
            }

            preferences.saveAge(ageDigit)
            _uiEvent.send(UiEvent.Navigate(Route.HEIGHT))


        }
    }


}