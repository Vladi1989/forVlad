package com.spase_y.vladfooddelivery.main.menu.ui.adapters.recommend_menu_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spase_y.vladfooddelivery.core.toPx
import com.spase_y.vladfooddelivery.databinding.RecommendItemMenuBinding
import com.spase_y.vladfooddelivery.main.menu.data.model.MenuItem
import com.spase_y.vladfooddelivery.main.menu.ui.adapters.menu_adapters.MenuAdapter.OnItemClickListener

class RecommendMenuAdapter(
    private val items: List<MenuItem>,
    private val listener: OnItemClickListener,
    private val onAddClick: (MenuItem)-> Unit
) : RecyclerView.Adapter<RecommendMenuAdapter.MenuViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item:MenuItem)
    }

    inner class MenuViewHolder(val binding: RecommendItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MenuItem) {
            binding.imageView7.setImageResource(item.imageRes)
            binding.productName.text = item.name
            binding.productTitle.text = item.description
            binding.productPrice.text = "$ ${item.price}"
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