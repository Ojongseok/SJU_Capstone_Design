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
import com.example.capstonedesign.model.PostTest
import com.example.capstonedesign.databinding.FragmentRequestBoardBinding
import com.example.capstonedesign.util.GridSpaceItemDecoration

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
        list.add(PostTest("null", "캡스톤디자인입니다.", "컴퓨터공학과 1분반 캡스톤디자인입니다."))
        list.add(PostTest("null", "파릇파릇 팀입니다.", "안녕하세요."))
        list.add(PostTest("null", "이럴 때는 어떤 농약을 사용하나요?", "알려주세요~"))
        list.add(PostTest("null", "이번에 귀농한 xx살입니다.", "잘 부탁드립니다."))
        list.add(PostTest("null", "제목", "내용"))
        list.add(PostTest("null", "제가 기르고 있는 식물인데 이름이 뭔가요?", "이름을 모르겠습니다..ㅠㅠ"))
        list.add(PostTest("null", "안녕", "하이"))

        requestBoardPostAdapter = BoardPostAdapter(requireContext(), list)

        binding.rvRequestPost.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = requestBoardPostAdapter
            // spanCount -> grid 수, space->여백
            val spanCount = 2
            val space = 36
//            addItemDecoration(GridSpaceItemDecoration(spanCount, space))
            addItemDecoration(GridSpaceItemDecoration(requireContext(), 2))
        }

        requestBoardPostAdapter.setItemClickListener(object : BoardPostAdapter.OnItemClickListener {
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