package com.example.ruwlar

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ruwlar.data.DataBase
import com.example.ruwlar.data.Keys
import com.example.ruwlar.data.Ruwlar
import com.example.ruwlar.data.RuwlarDao

object Provider {

    private lateinit var dataBase: DataBase
    private lateinit var ruwlarDao: RuwlarDao

    private val mutableNewList: MutableLiveData<List<Ruwlar>> = MutableLiveData()
    val newList: LiveData<List<Ruwlar>> = mutableNewList

    fun getNewRuwlar(context: Context, id: Int) {
        if (!::dataBase.isInitialized && !::ruwlarDao.isInitialized){
            dataBase = DataBase.getInstance(context)
            ruwlarDao = dataBase.ruwlarDao()
        }
        val newRuwlar = ruwlarDao.getNameRuwlar(id)
        mutableNewList.value = newRuwlar
    }
}
