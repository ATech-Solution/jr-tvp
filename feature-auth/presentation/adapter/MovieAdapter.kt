package com.atech.feature_home.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atech.base.config.Constants
import com.atech.base.util.DateFormater
import com.atech.base.util.GlideHelper
import com.atech.domain.entities.MovieModel
import com.atech.feature_home.R
import com.atech.feature_home.databinding.ItemMovieHeaderBinding
import com.atech.feature_home.databinding.ItemMovieSectionBinding

/**
 * Created by Abraham Lay on 2019-10-13.
 */
class MovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, pos: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)


        return if (isHeader) MovieItemHeaderViewHolder(
            ItemMovieHeaderBinding.bind(
                inflater.inflate(
                    R.layout.item_movie_header,
                    viewGroup,
                    false
                )
            )
        )
        else
            MovieItemSectionViewHolder(
                ItemMovieSectionBinding.bind(
                    inflater.inflate(
                        R.layout.item_movie_section,
                        viewGroup,
                        false
                    )
                )
            )

    }

    var isHeader: Boolean = false

    var data: List<MovieModel>? = arrayListOf()

    var onClickListener: OnClickListener? = null

    override fun getItemCount(): Int {
        return data?.size!!
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, pos: Int) {
        when (viewHolder) {
            is MovieItemHeaderViewHolder -> {
                setItemHeaderData(viewHolder, pos)
            }
            is MovieItemSectionViewHolder -> {
                setItemHeaderData(viewHolder, pos)
            }
        }
    }

    private fun setItemHeaderData(viewHolder: MovieItemHeaderViewHolder, pos: Int) {
        viewHolder.bind(data!![pos])
        viewHolder.setOnClickListener(View.OnClickListener {
            onClickListener?.onItemClicked(
                data!![pos]
            )
        })
    }

    private fun setItemHeaderData(viewHolder: MovieItemSectionViewHolder, pos: Int) {
        viewHolder.bind(data!![pos])
        viewHolder.setOnClickListener(View.OnClickListener {
            onClickListener?.onItemClicked(
                data!![pos]
            )
        })
    }

    interface OnClickListener {
        fun onItemClicked(data: Any)
    }

    inner class MovieItemSectionViewHolder(private val itemMovieBinding: ItemMovieSectionBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root) {

        fun setOnClickListener(listener: View.OnClickListener) {
            itemView.setOnClickListener(listener)
        }

        fun bind(movieModel: MovieModel) = with(itemMovieBinding) {
            val url = movieModel.backdropPath
            url?.let {
                val addedUrl =
                    if (!url.contains("https://") || !url.contains("http://")) String.format(
                        Constants.MOVIE_THUMBNAIL_BASE_URL_LARGE,
                        url
                    ) else url
                GlideHelper.showThumbnail(addedUrl, ivMovie, itemView.context)
            }
            tvTitle.text = movieModel.title
            tvReleaseDate.text = DateFormater.changeDateFormat(movieModel.releaseDate)
        }
    }

    inner class MovieItemHeaderViewHolder(private val itemMovieBinding: ItemMovieHeaderBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root) {

        fun setOnClickListener(listener: View.OnClickListener) {
            itemView.setOnClickListener(listener)
        }

        fun bind(movieModel: MovieModel) = with(itemMovieBinding) {
            val url = movieModel.backdropPath
            url?.let {
                val addedUrl =
                    if (!url.contains("https://") || !url.contains("http://")) String.format(
                        Constants.MOVIE_THUMBNAIL_BASE_URL_LARGE,
                        url
                    ) else url
                GlideHelper.showThumbnail(addedUrl, ivMovie, itemView.context)
            }
            tvTitle.text = movieModel.title
        }
    }
}