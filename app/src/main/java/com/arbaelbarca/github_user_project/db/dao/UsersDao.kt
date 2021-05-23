package com.arbaelbarca.github_user_project.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.arbaelbarca.github_user_project.db.entity.EntityUsers

@Dao
interface UsersDao : BaseDao<EntityUsers> {
    @Query("SELECT * FROM tbl_users")
    suspend fun findAllUsers(): List<EntityUsers>

    @Query("SELECT * FROM tbl_users WHERE titleUser LIKE :titleUsers")
    suspend fun checkFavTitleUser(titleUsers: String): List<EntityUsers>

//    @Query("SELECT EXISTS (SELECT 1 FROM tbl_users WHERE id=:id)")
//    suspend fun checkFavUsers(id: Int): Int


    @Query("DELETE From tbl_users")
    suspend fun deleteAllUsers()
}