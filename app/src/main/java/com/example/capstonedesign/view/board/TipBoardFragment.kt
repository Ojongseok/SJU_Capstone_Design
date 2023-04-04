package com.example.capstonedesign.view.board

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign.adapter.BoardPostAdapter
import com.example.capstonedesign.databinding.FragmentTipBoardBinding
import com.example.capstonedesign.model.PostTest
import com.example.capstonedesign.util.GridSpaceItemDecoration

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
//            addItemDecoration(GridSpaceItemDecoration(spanCount, space))
            addItemDecoration(GridSpaceItemDecoration(requireContext(), 2))
        }

        tipBoardPostAdapter.setItemClickListener(object : BoardPostAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val action = BoardFragmentDirections.actionFragmentBoardToFragmentPostDetail()
                findNavController().navigate(action)
            }
        })
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}