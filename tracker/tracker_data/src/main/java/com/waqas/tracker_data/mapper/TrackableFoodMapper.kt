package com.waqas.tracker_data.mapper

import com.waqas.tracker_data.local.entity.TrackerFoodEntity
import com.waqas.tracker_data.remote.dto.Product
import com.waqas.tracker_domain.model.MealType
import com.waqas.tracker_domain.model.TrackableFood
import com.waqas.tracker_domain.model.TrackedFood
import java.time.LocalDate
import kotlin.math.roundToInt

fun Product.toTrackableFood(): TrackableFood {
    val carbsPer100g = nutriments.carbohydrates100g.roundToInt()
    val proteinPer100g = nutriments.proteins100g.roundToInt()
    val fatPer100g = nutriments.fat100g.roundToInt()
    val caloriesPer100g = nutriments.energyKcal100g.roundToInt()
    return TrackableFood(
        name = productName,
        carbsPer100g = carbsPer100g,
        proteinPer100g = proteinPer100g,
        fatPer100g = fatPer100g,
        caloriesPer100g = caloriesPer100g,
        imageUrl = imageFrontThumbUrl
    )
}
