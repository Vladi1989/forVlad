package com.spase_y.vladfooddelivery.main.menu.ui.adapters.recommend_menu_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spase_y.vladfooddelivery.core.toPx
import com.spase_y.vladfooddelivery.databinding.RecommendItemMenuBinding
import com.spase_y.vladfooddelivery.main.menu.data.model.Item
import com.spase_y.vladfooddelivery.main.menu.ui.adapters.menu_adapters.MenuAdapter.OnItemClickListener

class RecommendMenuAdapter(
    private val items: List<Item>,
    private val listener: OnItemClickListener,
    private val onAddClick: (Item)-> Unit
) : RecyclerView.Adapter<RecommendMenuAdapter.MenuViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item:Item)
    }

    inner class MenuViewHolder(val binding: RecommendItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            Glide.with(binding.root).load(item.item_image).into(binding.imageView7)
            binding.productName.text = item.item_name
            binding.productTitle.text = item.item_description
            binding.productPrice.text = "$ ${item.item_price}"
            binding.btnAddRecommend.setOnClickListener {
                onAddClick.invoke(item)
            }
            binding.root.setOnClickListener{
                listener.onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = RecommendItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        if(position == 0){
            val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
            layoutParams.leftMargin = 28.toPx(holder.itemView.context).toInt()

            holder.itemView.layoutParams = layoutParams
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}