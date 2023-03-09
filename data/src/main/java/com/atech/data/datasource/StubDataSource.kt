package com.atech.data.datasource

import com.atech.data.dtos.MovieDto
import io.reactivex.Flowable

interface StubDataSource {
    fun getPopularMovies(): Flowable<MovieDto>
    fun getTopRatedMovies(): Flowable<MovieDto>
    fun getNowPlayingMovies(): Flowable<MovieDto>
}