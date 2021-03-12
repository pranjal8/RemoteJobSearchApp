package com.example.jobsearch.api

import com.example.jobsearch.models.RemoteJobResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RemoteJobApi {

    @GET("remote-jobs")
    fun getRemoteJob(): Call<RemoteJobResponse>

    @GET("remote-jobs")
    fun searchRemoteJob(@Query("search") query: String?): Call<RemoteJobResponse>

}