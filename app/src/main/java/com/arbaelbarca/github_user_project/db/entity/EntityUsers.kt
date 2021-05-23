package com.arbaelbarca.github_user_project.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_users")
class EntityUsers(
    @PrimaryKey
    var id: Int,

    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "titleUser")
    var titleUser: String,

    @ColumnInfo(name = "imageUser")
    var imageUser: String
)