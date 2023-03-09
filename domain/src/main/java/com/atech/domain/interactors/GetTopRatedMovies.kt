package com.atech.domain.interactors

import com.atech.domain.FlowableUseCase
import com.atech.domain.PostExecutionThread
import com.atech.domain.entities.MovieModel
import com.atech.domain.repositories.MovieRepository
import io.reactivex.Flowable

/**
 * Created by Abraham Lay on 2019-09-29.
 */
class GetTopRatedMovies constructor(
    private val repository: MovieRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<List<MovieModel>?, GetTopRatedMovies.Params>(postExecutionThread) {

    override fun build(params: Params): Flowable<List<MovieModel>?> {
        return repository.getTopRatedMovies(params.apiKey)
    }

    data class Params(
        val apiKey: String
    )
}