package com.wizebrain.adebdriver.ui.profile

import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.databinding.RvProfileRowBinding
import com.wizebrain.adebdriver.extensions.loadImage


class ProfileAdapter(
    var context: Context,
    var profileActivityListener: ProfileViewListener,
    var modulesArray: Array<String>,
    var drawableIcons: TypedArray
) : androidx.recyclerview.widget.RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {


    var selected = 0


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileViewHolder {
        val binding = RvProfileRowBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProfileViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return modulesArray.size
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.ivModule.setImageDrawable(drawableIcons.getDrawable(position))
        holder.tvModule.text = modulesArray[position].toString()
        holder.bind()

        if (selected == position) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.tvModule.setTextColor(
                    context.resources.getColor(android.R.color.white, context.getTheme())
                );
            } else {
                holder.tvModule.setTextColor(
                    context.resources.getColor(android.R.color.white)
                );
            }


            holder.ivModule.setColorFilter(ContextCompat.getColor(context, R.color.white));

            when (selected) {
                0 -> {
                    holder.lvParent.setBackgroundResource(R.drawable.ic_booking_bg)
                }
                1 -> {
                    holder.lvParent.setBackgroundResource(R.drawable.ic_booking_bg)
                }
                2 -> {
                    holder.lvParent.setBackgroundResource(R.drawable.ic_document_bg)
                }
                3 -> {
                    holder.lvParent.setBackgroundResource(R.drawable.ic_payment_bg)
                }
                4 -> {
                    holder.lvParent.setBackgroundResource(R.drawable.ic_setting_bg)
                }
                5 -> {
                    holder.lvParent.setBackgroundResource(R.drawable.ic_support_bg)
                }
            }
        } else {
            holder.lvParent.setBackgroundResource(R.drawable.white_background_profile)
            holder.ivModule.setColorFilter(ContextCompat.getColor(context, R.color.black));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.tvModule.setTextColor(
                    context.resources.getColor(android.R.color.black, context.getTheme())
                );
            } else {
                holder.tvModule.setTextColor(
                    context.resources.getColor(android.R.color.black)
                );
            }

        }

    }

    inner class ProfileViewHolder(private val binding: RvProfileRowBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        var ivModule = binding.ivModule
        var tvModule = binding.tvModule
        var lvParent = binding.lvParent
        fun bind() {

            binding.lvParent.setOnClickListener {
                profileActivityListener.profileActivityCallback(adapterPosition)
                selected = adapterPosition
                notifyDataSetChanged()
            }
        }

    }


    interface ProfileViewListener {
        fun profileActivityCallback(position: Int)
    }


}