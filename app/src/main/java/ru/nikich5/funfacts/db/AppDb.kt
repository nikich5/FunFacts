package ru.nikich5.funfacts.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.nikich5.funfacts.dao.FactDao
import ru.nikich5.funfacts.entity.FactEntity

@Database(
    entities = [FactEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract fun factDao(): FactDao
}