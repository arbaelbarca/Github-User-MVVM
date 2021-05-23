package com.arbaelbarca.github_user_project.datasource.network

import com.arbaelbarca.github_user_project.domain.response.ResponseDetailUsers
import com.arbaelbarca.github_user_project.domain.response.ResponseRepoList
import com.arbaelbarca.github_user_project.domain.response.ResponseSearchUsers
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/search/users")
    suspend fun callSearchUser(
        @Query("q") textSearch: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): ResponseSearchUsers

    @GET("/users/{username}")
    suspend fun callDetailUser(
        @Path("username") username: String
    ): ResponseDetailUsers

    @GET("/users/{username}/repos")
    suspend fun callDetailRepoUser(
        @Path("username") username: String
    ): ResponseRepoList
}