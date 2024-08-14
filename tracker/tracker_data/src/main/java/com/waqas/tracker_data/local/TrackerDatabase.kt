package com.waqas.tracker_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.waqas.tracker_data.local.entity.TrackerFoodEntity

@Database(
    entities = [TrackerFoodEntity::class],
    version = 1
)
abstract class TrackerDatabase : RoomDatabase() {


    abstract val dao: TrackerDao


}