package com.atech.domain.interactors

import com.atech.domain.FlowableUseCase
import com.atech.domain.PostExecutionThread
import com.atech.domain.entities.DetailMovieModel
import com.atech.domain.repositories.MovieRepository
import io.reactivex.Flowable

/**
 * Created by Abraham Lay on 10/06/20.
 */
class GetDetailMovie constructor(
    private val repository: MovieRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<DetailMovieModel, GetDetailMovie.Params>(postExecutionThread) {

    override fun build(params: Params): Flowable<DetailMovieModel> {
        return repository.getMovieDetails(params.apiKey, params.movieId)
    }

    data class Params(val apiKey: String, val movieId: Int)
}