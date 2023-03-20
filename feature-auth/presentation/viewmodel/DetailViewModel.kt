package com.atech.feature_home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.atech.base.config.Constants
import com.atech.base.viewmodel.BaseViewModel
import com.atech.domain.entities.DetailMovieModel
import com.atech.domain.entities.MovieModel
import com.atech.domain.entities.ReviewModel
import com.atech.domain.entities.VideoModel
import com.atech.domain.interactors.*
import com.atech.domain.subscriber.DefaultSubscriber
import com.atech.domain.subscriber.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getReviews: GetReviews,
    private val getVideos: GetVideos,
    private val insertFavoriteMovie: InsertFavoriteMovie,
    private val deleteFavoriteMovie: DeleteFavoriteMovie,
    private val getFavoriteMovie: GetFavoriteMovie,
    private val getDetailMovie: GetDetailMovie
) : BaseViewModel() {

    private val _getVideos = MutableLiveData<ResultState<List<VideoModel>>>()
    val getVideosLiveData: LiveData<ResultState<List<VideoModel>>> = _getVideos
    fun fetchVideos(movieId: Int) {
        getVideos.execute(
            object : DefaultSubscriber<List<VideoModel>>() {
                override fun onError(error: ResultState<List<VideoModel>>) {
                    val message: String? = (error as ResultState.Error).throwable.message
                    Log.d(TAG, "Error : $message")
                    _getVideos.value = error
                }

                override fun onSuccess(data: ResultState<List<VideoModel>>) {
                    val list = (data as ResultState.Success).data
                    Log.d(TAG, "Success Popular Fetched : $list")
                    _getVideos.value = data
                }
            }, GetVideos.Params(Constants.API_KEY, movieId)
        )
    }

    private val _getReview = MutableLiveData<ResultState<List<ReviewModel>>>()
    val getReviewLiveData: LiveData<ResultState<List<ReviewModel>>> = _getReview
    fun fetchReviews(movieId: Int) {
        getReviews.execute(
            object : DefaultSubscriber<List<ReviewModel>>() {
                override fun onError(error: ResultState<List<ReviewModel>>) {
                    val message: String? = (error as ResultState.Error).throwable.message
                    Log.d(TAG, "Error : $message")
                    _getReview.value = error
                }

                override fun onSuccess(data: ResultState<List<ReviewModel>>) {
                    val list = (data as ResultState.Success).data
                    Log.d(TAG, "Success Popular Fetched : $list")
                    _getReview.value = data
                }
            }, GetReviews.Params(Constants.API_KEY, movieId)
        )
    }

    private val _getFavoriteMovie = MutableLiveData<ResultState<MovieModel?>>()
    val getFavoriteMovieLiveData: LiveData<ResultState<MovieModel?>> = _getFavoriteMovie
    fun fetchFavoriteMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getFavoriteMovie.execute(
                object : DefaultSubscriber<MovieModel?>() {
                    override fun onError(error: ResultState<MovieModel?>) {
                        val message: String? = (error as ResultState.Error).throwable.message
                        Log.d(TAG, "Error : $message")
                        _getFavoriteMovie.value = error
                    }

                    override fun onSuccess(data: ResultState<MovieModel?>) {
                        val list = (data as ResultState.Success).data
                        Log.d(TAG, "Success Popular Fetched : $list")
                        _getFavoriteMovie.value = data
                    }
                }, GetFavoriteMovie.Params(movieId)
            )
        }
    }

    private val _insertMovie = MutableLiveData<ResultState<Long>>()
    val insertMovieLiveData: LiveData<ResultState<Long>> = _insertMovie
    fun insertFavoriteMovie(movieModel: MovieModel) {
        viewModelScope.launch(Dispatchers.IO) {
            insertFavoriteMovie.execute(object : DefaultSubscriber<Long>() {

                override fun onError(error: ResultState<Long>) {
                    _insertMovie.postValue(error)
                }

                override fun onSuccess(data: ResultState<Long>) {
                    _insertMovie.postValue(data)
                }
            }, InsertFavoriteMovie.Params(movieModel))
        }
    }

    private val _deleteMovie = MutableLiveData<ResultState<Int>>()
    val deleteMovieLiveData: LiveData<ResultState<Int>> = _deleteMovie
    fun deleteFavoriteMovie(movieModel: MovieModel) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteFavoriteMovie.execute(object : DefaultSubscriber<Int>() {

                override fun onError(error: ResultState<Int>) {
                    _deleteMovie.postValue(error)
                }

                override fun onSuccess(data: ResultState<Int>) {
                    _deleteMovie.postValue(data)
                }
            }, DeleteFavoriteMovie.Params(movieModel))
        }
    }

    private val _getDetailMovie = MutableLiveData<ResultState<DetailMovieModel>>()
    val getDetailMovieLiveData: LiveData<ResultState<DetailMovieModel>> = _getDetailMovie
    fun fetchDetailMovie(movieId: Int) {
        getDetailMovie.execute(
            object : DefaultSubscriber<DetailMovieModel>() {
                override fun onError(error: ResultState<DetailMovieModel>) {
                    val message: String? = (error as ResultState.Error).throwable.message
                    Log.d(TAG, "Error : $message")
                    _getDetailMovie.value = error
                }

                override fun onSuccess(data: ResultState<DetailMovieModel>) {
                    val list = (data as ResultState.Success).data
                    Log.d(TAG, "Success Popular Fetched : $list")
                    _getDetailMovie.value = data
                }
            }, GetDetailMovie.Params(Constants.API_KEY, movieId)
        )
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}