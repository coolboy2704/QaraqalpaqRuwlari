package com.example.ruwlar

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.ruwlar.data.DataBase
import com.example.ruwlar.data.Keys
import com.example.ruwlar.data.RuwlarDao
import com.example.ruwlar.databinding.FragmentBinding

class FragmentRuwlar: Fragment(R.layout.fragment) {

    private lateinit var binding: FragmentBinding
    private val adapter by lazy { Adapter() }
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var ruwlarDao: RuwlarDao


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBinding.bind(view)

        ruwlarDao = DataBase.getInstance(requireContext()).ruwlarDao()

        sharedPreferences = requireActivity().getSharedPreferences("Ruwlar", Context.MODE_PRIVATE)

        binding.rvFragment.adapter = adapter

        Provider.getNewRuwlar(requireContext(), 0)

        adapter.setItemClickListener {
            Provider.getNewRuwlar(requireContext(), it.id)
        }

        Provider.newList.observe(viewLifecycleOwner){
            adapter.models = it

            it.firstOrNull()?.let {
                sharedPreferences.edit().putInt(Keys.PARENT_ID, it.parent_id).apply()
            }
        }

        binding.apply {
            val ruwlar = ruwlarDao.getNameRuwlar(0)
            adapter.models = ruwlar

            adapter.setItemClickListener {
                adapter.models = ruwlarDao.getNameRuwlar(it.id)
            }
            rvFragment.adapter = adapter
        }
    }
}
