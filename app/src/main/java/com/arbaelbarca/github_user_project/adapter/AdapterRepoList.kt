package com.arbaelbarca.github_user_project.adapter

import android.content.Context
import com.arbaelbarca.github_user_project.R
import com.arbaelbarca.github_user_project.domain.response.ResponseRepoList
import com.arbaelbarca.github_user_project.utils.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.layout_item_repo.view.*

class AdapterRepoList(
    val context: Context,
    val responseRepoListItem: ResponseRepoList.ResponseRepoListItem
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            itemView.tvTitleUserRepo.text = responseRepoListItem.name
            itemView.tvDescRepo.text = responseRepoListItem.description
            itemView.circleImgRepo.loadImageUrl(
                responseRepoListItem.owner?.avatarUrl.toString(),
                context
            )
        }
    }

    override fun getLayout(): Int = R.layout.layout_item_repo
}