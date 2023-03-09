package com.atech.data.datasource

import com.atech.data.dtos.*
import com.atech.domain.entities.MovieModel
import io.reactivex.Flowable

interface MovieDataSource {
    fun getDiscoverMoviesByGenre(apiKey: String, map: HashMap<String, Any>): Flowable<MovieDto>
    fun getGenres(apiKey: String): Flowable<GenresDto>
    fun getReviews(movieId: Int, apiKey: String): Flowable<ReviewDto>
    fun getVideo(movieId: Int, apiKey: String): Flowable<VideoDto>
    fun getMovieDetails(movieId: Int, apiKey: String): Flowable<DetailMovieDto>
    fun getPopularMovies(apiKey: String): Flowable<MovieDto>
    fun getTopRatedMovies(apiKey: String): Flowable<MovieDto>
    fun getNowPlayingMovies(apiKey: String): Flowable<MovieDto>
    fun getFavoriteMovies(): Flowable<List<MovieModel>?>
    fun getFavoriteMovie(movieId: Int): Flowable<MovieModel?>
    fun insertFavoriteMovie(movieModel: MovieModel): Long
    fun deleteFavoriteMovie(movieModel: MovieModel): Int
}