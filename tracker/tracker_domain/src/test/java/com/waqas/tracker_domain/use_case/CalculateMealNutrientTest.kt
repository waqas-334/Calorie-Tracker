package com.waqas.tracker_domain.use_case

import com.google.common.truth.Truth.assertThat
import com.waqas.core.domain.model.ActivityLevel
import com.waqas.core.domain.model.Gender
import com.waqas.core.domain.model.GoalType
import com.waqas.core.domain.model.UserInfo
import com.waqas.core.domain.preferences.Preferences
import com.waqas.tracker_domain.model.MealType
import com.waqas.tracker_domain.model.TrackedFood
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

class CalculateMealNutrientTest {

    private lateinit var calculateMealNutrients: CalculateMealNutrients

    @Before
    fun setup() {
        val preferences: Preferences = mockk(relaxed = true)
        every { preferences.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 30, weight = 80f,
            height = 180,
            activityLevel = ActivityLevel.Medium,
            goalType = GoalType.KeepWeight,
            carbRatio = 0.4f,
            proteinRatio = 0.3f,
            fatRatio = 0.3f

        )
        calculateMealNutrients = CalculateMealNutrients(preferences)
    }

    @Test
    fun `calories for dinner properly calculated`() {
        val trackedFood = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf(
                        "breakfast",
                        "lunch",
                        "dinner",
                        "snack"
                    ).random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }


        val result = calculateMealNutrients(trackedFood)

        val breakfastCalories = result.mealNutrients.values
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.calories }

        val expectedCalories = trackedFood
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.calories }


        assertThat(breakfastCalories).isEqualTo(expectedCalories)


    }

    @Test
    fun `carbs for breakfast properly calculated`() {
        val trackedFood = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf(
                        "breakfast",
                        "lunch",
                        "dinner",
                        "snack"
                    ).random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }


        val result = calculateMealNutrients(trackedFood)

        val dinnerCarbs = result.mealNutrients.values
            .filter { it.mealType is MealType.Dinner }
            .sumOf { it.carbs }

        val expectedCarbs = trackedFood
            .filter { it.mealType is MealType.Dinner }
            .sumOf { it.carbs }


        assertThat(dinnerCarbs).isEqualTo(expectedCarbs)


    }


}