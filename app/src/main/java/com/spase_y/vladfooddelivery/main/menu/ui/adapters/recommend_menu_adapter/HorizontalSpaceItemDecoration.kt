package com.spase_y.vladfooddelivery.main.menu.ui.adapters.recommend_menu_adapter

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
            // Устанавливаем отрицательный отступ для первого элемента
            outRect.left = -spaceWidth.toInt()
        } else {
            // Для остальных элементов добавляем стандартный отступ
            outRect.left = spaceWidth.toInt()
        }

        // Если нужно, можно задать отступы сверху, снизу или справа
        outRect.top = 0
        outRect.bottom = 0
        outRect.right = 0
    }
}