package com.waqas.onboarding_presentation.nutrient_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waqas.core.domain.preferences.Preferences
import com.waqas.core.domain.usecase.FilterOutDigits
import com.waqas.core.util.UiEvent
import com.waqas.onboarding_domain.use_case.ValidateNutrients
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits,
    private val validateNutrients: ValidateNutrients
) : ViewModel() {

    var state by mutableStateOf(NutrientGoalState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: NutrientGoalEvent) {
        when (event) {
            is NutrientGoalEvent.OnCarbRatioEnter -> {
                state = state.copy(carbsRatio = filterOutDigits(event.ratio))
            }

            is NutrientGoalEvent.OnFatRatioEnter -> {
                state = state.copy(fatRatio = filterOutDigits(event.ratio))
            }

            is NutrientGoalEvent.OnProteinRatioEnter -> {
                state = state.copy(proteinRatio = filterOutDigits(event.ratio))
            }

            NutrientGoalEvent.OnNextClick -> {
                val result: ValidateNutrients.Result = validateNutrients(
                    fatRatioRatioText = state.fatRatio,
                    carbsRatioText = state.carbsRatio,
                    proteinRatioText = state.proteinRatio
                )
                when (result) {
                    is ValidateNutrients.Result.Success -> {
                        viewModelScope.launch {
                            preferences.saveCarbRatio(result.carbsRatio)
                            preferences.saveFatRatio(result.fatRatio)
                            preferences.saveProteinRatio(result.proteinRatio)
                            _uiEvent.send(UiEvent.Success)
                        }

                    }

                    is ValidateNutrients.Result.Error -> {
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.ShowSnackbar(result.message))
                        }
                    }
                }


            }
        }
    }


}