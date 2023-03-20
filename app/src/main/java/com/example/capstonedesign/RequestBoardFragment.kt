package com.example.capstonedesign

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

class RequestBoardFragment: Fragment() {
    private var _binding: FragmentRequestBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestBoardPostAdapter: BoardPostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRequestBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRvPost()

    }

    private fun setRvPost() {
        val list = mutableListOf<PostTest>()
        list.add(PostTest("null", "안녕", "하이"))
        list.add(PostTest("null", "안녕", "하이"))
        list.add(PostTest("null", "안녕", "하이"))
        list.add(PostTest("null", "안녕", "하이"))
        list.add(PostTest("null", "안녕", "하이"))
        list.add(PostTest("null", "안녕", "하이"))
        list.add(PostTest("null", "안녕", "하이"))
        list.add(PostTest("null", "안녕", "하이"))
        list.add(PostTest("null", "안녕", "하이"))

        requestBoardPostAdapter = BoardPostAdapter(requireContext(), list)

        binding.rvRequestPost.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = requestBoardPostAdapter
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
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
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