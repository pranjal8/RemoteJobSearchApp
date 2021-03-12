package com.example.jobsearch.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Fav_job")
data class FavoriteJob(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val candidateRequiredLocation: String?,
    val category: String?,
    val companyLogoUrl: String?,
    val companyName: String?,
    val description: String?,
    val jobId: Int?,
    val jobType: String?,
    val publicationDate: String?,
    val salary: String?,
    val title: String?,
    val url: String?
)

