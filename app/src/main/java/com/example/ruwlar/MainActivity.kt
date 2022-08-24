package com.example.ruwlar

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.addTextChangedListener
import com.example.ruwlar.data.DataBase
import com.example.ruwlar.data.Keys.PARENT_ID
import com.example.ruwlar.data.RuwlarDao
import com.example.ruwlar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var ruwlarDao: RuwlarDao
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var ruwlarDataBase: DataBase
    private var adapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, FragmentRuwlar()).commit()

        sharedPreferences = getSharedPreferences("Rwlar", MODE_PRIVATE)
        ruwlarDataBase = DataBase.getInstance(this)
        ruwlarDao = ruwlarDataBase.ruwlarDao()

        getNewRuwlar(0)

        adapter.setItemClickListener {
            getNewRuwlar(it)
        }

        binding.searchView.addTextChangedListener {
            it?.let { editable ->
                val searchValue = editable.toString()
                val newList = ruwlarDao.searchName("%$searchValue%")
                adapter.models = newList
            }
        }
    }

    override fun onBackPressed() {
        val parentId = sharedPreferences.getInt(PARENT_ID, -1)

        if (parentId == 0){
            finish()
        } else {
            val grandParentId = ruwlarDao.getParentId(parentId)
            getNewRuwlar(grandParentId)
        }
    }

    fun getNewRuwlar(id: Int){
        val newRuwlar = ruwlarDao.getNameRuwlar(id)
        adapter.models = newRuwlar

        newRuwlar.firstOrNull()?.let {
            sharedPreferences.edit().putInt(PARENT_ID, it.parent_id).apply()
        }
    }
}
