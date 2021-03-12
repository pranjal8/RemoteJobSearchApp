package com.example.jobsearch.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.jobsearch.models.FavoriteJob

@Dao
interface FavoriteJobDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavJob(job: FavoriteJob): Long

    @Delete
    suspend fun deleteAllFavJob(job: FavoriteJob)

    @Query("SELECT * FROM Fav_job ORDER BY id DESC")
    fun getAllFavJob(): LiveData<List<FavoriteJob>>


}