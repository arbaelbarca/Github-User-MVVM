package com.arbaelbarca.github_user_project.repository

import com.arbaelbarca.github_user_project.datasource.network.ApiService
import com.arbaelbarca.github_user_project.db.dao.UsersDao
import com.arbaelbarca.github_user_project.db.entity.EntityUsers
import com.arbaelbarca.github_user_project.domain.response.ResponseDetailUsers
import com.arbaelbarca.github_user_project.domain.response.ResponseRepoList
import com.arbaelbarca.github_user_project.domain.response.ResponseSearchUsers

class RepositoryUserImpl(val apiService: ApiService, val usersDao: UsersDao) : IRepositoryUser {
    override suspend fun callApiSearchUser(
        textSearch: String,
        perPage: Int,
        page: Int
    ): ResponseSearchUsers {
        return apiService.callSearchUser(textSearch, page, perPage)
    }

    override suspend fun callApiDetailUser(userName: String): ResponseDetailUsers {
        return apiService.callDetailUser(userName)
    }

    override suspend fun callApiRepoList(userName: String): ResponseRepoList {
        return apiService.callDetailRepoUser(userName)
    }

    override suspend fun checkFavUser(title: String): List<EntityUsers> {
        return usersDao.checkFavTitleUser(title)
    }

    override suspend fun addFavUser(entityUsers: EntityUsers) {
        return usersDao.insert(entityUsers)
    }

    override suspend fun deleteFav(entityUsers: EntityUsers) {
        return usersDao.delete(entityUsers)
    }

    override suspend fun getAllFav(): List<EntityUsers> {
        return usersDao.findAllUsers()
    }
}