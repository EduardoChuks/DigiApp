package com.edu.labs.digiapp.domain.interactor

sealed class UseCaseResult<out T : Any> {

    class Success<out T : Any>(val data: T): UseCaseResult<T>()
    class Error(val errorMsg: String): UseCaseResult<Nothing>()

}