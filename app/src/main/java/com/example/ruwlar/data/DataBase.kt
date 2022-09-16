package com.example.ruwlar.data

import android.content.Context
import android.provider.CalendarContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Ruwlar::class], version = 3)
abstract class DataBase: RoomDatabase() {
    companion object{
        private var INSTANCE: DataBase? = null
        private var LOCK = Any()

        fun getInstance(context: Context): DataBase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK){
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    context,
                    DataBase::class.java,
                    "Rwlar.db"
                )
                    .createFromAsset("Rwlar.db")
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
    abstract fun ruwlarDao(): RuwlarDao
}
