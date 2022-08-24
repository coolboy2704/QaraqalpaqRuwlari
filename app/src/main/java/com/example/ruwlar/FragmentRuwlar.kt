package com.example.ruwlar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.ruwlar.data.DataBase
import com.example.ruwlar.data.RuwlarDao
import com.example.ruwlar.databinding.FragmentBinding

class FragmentRuwlar: Fragment(R.layout.fragment) {

    private lateinit var binding: FragmentBinding
    private val adapter = Adapter()
    private lateinit var ruwlarDao: RuwlarDao


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBinding.bind(view)

        ruwlarDao = DataBase.getInstance(requireContext()).ruwlarDao()

        binding.apply {
            val ruwlar = ruwlarDao.getNameRuwlar(0)
            adapter.models = ruwlar

            adapter.setItemClickListener {
                adapter.models = ruwlarDao.getNameRuwlar(it)
            }
            rvFragment.adapter = adapter
        }

        binding.rvFragment.adapter = adapter
    }
}