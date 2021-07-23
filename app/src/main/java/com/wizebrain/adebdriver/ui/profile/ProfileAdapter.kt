package com.wizebrain.adebdriver.ui.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wizebrain.adebdriver.databinding.RvProfileRowBinding


class ProfileAdapter(
    var context: Context,
    var profileActivityListener:ProfileViewListener
) : androidx.recyclerview.widget.RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {


  //  bloodGroupList = resources.getStringArray(R.array.blood_group)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileViewHolder {
        val binding = RvProfileRowBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProfileViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind()
    }

    inner class ProfileViewHolder(private val binding: RvProfileRowBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun bind() {

            binding.rlParent.setOnClickListener {
                profileActivityListener.profileActivityCallback(adapterPosition)
            }
        }

    }



    interface ProfileViewListener

    {
        fun profileActivityCallback(position:Int)
    }



}