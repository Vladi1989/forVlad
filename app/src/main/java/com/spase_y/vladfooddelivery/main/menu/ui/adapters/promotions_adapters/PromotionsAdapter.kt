package com.spase_y.vladfooddelivery.main.menu.ui.adapters.promotions_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spase_y.vladfooddelivery.databinding.ImageSliderContainerBinding
import com.spase_y.vladfooddelivery.main.menu.data.model.PromotionItem

class PromotionsAdapter(
): RecyclerView.Adapter<PromotionsAdapter.ImageViewHolder>() {
    class ImageViewHolder(val binding: ImageSliderContainerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(promotionItem: PromotionItem) {
            Glide.with(itemView.context)
                .load(promotionItem.item_image)
                .into(binding.ivImageInImage)

            binding.tvHeader.text = promotionItem.item_header
            binding.tvText.text = promotionItem.item_description

            if (promotionItem.button_text.isNotEmpty()) {
                binding.btnOrder.text = promotionItem.button_text
                binding.btnOrder.visibility = View.VISIBLE
            } else {
                binding.btnOrder.visibility = View.GONE
            }
        }
    }
    var promotionList: List<PromotionItem> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        val binding = ImageSliderContainerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(promotionList[position])
    }

    override fun getItemCount(): Int {
        return promotionList.size
    }

}

