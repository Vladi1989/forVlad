package com.spase_y.vladfooddelivery.main.add_card.add_card.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spase_y.vladfooddelivery.databinding.ItemAddCardBinding
import com.spase_y.vladfooddelivery.main.add_card.add_card.data.model.Card

class CardAdapter : ListAdapter<Card, CardAdapter.CardViewHolder>(CardDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemAddCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CardViewHolder(private val binding: ItemAddCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card) {
            binding.tvPasswordHide.text = "**** **** **** ${card.cardNumber.takeLast(4)}"
            binding.tvCardHolderName.text = card.cardHolderName
            binding.tvExpiryDate.text = card.expiryDate
        }
    }
}

class CardDiffCallback : DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.cardNumber == newItem.cardNumber
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem == newItem
    }
}