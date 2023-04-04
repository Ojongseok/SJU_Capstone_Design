package com.example.capstonedesign.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpaceItemDecoration(private val spanCount: Int, private val space: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount + 1      // 1부터 시작

        if (position < spanCount){
            outRect.top = space
        }

        if (column == spanCount){
            outRect.right = space
        }

        outRect.left = space
        outRect.bottom = space
    }
}