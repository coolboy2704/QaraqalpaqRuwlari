package com.example.ruwlar.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rwlar")
data class Ruwlar(
     @PrimaryKey val id: Int,

     @ColumnInfo(name="name")
     val name: String,

     @ColumnInfo(name="has_children")
     val has_children: Int,

     @ColumnInfo(name="parent_id")
     val parent_id: Int
)
