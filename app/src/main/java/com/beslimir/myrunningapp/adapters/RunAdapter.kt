package com.beslimir.myrunningapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.beslimir.myrunningapp.R
import com.beslimir.myrunningapp.data.db.Run
import com.beslimir.myrunningapp.databinding.ItemRunBinding
import com.beslimir.myrunningapp.util.TrackingUtility
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    inner class RunViewHolder(private val itemRunBinding: ItemRunBinding) : RecyclerView.ViewHolder(itemRunBinding.root) {
        fun bind(run: Run) {
            Glide.with(itemView)
                .load(run.img)
                .into(itemRunBinding.ivRunImage)

            val calender = Calendar.getInstance().apply {
                timeInMillis = run.timestamp
            }
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            itemRunBinding.tvDate.text = dateFormat.format(calender.time)

            val avgSpeed = "${run.avgSpeedInKMH}km/h"
            itemRunBinding.tvAvgSpeed.text = avgSpeed

            val distanceInKM = "${run.distanceInMeters / 1000f}km"
            itemRunBinding.tvDistance.text = distanceInKM

            itemRunBinding.tvTime.text = TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

            val caloriesBurned = "${run.caloriesBurned}kcal"
            itemRunBinding.tvCalories.text = caloriesBurned
        }
    }

    val diffCallback = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Run>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        val itemBinding = ItemRunBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return RunViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]
        holder.bind(run)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}