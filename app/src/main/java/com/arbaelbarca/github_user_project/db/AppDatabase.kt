package com.arbaelbarca.github_user_project.db

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arbaelbarca.github_user_project.db.dao.UsersDao
import com.arbaelbarca.github_user_project.db.entity.EntityUsers

@Database(
    entities = [EntityUsers::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UsersDao

    companion object {
        @Volatile
        var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "fav_users.db"
                    ).build()
                }
            }

            return INSTANCE as AppDatabase
        }
    }
}