package com.wizebrain.adebdriver.ui.map

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wizebrain.adebdriver.databinding.RvUserRideRequestBinding


public class UserRideRequestAdapter(
    val context: Context,
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<UserRideRequestAdapter.RequestRideViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RequestRideViewHolder {
        val binding = RvUserRideRequestBinding.inflate(LayoutInflater.from(context), parent, false)
        return RequestRideViewHolder(binding)


    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: RequestRideViewHolder, position: Int) {
        holder.bind()

    }

    inner class RequestRideViewHolder(private val binding: RvUserRideRequestBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun bind() {


        }

    }
}