package com.spase_y.vladfooddelivery.main.menu.ui.adapters.menu_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spase_y.vladfooddelivery.databinding.ItemMenuBinding
import com.spase_y.vladfooddelivery.main.menu.data.model.MenuItem

class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val listener: OnItemClickListener,
    private val onAddClick: (MenuItem)-> Unit
) :RecyclerView.Adapter<MenuAdapter.MenuAdapterViewHolder>(){

    interface OnItemClickListener {
        fun onItemClick(item:MenuItem)
    }

    inner class MenuAdapterViewHolder(val binding: ItemMenuBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:MenuItem){
            binding.imageView8.setImageResource(item.imageRes)
            binding.productName.text = item.name
            binding.productTitle.text = item.description
            binding.productPrice.text = "\$ ${item.price}"
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
