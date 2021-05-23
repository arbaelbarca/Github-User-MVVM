package com.arbaelbarca.github_user_project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arbaelbarca.github_user_project.R
import com.arbaelbarca.github_user_project.domain.response.ItemsItem
import com.arbaelbarca.github_user_project.presentation.onclick.OnClickItem
import com.arbaelbarca.github_user_project.utils.loadImageUrl
import kotlinx.android.synthetic.main.layout_item_search_user.view.*

class AdapterSearchUser(
    val context: Context,
    val itemsList: ArrayList<ItemsItem?>,
    val onClickItem: OnClickItem
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    val isLoading = false

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class ViewHolderLoading(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBarLoad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_search_user, parent, false)
            ViewHolder(
                view
            )
        } else {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_item_loading, parent, false)
            ViewHolderLoading(
                view
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemsList.size - 1) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val dataItems = itemsList[position]
            holder.apply {
                itemView.tvTitleUser.text = dataItems?.login
                itemView.circleImgUser.loadImageUrl(dataItems?.avatarUrl.toString(), context)

                itemView.setOnClickListener {
                    dataItems?.let { it1 -> onClickItem.clickItem(it1, position) }
                }
            }

        }

        if (holder is ViewHolderLoading) {
            holder.apply {
                progressBar.visibility = View.VISIBLE
            }
        }
    }

    fun showLoading(): Boolean {
        return isLoading
    }

    fun hideLoading(): Boolean {
        return isLoading
    }
}
