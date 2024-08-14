package com.waqas.tracker_data.repository

import com.waqas.tracker_data.local.TrackerDao
import com.waqas.tracker_data.mapper.toTrackableFood
import com.waqas.tracker_data.mapper.toTrackedFood
import com.waqas.tracker_data.mapper.toTrackedFoodEntity
import com.waqas.tracker_data.remote.OpenFoodApi
import com.waqas.tracker_domain.model.TrackableFood
import com.waqas.tracker_domain.model.TrackedFood
import com.waqas.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao, //this is the local source
    private val api: OpenFoodApi, //this is the remote source
) : TrackerRepository {

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(
                query = query,
                page = page,
                pageSize = pageSize
            )
            Result.success(searchDto.products.map { it.toTrackableFood() })
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        dao.insertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodsForDate(localDate.dayOfMonth, localDate.dayOfMonth, localDate.dayOfYear)
            .map { entities ->
                entities.map { it.toTrackedFood() }
            }
    }
}