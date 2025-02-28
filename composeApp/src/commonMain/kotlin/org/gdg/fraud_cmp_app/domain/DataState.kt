package org.gdg.fraud_cmp_app.domain

sealed class DataState<out T> {
    object Loading : DataState<Nothing>()
    data class Success<out T : Any?>(val data: T) : DataState<T>()
    data class Error(val error: String) : DataState<Nothing>()
}








