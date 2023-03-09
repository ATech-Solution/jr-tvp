package com.atech.data.datasource

import android.content.Context
import com.atech.data.R
import com.atech.data.dtos.MovieDto
import com.atech.domain.util.StubUtil
import io.reactivex.Flowable
import javax.inject.Inject

class StubDataSourceImpl @Inject constructor(
    private val context: Context,
    private val stubUtil: StubUtil
) : StubDataSource {
    override fun getPopularMovies(): Flowable<MovieDto> =
        Flowable.just(
            stubUtil.parseInto(
                jsonString = stubUtil.getJsonFromRaw(context, R.raw.popular_stub),
                classOfT = MovieDto::class.java,
                defaultObject = MovieDto(0, 0, 0, listOf<MovieDto.Movie>())
            )
        )

    override fun getTopRatedMovies(): Flowable<MovieDto> =
        Flowable.just(
            stubUtil.parseInto(
                jsonString = stubUtil.getJsonFromRaw(context, R.raw.top_rated_stub),
                classOfT = MovieDto::class.java,
                defaultObject = MovieDto(0, 0, 0, listOf<MovieDto.Movie>())
            )
        )

    override fun getNowPlayingMovies(): Flowable<MovieDto> =
        Flowable.just(
            stubUtil.parseInto(
                jsonString = stubUtil.getJsonFromRaw(context, R.raw.now_playing_stub),
                classOfT = MovieDto::class.java,
                defaultObject = MovieDto(0, 0, 0, listOf<MovieDto.Movie>())
            )
        )
}