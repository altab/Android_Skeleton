package fr.pcohen.app.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ConstatCaseMediaDao {

    @Query("SELECT * FROM ${ContestCaseMedia.TABLE_NAME}")
    fun getAllLiveData(): LiveData<List<ContestCaseMedia>>

    @Query("SELECT * FROM ${ContestCaseMedia.TABLE_NAME}")
    fun getAll(): List<ContestCaseMedia>

    @Insert
    fun insert(contestCases: ContestCaseMedia)

    @Update
    fun update(contestCase: ContestCaseMedia)

    @Delete
    fun delete(contestCase: ContestCaseMedia)

    @Query("DELETE FROM ${ContestCaseMedia.TABLE_NAME}")
    fun deleteAll()

}