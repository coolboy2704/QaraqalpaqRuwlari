package com.example.ruwlar

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.addTextChangedListener
import com.example.ruwlar.data.DataBase
import com.example.ruwlar.data.Keys.PARENT_ID
import com.example.ruwlar.data.RuwlarDao
import com.example.ruwlar.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    private lateinit var ruwlarDao: RuwlarDao
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var ruwlarDataBase: DataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("abcde", Context.MODE_PRIVATE)
        ruwlarDataBase = DataBase.getInstance(this)
        ruwlarDao = ruwlarDataBase.ruwlarDao()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FragmentRuwlar())
            .commit()
    }

    override fun onBackPressed() {
        val parentId = sharedPreferences.getInt(PARENT_ID, -1)

        if (parentId == 0) {
            finish()
        } else {
            val grandParentId = ruwlarDao.getParentId(parentId)
            grandParentId
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                {
                    Provider.getNewRuwlar(this, it)
                },
                {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}
