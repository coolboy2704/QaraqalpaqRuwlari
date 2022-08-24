package com.example.ruwlar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Ruwlar::class], version = 2)
abstract class DataBase: RoomDatabase() {
    companion object{
        fun getInstance(context: Context): DataBase {
            return Room.databaseBuilder(
                context,
                DataBase::class.java,
                "Rwlar.db"
            )
                .createFromAsset("Rwlar.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
    }
    abstract fun ruwlarDao(): RuwlarDao
}