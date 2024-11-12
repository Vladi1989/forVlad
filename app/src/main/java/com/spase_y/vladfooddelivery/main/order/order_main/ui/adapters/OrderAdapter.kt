package com.spase_y.vladfooddelivery.main.order.order_main.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.main.menu.data.model.MenuItem

class OrderAdapter(private val orderList: List<MenuItem>)
    : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>(){

        inner class OrderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val productName: TextView = itemView.findViewById(R.id.textView7)
            val productImage: ImageView = itemView.findViewById(R.id.ivMyOrderImage)
            val productPrice: TextView = itemView.findViewById(R.id.tvPrice)


            fun bind(item: MenuItem) {
                productName.text = item.name
                productImage.setImageResource(item.imageRes)
                productPrice.text = "$${"%.2f".format(item.price)}"
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_current_order,parent,false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

}
