package com.hazizah.simplepagination.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hazizah.simplepagination.R
import com.hazizah.simplepagination.domain.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(user: User?) {
        user?.let {
            itemView.txt_username.text = user.account
            Glide
                .with(itemView.context)
                .load(user.avatarUrl)
                .circleCrop()
                .placeholder(R.drawable.placeholder_avatar)
                .into(itemView.img_avatar)
        }
    }

    companion object {
        fun create(parent: ViewGroup): UserViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)
            return UserViewHolder(view)
        }
    }
}