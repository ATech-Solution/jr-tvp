package com.atech.data.repositoriesimpl

import com.atech.data.mapper.DetailMovieMapper
import com.atech.data.mapper.MovieMapper
import com.atech.data.mapper.ReviewMapper
import com.atech.data.mapper.VideoMapper
import com.atech.data.datasource.MovieDataSource
import com.atech.data.datasource.StubDataSource
import com.atech.domain.entities.DetailMovieModel
import com.atech.domain.entities.MovieModel
import com.atech.domain.entities.ReviewModel
import com.atech.domain.entities.VideoModel
import com.atech.domain.repositories.MovieRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by Abraham Lay on 2020-06-09.
 */
class MovieRepositoryImpl @Inject constructor(
    private val source: MovieDataSource,
    private val stubDataSource: StubDataSource,
    private val movieMapper: MovieMapper,
    private val reviewMapper: ReviewMapper,
    private val videoMapper: VideoMapper,
    private val detailMovieMapper: DetailMovieMapper
): MovieRepository {
  
    var isUsingDummyData = false

    override fun getPopularMovies(apiKey: String): Flowable<List<MovieModel>?> =
        if (isUsingDummyData) stubDataSource.getPopularMovies().map(movieMapper) else source.getPopularMovies(apiKey).map(movieMapper)

    override fun getTopRatedMovies(apiKey: String): Flowable<List<MovieModel>?> =
        if (isUsingDummyData) stubDataSource.getTopRatedMovies().map(movieMapper) else source.getTopRatedMovies(apiKey).map(movieMapper)

    override fun getNowPlayingMovies(apiKey: String): Flowable<List<MovieModel>?> =
        if (isUsingDummyData) stubDataSource.getNowPlayingMovies().map(movieMapper) else source.getNowPlayingMovies(apiKey).map(movieMapper)

    override fun getReviews(apiKey: String, movieId: Int): Flowable<List<ReviewModel>> =
        source.getReviews(movieId, apiKey).map(reviewMapper)

    override fun getVideo(apiKey: String, movieId: Int): Flowable<List<VideoModel>> =
        source.getVideo(movieId, apiKey).map(videoMapper)

    override fun getMovieDetails(apiKey: String, movieId: Int): Flowable<DetailMovieModel> =
        source.getMovieDetails(movieId, apiKey).map(detailMovieMapper)

    override fun getFavoriteMovies(): Flowable<List<MovieModel>?> =
        source.getFavoriteMovies()

    override fun getFavoriteMovie(movieId: Int): Flowable<MovieModel?> =
        source.getFavoriteMovie(movieId)

    override fun insertFavoriteMovie(movieModel: MovieModel) =
        source.insertFavoriteMovie(movieModel)

    override fun deleteFavoriteMovie(movieModel: MovieModel): Int =
        source.deleteFavoriteMovie(movieModel)
}