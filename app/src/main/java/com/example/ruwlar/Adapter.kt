package com.example.ruwlar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.ruwlar.data.Ruwlar
import com.example.ruwlar.databinding.ItemBinding

class Adapter: RecyclerView.Adapter<Adapter.ViewHolder>() {

    var models: List<Ruwlar> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(ruwlar: Ruwlar){
            binding.apply {
                tvItem.text = ruwlar.name
                ivNext.isVisible = ruwlar.has_children == 1
                binding.root.isEnabled = ruwlar.has_children == 1

                cardView.setOnClickListener {
                    itemClick.invoke(ruwlar)
                }
            }
        }
    }

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(models[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        val binding = ItemBinding.bind(view)
        return ViewHolder(binding)
    }

    private var itemClick: (ruw: Ruwlar) -> Unit = {}
    fun setItemClickListener(itemClickListener: (ruw: Ruwlar) -> Unit) {
        this.itemClick = itemClickListener
    }
}