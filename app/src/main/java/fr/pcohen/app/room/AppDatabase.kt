package fr.pcohen.app.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ContestCaseMedia::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun constatCaseMediaDao(): ConstatCaseMediaDao
}