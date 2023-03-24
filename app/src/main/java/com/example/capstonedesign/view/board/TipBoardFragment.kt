package com.example.capstonedesign.view.board

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign.databinding.FragmentMainBinding
import com.example.capstonedesign.databinding.FragmentRequestBoardBinding
import com.example.capstonedesign.databinding.FragmentTipBoardBinding
import com.example.capstonedesign.model.PostTest

class TipBoardFragment: Fragment() {
    private var _binding: FragmentTipBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var tipBoardPostAdapter: BoardPostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTipBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRvPost()
    }

    private fun setRvPost() {
        val list = mutableListOf<PostTest>()
        list.add(PostTest("null", "팁 게시판 1.", "컴퓨터공학과 1분반 캡스톤디자인입니다."))
        list.add(PostTest("null", "팁 게시판 2", "팁팁팁팁팁"))
        list.add(PostTest("null", "팁 게시판 3", "tiptiptip"))
        list.add(PostTest("null", "제가 기르고 있는 식물인데 이름이 뭔가요?", "이름을 모르겠습니다..ㅠㅠ"))
        list.add(PostTest("null", "안녕", "하이"))

        tipBoardPostAdapter = BoardPostAdapter(requireContext(), list)

        binding.rvTipPost.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = tipBoardPostAdapter
            // spanCount -> grid 수, space->여백
            val spanCount = 2
            val space = 36
            addItemDecoration(GridSpaceItemDecoration(spanCount, space))
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    inner class GridSpaceItemDecoration(private val spanCount: Int, private val space: Int): RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount + 1      // 1부터 시작

            /** 첫번째 행(row-1)에 있는 아이템인 경우 상단에 [space] 만큼의 여백을 추가한다 */
            if (position < spanCount){
                outRect.top = space
            }
            /** 마지막 열(column-N)에 있는 아이템인 경우 우측에 [space] 만큼의 여백을 추가한다 */
            if (column == spanCount){
                outRect.right = space
            }
            /** 모든 아이템의 좌측과 하단에 [space] 만큼의 여백을 추가한다. */
            outRect.left = space
            outRect.bottom = space
        }
    }
}