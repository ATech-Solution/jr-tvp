package com.atech.feature_home.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atech.domain.entities.StudentsModelItem
import com.atech.feature_home.databinding.ItemPeopleBinding

class PeopleAdapter(
    private val onClickItem: (StudentsModelItem) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = mutableListOf<StudentsModelItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<StudentsModelItem>) {
        this.data.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPeopleBinding.inflate(inflater, parent,false)
        return PeopleItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PeopleItemViewHolder).bind(data[position])
        holder.itemPeopleBinding.root.setOnClickListener {
            onClickItem.invoke(data[position])
        }
    }

    inner class PeopleItemViewHolder(val itemPeopleBinding: ItemPeopleBinding) :
        RecyclerView.ViewHolder(itemPeopleBinding.root) {

        fun bind(model: StudentsModelItem) {
            itemPeopleBinding.txtName.text = model.name
        }

    }
}