package com.atech.data.mapper

import com.atech.data.dtos.MovieDto
import com.atech.domain.entities.MovieModel

/**
 * Created by Abraham Lay on 14/06/20.
 */

class MovieMapper : Mapper<MovieDto?, List<MovieModel>?>() {
    override fun apply(from: MovieDto?): List<MovieModel>? {
        return from?.results?.map { movie ->
            MovieModel(
                movie.voteCount,
                movie.id,
                movie.video,
                movie.voteAverage,
                movie.originalTitle,
                movie.popularity,
                movie.posterPath,
                movie.originalLanguage,
                movie.originalTitle,
                movie.genreIds,
                movie.backdropPath,
                movie.adult,
                movie.overview,
                movie.releaseDate

            )
        }
    }
}