package com.atech.feature_home.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atech.domain.entities.ReviewModel
import com.atech.feature_home.databinding.ItemReviewsBinding

/**
 * Created by Abraham Lay on 2019-10-13.
 */
class ReviewAdapter(private val data: List<ReviewModel>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, pos: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding =
            ItemReviewsBinding.inflate(inflater, viewGroup,false)
        return ReviewItemViewHolder(binding)
    }

    var onClickListener: OnClickListener? = null

    override fun getItemCount(): Int {
        return data?.size!!
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, pos: Int) {
        (viewHolder as ReviewItemViewHolder).bind(data?.get(pos)!!)
        viewHolder.setOnClickListener(View.OnClickListener {
            onClickListener?.onItemClicked(
                data[pos]
            )
        })
    }

    interface OnClickListener {
        fun onItemClicked(data: Any)
    }

    inner class ReviewItemViewHolder(val itemReviewBinding: ItemReviewsBinding) :
        RecyclerView.ViewHolder(itemReviewBinding.root) {

        fun bind(reviewModel: ReviewModel) {
            itemReviewBinding.tvName.text = reviewModel.author
            itemReviewBinding.tvOpinion.text = reviewModel.content
        }

        fun setOnClickListener(listener: View.OnClickListener) {
            itemView.setOnClickListener(listener)
        }
    }
}