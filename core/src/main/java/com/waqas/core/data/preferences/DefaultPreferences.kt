package com.waqas.core.data.preferences

import android.content.SharedPreferences
import com.waqas.core.domain.model.ActivityLevel
import com.waqas.core.domain.model.Gender
import com.waqas.core.domain.model.GoalType
import com.waqas.core.domain.model.UserInfo
import com.waqas.core.domain.preferences.Preferences
import com.waqas.core.domain.preferences.Preferences.Companion.KEY_ACTIVITY_LEVEL
import com.waqas.core.domain.preferences.Preferences.Companion.KEY_AGE
import com.waqas.core.domain.preferences.Preferences.Companion.KEY_CARB_RATIO
import com.waqas.core.domain.preferences.Preferences.Companion.KEY_FAT_RATIO
import com.waqas.core.domain.preferences.Preferences.Companion.KEY_GENDER
import com.waqas.core.domain.preferences.Preferences.Companion.KEY_GOAL_TYPE
import com.waqas.core.domain.preferences.Preferences.Companion.KEY_HEIGHT
import com.waqas.core.domain.preferences.Preferences.Companion.KEY_PROTEIN_RATIO
import com.waqas.core.domain.preferences.Preferences.Companion.KEY_WEIGHT

class DefaultPreferences(
    private val sharedPref: SharedPreferences
) : Preferences {
    override fun saveGender(gender: Gender) {
        sharedPref.edit().putString(KEY_GENDER, gender.name).apply()
    }

    override fun saveAge(age: Int) {
        sharedPref.edit().putInt(KEY_AGE, age).apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPref.edit().putFloat(KEY_WEIGHT, weight).apply()
    }

    override fun saveHeight(height: Int) {
        sharedPref.edit().putInt(KEY_HEIGHT, height).apply()
    }

    override fun saveActivityLevel(level: ActivityLevel) {
        sharedPref.edit().putString(KEY_ACTIVITY_LEVEL, level.name).apply()
    }

    override fun saveGoalType(type: GoalType) {
        sharedPref.edit().putString(KEY_GOAL_TYPE, type.name).apply()
    }

    override fun saveCarbRatio(ratio: Float) {
        sharedPref.edit().putFloat(KEY_CARB_RATIO, ratio).apply()
    }

    override fun saveProteinRatio(ratio: Float) {
        sharedPref.edit().putFloat(KEY_PROTEIN_RATIO, ratio).apply()
    }

    override fun saveFatRatio(ratio: Float) {
        sharedPref.edit().putFloat(KEY_FAT_RATIO, ratio).apply()
    }

    override fun loadUserInfo(): UserInfo {
        val age = sharedPref.getInt(KEY_AGE, -1)
        val height = sharedPref.getInt(KEY_HEIGHT, -1)
        val weight = sharedPref.getFloat(KEY_WEIGHT, -1f)
        val genderString = sharedPref.getString(KEY_GENDER, null)
        val activityLevelString = sharedPref.getString(KEY_ACTIVITY_LEVEL, null)
        val goalTypeString = sharedPref.getString(KEY_GOAL_TYPE, null)
        val carbRatio = sharedPref.getFloat(KEY_CARB_RATIO, -1f)
        val proteinRatio = sharedPref.getFloat(KEY_PROTEIN_RATIO, -1f)
        val fatRatio = sharedPref.getFloat(KEY_FAT_RATIO, -1f)

        return UserInfo(
            age = age,
            height = height,
            weight = weight,
            gender = Gender.fromString(genderString ?: "male"),
            activityLevel = ActivityLevel.fromString(activityLevelString ?: "medium"),
            goalType = GoalType.fromString(goalTypeString ?: "keep_weight"),
            carbRatio = carbRatio,
            proteinRatio = proteinRatio,
            fatRatio = fatRatio


        )
    }
}