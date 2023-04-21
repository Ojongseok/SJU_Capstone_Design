package com.example.capstonedesign.view.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.capstonedesign.adapter.BoardPostAdapter
import com.example.capstonedesign.databinding.FragmentTipBoardBinding
import com.example.capstonedesign.model.board.ContentList
import com.example.capstonedesign.repository.BoardRepository
import com.example.capstonedesign.util.GridSpaceItemDecoration
import com.example.capstonedesign.viewmodel.BoardViewModel
import com.example.capstonedesign.viewmodel.factory.BoardViewModelFactory

class TipBoardFragment: Fragment() {
    private var _binding: FragmentTipBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var tipBoardPostAdapter: BoardPostAdapter
    private lateinit var viewModel: BoardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTipBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = BoardRepository()
        val factory = BoardViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[BoardViewModel::class.java]

        initDataSettings()
        setObserver()
    }

    private fun setObserver() {
        viewModel.PostListResponse.observe(viewLifecycleOwner) {
            setRvPost(it.content)
            binding.pbTipBoard.visibility = View.GONE
        }
    }

    private fun initDataSettings() {
        viewModel.getAllPost("KNOWHOW")
    }

    private fun setRvPost(tipBoardList: List<ContentList>) {
        tipBoardPostAdapter = BoardPostAdapter(requireContext(), tipBoardList)

        binding.rvTipPost.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = tipBoardPostAdapter
            addItemDecoration(GridSpaceItemDecoration(requireContext(), 2))
        }

        tipBoardPostAdapter.setItemClickListener(object : BoardPostAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val action = BoardFragmentDirections.actionFragmentBoardToFragmentPostDetail(tipBoardList[position].boardId)
                findNavController().navigate(action)
            }
        })
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}