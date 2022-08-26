package com.example.ruwlar

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.addTextChangedListener
import com.example.ruwlar.data.DataBase
import com.example.ruwlar.data.Keys.PARENT_ID
import com.example.ruwlar.data.RuwlarDao
import com.example.ruwlar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var ruwlarDao: RuwlarDao
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var ruwlarDataBase: DataBase
    private var adapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("Ruwlar", MODE_PRIVATE)
        ruwlarDataBase = DataBase.getInstance(this)
        ruwlarDao = ruwlarDataBase.ruwlarDao()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FragmentRuwlar())
            .commit()

        binding.searchView.addTextChangedListener {
            val searchValue = it.toString()
            val newList = ruwlarDao.searchName("%$searchValue%")
            adapter.models = newList
        }
    }

    override fun onBackPressed() {
        val parentId = sharedPreferences.getInt(PARENT_ID, -1)

        if (parentId == 0) {
            finish()
        } else {
            val grandParentId = ruwlarDao.getParentId(parentId)
            Provider.getNewRuwlar(this, grandParentId)
        }
    }
}
