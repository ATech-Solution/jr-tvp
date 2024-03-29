package com.atech.domain.subscriber

/**
 * Created by Dr.jacky on 10/9/2018.
 */
/**
 * A wrapper for database and network states.
 */
sealed class ResultState<T> {

    /**
     * A state of [data] which shows that we know there is still an update to come.
     */
    class Loading<T> : ResultState<T>()

    /**
     * A state that shows the [data] is the last known update.
     */
    data class Success<T>(val data: T) : ResultState<T>()

    /**
     * A state to show a [throwable] is thrown.
     */
    data class Error<T>(val throwable: Throwable) : ResultState<T>()
}