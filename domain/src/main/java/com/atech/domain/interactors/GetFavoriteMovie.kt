package com.atech.domain.interactors

import com.atech.domain.FlowableUseCase
import com.atech.domain.PostExecutionThread
import com.atech.domain.entities.MovieModel
import com.atech.domain.repositories.MovieRepository
import io.reactivex.Flowable

/**
 * Created by Abraham Lay on 13/06/20.
 */
class GetFavoriteMovie constructor(
    private val repository: MovieRepository,
    postExecutionThread: PostExecutionThread
): FlowableUseCase<MovieModel?, GetFavoriteMovie.Params>(postExecutionThread) {
    override fun build(params: Params): Flowable<MovieModel?> {
        return repository.getFavoriteMovie(params.movieId)
    }

    data class Params(val movieId: Int)
}
