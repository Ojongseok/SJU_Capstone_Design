package sju.sejong.capstonedesign.util

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridSpaceItemDecoration(
    private val context: Context,
    private val spanCount: Int,
    private val a: Int = 10,
    private val b: Int = 5
): RecyclerView.ItemDecoration() {
    private var size10 = dpToPx(context, a)
    private var size5 = dpToPx(context, b)

    // dp -> pixel 단위로 변경
    private fun dpToPx(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        //상하 설정
        if (position < spanCount) {
            // 첫번 째 줄 아이템
            outRect.top = size10
            outRect.bottom = size10
        } else {
            outRect.bottom = size10
        }

        // spanIndex = 0 -> 왼쪽
        // spanIndex = 1 -> 오른쪽
        val lp = view.layoutParams as GridLayoutManager.LayoutParams
        val spanIndex = lp.spanIndex

        // Grid 두 줄
        if (spanCount == 2) {
            if (spanIndex == 0) {
                //왼쪽 아이템
                outRect.left = size10
                outRect.right = size5
            } else if (spanIndex == 1){
                //오른쪽 아이템
                outRect.left = size5
                outRect.right = size10
            }
        } else if (spanCount == 3) {      // Grid 세 줄
            if (spanIndex == 0) {
                //왼쪽 아이템
                outRect.left = size10
                outRect.right = size5
            } else if (spanIndex == 1){
                //가운데 아이템
                outRect.left = size5
                outRect.right = size5
            } else if (spanIndex == 2){
                //오른쪽 아이템
                outRect.left = size5
                outRect.right = size10
            }
        } else if (spanCount == 4) {      // Grid 네 줄
            if (spanIndex == 0) {
                //왼쪽 아이템
                outRect.left = size10
                outRect.right = size5
            } else if (spanIndex == 1){
                //가운데 아이템
                outRect.left = size5
                outRect.right = size5
            } else if (spanIndex == 2){
                //가운데 아이템
                outRect.left = size5
                outRect.right = size5
            } else if (spanIndex == 3) {
                //오른쪽 아이템
                outRect.left = size5
                outRect.right = size10
            }
        }

    }
}