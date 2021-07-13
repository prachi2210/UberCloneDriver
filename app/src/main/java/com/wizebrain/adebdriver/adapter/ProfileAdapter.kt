package com.wizebrain.adebdriver.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wizebrain.adebdriver.R

class ProfileAdapter(var context: Context,val onVClick: onViewClick) : RecyclerView.Adapter<ProfileAdapter.AlertHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.AlertHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.profile_row, parent, false)
        return AlertHolder(v)
    }

    override fun onBindViewHolder(holder: ProfileAdapter.AlertHolder, position: Int) {
      holder.onBind(position)
    }
    override fun getItemCount(): Int {
        return 2
    }

    class AlertHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind( position: Int){

        }

    }
    interface onViewClick{
        fun onClick()
    }
}