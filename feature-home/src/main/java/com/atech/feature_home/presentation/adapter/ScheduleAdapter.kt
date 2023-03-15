package com.atech.feature_home.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atech.feature_home.databinding.ItemClassroomBinding
import com.atech.feature_home.databinding.ItemScheduleBinding

class ScheduleAdapter(
    private val onClickItem: (Int) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = mutableListOf<Int>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<Int>) {
        this.data.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemScheduleBinding.inflate(inflater, parent,false)
        return ScheduleItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ScheduleItemViewHolder).bind(data[position])
        holder.itemScheduleBinding.root.setOnClickListener {
            onClickItem.invoke(data[position])
        }
    }

    inner class ScheduleItemViewHolder(val itemScheduleBinding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(itemScheduleBinding.root) {

        fun bind(model: Int) {

        }

    }
}