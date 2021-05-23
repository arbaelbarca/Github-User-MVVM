package com.arbaelbarca.github_user_project.repository

import com.arbaelbarca.github_user_project.db.entity.EntityUsers
import com.arbaelbarca.github_user_project.domain.response.ResponseDetailUsers
import com.arbaelbarca.github_user_project.domain.response.ResponseRepoList
import com.arbaelbarca.github_user_project.domain.response.ResponseSearchUsers

interface IRepositoryUser {
    suspend fun callApiSearchUser(
        textSearch: String,
        perPage: Int, page: Int
    ): ResponseSearchUsers

    suspend fun callApiDetailUser(
        userName: String
    ): ResponseDetailUsers

    suspend fun callApiRepoList(
        userName: String
    ): ResponseRepoList

    suspend fun checkFavUser(titleUser: String): List<EntityUsers>
    suspend fun addFavUser(entityUsers: EntityUsers)
    suspend fun deleteFav(entityUsers: EntityUsers)
    suspend fun getAllFav(): List<EntityUsers>
}

