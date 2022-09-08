package com.example.ruwlar.data

import android.database.Observable
import androidx.room.Dao
import androidx.room.Query

@Dao
interface RuwlarDao {
    @Query("SELECT * FROM rwlar WHERE parent_id = :id")
    fun getNameRuwlar(id: Int): Observable<List<Ruwlar>>

    @Query("SELECT * FROM rwlar WHERE name LIKE :searchValue")
    fun searchName(searchValue: String): Observable<List<Ruwlar>>

    @Query("SELECT parent_id FROM rwlar WHERE id = :id")
    fun getParentId(id: Int): Int
}
