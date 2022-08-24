package com.example.ruwlar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.ruwlar.data.Ruwlar
import com.example.ruwlar.databinding.ItemBinding

class Adapter: RecyclerView.Adapter<Adapter.ViewHolder>() {

    var models = listOf<Ruwlar>()
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

                cardView.setOnClickListener {
                    itemClick.invoke(ruwlar.id)
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

    private var itemClick: (id: Int) -> Unit = {}
    fun setItemClickListener(itemClickListener: (id: Int) -> Unit) {
        this.itemClick = itemClickListener
    }
}