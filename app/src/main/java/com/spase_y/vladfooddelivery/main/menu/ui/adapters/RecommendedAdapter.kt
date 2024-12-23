package com.spase_y.vladfooddelivery.main.menu.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spase_y.vladfooddelivery.databinding.RecommendItemMenuBinding
import com.spase_y.vladfooddelivery.main.menu.data.model.Item

class RecommendedAdapter(
    private var items: List<Item>,
    private val listener: (Item) -> Unit
) : RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder>() {

    inner class RecommendedViewHolder(private val binding: RecommendItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.productName.text = item.itemName
            Glide.with(binding.imageView7.context).load(item.itemImage).into(binding.imageView7)

            binding.root.setOnClickListener {
                listener(item)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        val binding = RecommendItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }
}