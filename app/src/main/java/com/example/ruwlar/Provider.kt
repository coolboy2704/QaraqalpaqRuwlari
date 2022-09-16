package com.example.ruwlar

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ruwlar.data.DataBase
import com.example.ruwlar.data.Keys
import com.example.ruwlar.data.Ruwlar
import com.example.ruwlar.data.RuwlarDao
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

object Provider {

    private lateinit var dataBase: DataBase
    private lateinit var ruwlarDao: RuwlarDao

    private val mutableNewList: MutableLiveData<List<Ruwlar>> = MutableLiveData()
    val newList: LiveData<List<Ruwlar>> get() = mutableNewList

    fun getNewRuwlar(context: Context, id: Int) {
        if (!::dataBase.isInitialized && !::ruwlarDao.isInitialized){
            dataBase = DataBase.getInstance(context)
            ruwlarDao = dataBase.ruwlarDao()
        }
        val newRuwlar = ruwlarDao.getNameRuwlar(id)
        newRuwlar
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
            {
                mutableNewList.value = it
            },
            {
                Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
                it.printStackTrace()
            }
        )
    }
}
