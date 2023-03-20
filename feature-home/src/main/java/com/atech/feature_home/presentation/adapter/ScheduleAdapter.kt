package com.atech.feature_home.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atech.domain.entities.SchedulesModel
import com.atech.feature_home.databinding.ItemScheduleBinding
import java.text.SimpleDateFormat
import java.util.*

class ScheduleAdapter(
    private val isStudent: Boolean,
    private val onClickItem: (SchedulesModel) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = mutableListOf<SchedulesModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<SchedulesModel>) {
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
    }

    inner class ScheduleItemViewHolder(private val itemScheduleBinding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(itemScheduleBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: SchedulesModel) {
            itemScheduleBinding.txtSchedule.text = convertDateRange(
                model.start_time,
                model.finish_time
            )
            itemScheduleBinding.btnTakeAttendance.apply {
                visibility = if (isStudent) {
                    View.GONE
                } else {
                    View.VISIBLE
                }

                setOnClickListener {
                    onClickItem.invoke(model)
                }
            }
        }

    }

    private fun convertDateRange(startTime: String, endTime: String): String {
        val oldPattern = "yyyy-MM-dd HH:mm:ss"
        val yearPattern = "EEE dd MMM"
        val timePattern = "HH:mm"
        val oldFormat = SimpleDateFormat(oldPattern, Locale.getDefault())
        val currentStart = oldFormat.parse(startTime)
        val currentEnd = oldFormat.parse(endTime)
        val newYearFormat = SimpleDateFormat(yearPattern, Locale.getDefault())
        val currentYear = newYearFormat.format(currentStart ?: Date())
        val newTimeFormat = SimpleDateFormat(timePattern, Locale.getDefault())
        return "$currentYear, ${newTimeFormat.format(currentStart ?: Date())} - ${
            newTimeFormat.format(
                currentEnd ?: Date()
            )
        }"
    }
}