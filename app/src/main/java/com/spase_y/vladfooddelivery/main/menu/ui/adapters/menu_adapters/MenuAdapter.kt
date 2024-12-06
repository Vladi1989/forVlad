package com.spase_y.vladfooddelivery.main.menu.ui.adapters.menu_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spase_y.vladfooddelivery.main.menu.data.model.Item
import com.spase_y.vladfooddelivery.databinding.ItemMenuBinding

class MenuAdapter(
    private var menuItems: List<Item>,
    private val listener: OnItemClickListener,
    private val onAddClick: (Item)-> Unit
) :RecyclerView.Adapter<MenuAdapter.MenuAdapterViewHolder>(){

    interface OnItemClickListener {
        fun onItemClick(item: Item)
    }

    inner class MenuAdapterViewHolder(val binding: ItemMenuBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: Item){
            Glide.with(binding.imageView8.context)
                .load(item.item_image)
                .into(binding.imageView8) 

            binding.productName.text = item.item_name
            binding.productTitle.text = item.item_description
            binding.productPrice.text = "\$ ${item.item_price}"
            binding.btnAdd.setOnClickListener {
                onAddClick.invoke(item)
            }
            binding.root.setOnClickListener{
                listener.onItemClick(item)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapterViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuAdapterViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MenuAdapterViewHolder, position: Int) {
        val item = menuItems[position]
        holder.bind(item)

    }
    override fun getItemCount(): Int {
        return menuItems.size
    }
    fun updateItems(newItems: List<Item>) {
        menuItems = newItems
        notifyDataSetChanged()
    }
}
