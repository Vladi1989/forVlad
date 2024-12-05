package com.spase_y.vladfooddelivery.main.menu.ui.adapters.menu_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spase_y.vladfooddelivery.Item
import com.spase_y.vladfooddelivery.databinding.ItemMenuBinding
import com.spase_y.vladfooddelivery.main.menu.data.model.MenuItem

class MenuAdapter(
    private val menuItems: List<Item>,
    private val listener: OnItemClickListener,
    private val onAddClick: (Item)-> Unit
) :RecyclerView.Adapter<MenuAdapter.MenuAdapterViewHolder>(){

    interface OnItemClickListener {
        fun onItemClick(item:Item)
    }

    inner class MenuAdapterViewHolder(val binding: ItemMenuBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:Item){
            binding.imageView8.setImageResource(item.item_image)
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
}
