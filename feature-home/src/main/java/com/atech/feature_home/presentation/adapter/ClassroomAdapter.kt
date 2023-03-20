package com.atech.feature_home.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atech.domain.entities.ClassScheduleModelItem
import com.atech.feature_home.databinding.ItemClassroomBinding

class ClassroomAdapter(
    private val onClickItem: (ClassScheduleModelItem) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = mutableListOf<ClassScheduleModelItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<ClassScheduleModelItem>) {
        this.data.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemClassroomBinding.inflate(inflater, parent,false)
        return ClassroomItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ClassroomItemViewHolder).bind(data[position])
        holder.itemClassroomBinding.root.setOnClickListener {
            onClickItem.invoke(data[position])
        }
    }

    inner class ClassroomItemViewHolder(val itemClassroomBinding: ItemClassroomBinding) :
        RecyclerView.ViewHolder(itemClassroomBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: ClassScheduleModelItem) {
            itemClassroomBinding.apply {
                txtClassTitle.text = "${model.category} - ${model.location}"
                txtClassDetail.text = model.time_summary
                txtAction.text = "${model.duration} minutes"
            }
        }

    }
}