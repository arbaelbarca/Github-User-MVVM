package com.arbaelbarca.github_user_project.adapter

import android.content.Context
import com.arbaelbarca.github_user_project.R
import com.arbaelbarca.github_user_project.db.entity.EntityUsers
import com.arbaelbarca.github_user_project.presentation.onclick.OnClickItem
import com.arbaelbarca.github_user_project.utils.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.layout_item_search_user.view.*

class AdapterUsersFavorite(
    val context: Context,
    val entityUsers: EntityUsers,
    val onClickItem: OnClickItem
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            itemView.tvTitleUser.text = entityUsers.titleUser
            itemView.circleImgUser.loadImageUrl(entityUsers.imageUser, context)

            itemView.setOnClickListener {
                onClickItem.clickItem(entityUsers, position)
            }
        }
    }

    override fun getLayout(): Int = R.layout.layout_item_search_user
}