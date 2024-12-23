package com.spase_y.vladfooddelivery.main.menu.ui.adapters.horizontal_spaceItem_decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalSpaceItemDecoration(private val spaceWidth: Float) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        if (position == 0) {
            outRect.left = -spaceWidth.toInt()
        } else {
            outRect.left = spaceWidth.toInt()
        }
        outRect.top = 0
        outRect.bottom = 0
        outRect.right = 0
    }
}