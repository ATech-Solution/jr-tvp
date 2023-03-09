package com.atech.domain.interactors

import com.atech.domain.FlowableUseCase
import com.atech.domain.PostExecutionThread
import com.atech.domain.entities.VideoModel
import com.atech.domain.repositories.MovieRepository
import io.reactivex.Flowable

/**
 * Created by Abraham Lay on 10/06/20.
 */

class GetVideos constructor(
    private val repository: MovieRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<List<VideoModel>, GetVideos.Params>(postExecutionThread) {
    override fun build(params: Params): Flowable<List<VideoModel>> {
        return repository.getVideo(params.apiKey, params.movieId)
    }

    data class Params(val apiKey: String, val movieId: Int)
}