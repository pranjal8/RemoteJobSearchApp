package com.example.jobsearch.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jobsearch.api.RetrofitInstance
import com.example.jobsearch.db.FavoriteJobDatabase
import com.example.jobsearch.models.FavoriteJob
import com.example.jobsearch.models.RemoteJobResponse
import com.example.jobsearch.utils.Constant.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RemoteJobRepository(private val db : FavoriteJobDatabase) {

    private val remoteJobService = RetrofitInstance.api
    private val remoteJobResponseLiveData: MutableLiveData<RemoteJobResponse> = MutableLiveData()
    private val searchJobResponseLiveData: MutableLiveData<RemoteJobResponse> = MutableLiveData()

    init {
        getRemoteJobResponse()
    }

    private fun getRemoteJobResponse(){

        remoteJobService.getRemoteJob().enqueue( object : Callback<RemoteJobResponse> {

            override fun onResponse(call: Call<RemoteJobResponse>, response: Response<RemoteJobResponse>) {
               remoteJobResponseLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<RemoteJobResponse>, t: Throwable) {
                remoteJobResponseLiveData.postValue(null)
                Log.e(TAG, "Failure ${t.message}")
            }
        })
    }

    fun remoteJobResult () : LiveData<RemoteJobResponse> {
        return remoteJobResponseLiveData
    }

    //access Room db
    suspend fun addFavJob(job : FavoriteJob) = db.getRemoteJobDao().addFavJob(job)

    suspend fun deleteJob(job: FavoriteJob) =db.getRemoteJobDao().deleteAllFavJob(job)

    fun getAllFavJobs() =db.getRemoteJobDao()


    //for retrofit search query
    fun searchJobResponse(query: String?){

        remoteJobService.searchRemoteJob(query).enqueue(object : Callback<RemoteJobResponse>{

            override fun onResponse(call: Call<RemoteJobResponse>, response: Response<RemoteJobResponse>) {
                searchJobResponseLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<RemoteJobResponse>, t: Throwable) {
                searchJobResponseLiveData.postValue(null)
            }
        })
    }

    fun searchJobResult() : LiveData<RemoteJobResponse>{
        return searchJobResponseLiveData
    }

}