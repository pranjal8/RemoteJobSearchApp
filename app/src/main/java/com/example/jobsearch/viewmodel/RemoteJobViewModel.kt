package com.example.jobsearch.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobsearch.models.FavoriteJob
import com.example.jobsearch.repository.RemoteJobRepository
import kotlinx.coroutines.launch

class RemoteJobViewModel(app: Application , private val remoteJobRepository: RemoteJobRepository) : AndroidViewModel(app) {

    //fun to display data from API
    fun remoteJobResult() = remoteJobRepository.remoteJobResult()


    //functions to manipulate data from Room db
    fun addFavJob(job:FavoriteJob) =viewModelScope.launch {

        remoteJobRepository.addFavJob(job)
    }
    fun deleteJob(job:FavoriteJob) =viewModelScope.launch {

        remoteJobRepository.deleteJob(job)

    }
    fun getAllLikedJobs() =remoteJobRepository.getAllFavJobs().getAllFavJob()


    //for retrofit search query
    fun searchRemoteJob(query :String?) =remoteJobRepository.searchJobResponse(query)

    fun searchResult() =remoteJobRepository.searchJobResult()

}