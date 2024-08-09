package com.waqas.onboarding_presentation.age

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.waqas.core.R
import com.waqas.core.util.UiEvent
import com.waqas.core_ui.LocalSpacing
import com.waqas.onboarding_presentation.components.ActionButton
import com.waqas.onboarding_presentation.components.UnitTextField


@Composable
fun AgeScreen(
    modifier: Modifier = Modifier,
    onNavigate: (UiEvent.Navigate) -> Unit,
    scaffoldState: ScaffoldState,
    viewModel: AgeViewModel = hiltViewModel(),
) {


    val spacing = LocalSpacing.current
    val context = LocalContext.current

    /**
     * The LaunchedEffect is used to collect the uiEvent from the viewModel
     * on a coroutine
     *
     * So the way it's working is:
     * it keeps an eye on uiEvents from GenderViewModel,
     * and when it receives something, it passes it up to the MainActivity
     */
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message.asString(context))
                }

                else -> Unit
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.whats_your_age),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = viewModel.age,
                onValueChanged = viewModel::onAgeEnter,
                unit = stringResource(
                    id = R.string.years
                )
            )
        }

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = viewModel::onNextClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}