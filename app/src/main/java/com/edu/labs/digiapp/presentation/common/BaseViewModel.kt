package com.edu.labs.digiapp.presentation.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

abstract class BaseViewModel: ViewModel() {

    var job: Job? = null

    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}