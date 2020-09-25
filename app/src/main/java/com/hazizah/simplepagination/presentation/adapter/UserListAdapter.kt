package com.hazizah.simplepagination.presentation.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hazizah.simplepagination.domain.State
import com.hazizah.simplepagination.domain.User

class UserListAdapter(
    private val retry: () -> Unit
) : PagedListAdapter<User, RecyclerView.ViewHolder>(POST_COMPARATOR) {

    private var state = State.LOADING

    companion object {
        const val DATA_VIEW_TYPE = 1
        const val FOOTER_VIEW_TYPE = 2

        val POST_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun getChangePayload(oldItem: User, newItem: User): Any? {
                return null
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            DATA_VIEW_TYPE -> UserViewHolder.create(parent)
            else -> ListFooterViewHolder.create(retry, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as UserViewHolder).bind(getItem(position))
        else (holder as ListFooterViewHolder).bind(state)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount())
            DATA_VIEW_TYPE
        else
            FOOTER_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }
}