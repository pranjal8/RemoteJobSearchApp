package com.example.jobsearch.models

import com.google.gson.annotations.SerializedName

data class RemoteJobResponse(
    @SerializedName("job-count")
    val jobCount: Int?,
    val jobs: List<Job>?,
    val legalNotice: String?
)


