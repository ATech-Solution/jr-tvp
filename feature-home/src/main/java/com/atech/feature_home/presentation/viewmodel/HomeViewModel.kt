package com.atech.feature_home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atech.base.config.Constants
import com.atech.base.viewmodel.BaseViewModel
import com.atech.domain.entities.MovieModel
import com.atech.domain.interactors.GetFavoriteMovies
import com.atech.domain.interactors.GetNowPlayingMovies
import com.atech.domain.interactors.GetPopularMovies
import com.atech.domain.interactors.GetTopRatedMovies
import com.atech.domain.subscriber.DefaultSubscriber
import com.atech.domain.subscriber.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val getTopRatedMovies: GetTopRatedMovies,
    private val getNowPlayingMovies: GetNowPlayingMovies,
    private val getFavoriteMovies: GetFavoriteMovies
) : BaseViewModel() {

    private val _popularMovies = MutableLiveData<ResultState<List<MovieModel>?>>()
    val popularMovies: LiveData<ResultState<List<MovieModel>?>> = _popularMovies

    fun fetchPopularMovie() {
        getPopularMovies.execute(
            object : DefaultSubscriber<List<MovieModel>?>() {
                override fun onError(error: ResultState<List<MovieModel>?>) {
                    val message = (error as ResultState.Error).throwable.message
                    Timber.d("Error : $message")
                    _popularMovies.value = error
                }

                override fun onSuccess(data: ResultState<List<MovieModel>?>) {
                    val list = (data as ResultState.Success).data
                    Timber.d("Success Popular Fetched : $list")
                    _popularMovies.value = data
                }
            }, GetPopularMovies.Params(Constants.API_KEY)
        )
    }

    private val _topRatedMovies = MutableLiveData<ResultState<List<MovieModel>?>>()
    val topRatedMovies: LiveData<ResultState<List<MovieModel>?>> = _topRatedMovies
    fun fetchTopRatedMovie() {
        getTopRatedMovies.execute(
            object : DefaultSubscriber<List<MovieModel>?>() {
                override fun onError(error: ResultState<List<MovieModel>?>) {
                    val message = (error as ResultState.Error).throwable.message
                    Timber.d("Error : $message")
                    _topRatedMovies.value = error
                }

                override fun onSuccess(data: ResultState<List<MovieModel>?>) {
                    val list = (data as ResultState.Success).data
                    Timber.d("Success Top Rated Fetched : $list")
                    _topRatedMovies.value = data
                }
            }, GetTopRatedMovies.Params(Constants.API_KEY)
        )
    }

    private val _nowPlayingMovies = MutableLiveData<ResultState<List<MovieModel>?>>()
    val nowPlayingMovies: LiveData<ResultState<List<MovieModel>?>> = _nowPlayingMovies

    fun fetchNowPlayingMovie() {
        getNowPlayingMovies.execute(
            object : DefaultSubscriber<List<MovieModel>?>() {
                override fun onError(error: ResultState<List<MovieModel>?>) {
                    val message = (error as ResultState.Error).throwable.message
                    Log.d(TAG, "Error : $message")
                    _nowPlayingMovies.value = error
                }

                override fun onSuccess(data: ResultState<List<MovieModel>?>) {
                    val list = (data as ResultState.Success).data
                    Log.d(TAG, "Success NowPlaying Fetched : $list")
                    _nowPlayingMovies.value = data
                }
            }, GetNowPlayingMovies.Params(Constants.API_KEY)
        )
    }

    private val _favoriteMovies = MutableLiveData<ResultState<List<MovieModel>?>>()
    val favoriteMovies: LiveData<ResultState<List<MovieModel>?>> = _favoriteMovies

    fun fetchFavoriteMovies() {
        getFavoriteMovies.execute(
            object : DefaultSubscriber<List<MovieModel>?>() {
                override fun onError(error: ResultState<List<MovieModel>?>) {
                    val message = (error as ResultState.Error).throwable.message
                    Log.d(TAG, "Error : $message")
                    _favoriteMovies.value = error
                }

                override fun onSuccess(data: ResultState<List<MovieModel>?>) {
                    val list = (data as ResultState.Success).data
                    Log.d(TAG, "Success Popular Fetched : $list")
                    _favoriteMovies.value = data
                }
            }, null
        )
    }

    override fun onCleared() {
        getPopularMovies.dispose()
        getNowPlayingMovies.dispose()
        getTopRatedMovies.dispose()
        super.onCleared()
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}