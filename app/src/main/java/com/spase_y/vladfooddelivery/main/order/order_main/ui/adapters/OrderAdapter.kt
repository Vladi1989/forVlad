package com.spase_y.vladfooddelivery.main.order.order_main.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.main.menu.data.model.Item

class OrderAdapter(
    private val onQuantityChanged: (Item,Item) -> Unit,
    private val onClickDeleteItem: (Item) -> Unit)
    : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>(){

        inner class OrderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val productName: TextView = itemView.findViewById(R.id.textView7)
            val productImage: ImageView = itemView.findViewById(R.id.ivMyOrderImage)
            val productPrice: TextView = itemView.findViewById(R.id.tvPrice)
            val quantityText: TextView = itemView.findViewById(R.id.textView5)
            val btnAddFood: ImageView = itemView.findViewById(R.id.ivAddFood)
            val btnMinusFood: ImageView= itemView.findViewById(R.id.ivMinusFood)
            val deleteItem: ImageView= itemView.findViewById(R.id.ivDeleteItem)

            fun bind(item: Item) {
                productName.text = item.itemName
                Glide.with(itemView).load(item.itemImage).into(productImage)
                productPrice.text = "$${"%.2f".format(item.itemPrice * item.quantity)}"
                quantityText.text = item.quantity.toString()
                btnAddFood.setOnClickListener {
                    onQuantityChanged(item,item.copy(quantity = item.quantity + 1))
                }
                deleteItem.setOnClickListener {
                    onClickDeleteItem.invoke(item)
                }
                btnMinusFood.setOnClickListener {
                    if (item.quantity > 1){
                        onQuantityChanged(item,item.copy(quantity = item.quantity - 1))

                    }
                }
            }
        }
    var orderList: MutableList<Item> = mutableListOf()

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
