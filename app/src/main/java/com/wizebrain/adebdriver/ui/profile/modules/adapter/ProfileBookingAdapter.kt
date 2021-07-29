package com.wizebrain.adebdriver.ui.profile.modules.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wizebrain.adebdriver.databinding.RvBookingsRowBinding


class ProfileBookingAdapter(
    val context: Context
) : androidx.recyclerview.widget.RecyclerView.Adapter<ProfileBookingAdapter.ProfileBookingViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileBookingViewHolder {
        val binding = RvBookingsRowBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProfileBookingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ProfileBookingViewHolder, position: Int) {

        holder.bind()
    }

    inner class ProfileBookingViewHolder(private val binding: RvBookingsRowBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {


        fun bind() {

        }

    }


}