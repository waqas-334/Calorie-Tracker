package com.waqas.onboarding_domain.use_case

import com.waqas.core.R
import com.waqas.core.util.UiText

class ValidateNutrients {

    operator fun invoke(
        carbsRatioText: String,
        proteinRatioText: String,
        fatRatioRatioText: String,
    ): Result {

        val carbsRatio = carbsRatioText.toIntOrNull()
        val proteinRatio = proteinRatioText.toIntOrNull()
        val fatRatio = fatRatioRatioText.toIntOrNull()

        if (carbsRatio == null || proteinRatio == null || fatRatio == null) {
            //for some reason, string reference can not be resolved while building the project
//            return Result.Error(message = UiText.StringResource(R.string.error_invalid_values))
            return Result.Error(message = UiText.DynamicString("The values must be valid"))
        }

        if (carbsRatio + proteinRatio + fatRatio != 100) {
            //for some reason, string reference can not be resolved while building the project
//            return Result.Error(message = UiText.StringResource(R.string.error_not_100_percent))
            return Result.Error(message = UiText.DynamicString("The values must add up to 100"))
        }

        return Result.Success(
            carbsRatio = carbsRatio / 100f,
            proteinRatio = proteinRatio / 100f,
            fatRatio = fatRatio / 100f
        )


    }

    sealed class Result {
        data class Success(
            val carbsRatio: Float,
            val proteinRatio: Float,
            val fatRatio: Float
        ) : Result()

        data class Error(val message: UiText) : Result()

    }
}