package com.wizebrain.adebdriver.ui.profile.modules.adapter

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import com.wizebrain.adebdriver.databinding.RvProgressBarBinding


class ProgressAdapter(

    val context: Context,
   var monthArray: Array<String>

) : androidx.recyclerview.widget.RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProgressViewHolder {
        val binding = RvProgressBarBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProgressViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return monthArray.size
    }

    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) {

        holder.bind(monthArray[position])
    }

    inner class ProgressViewHolder(private val binding: RvProgressBarBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {


        fun bind(month: String) {
            val percentage: String = "50"
            val d = percentage.toFloat()
            val c = Math.round(d)
            binding.tvPercentage.text=month
            val progressAnimator = ObjectAnimator.ofInt(binding.progressBar, "progress", 0, c)
            progressAnimator.duration = 700
            progressAnimator.interpolator = LinearInterpolator()
            progressAnimator.start()

        }

    }


}