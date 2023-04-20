package com.example.capstonedesign.view.board

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.capstonedesign.R
import com.example.capstonedesign.databinding.FragmentPostDetailBinding
import com.example.capstonedesign.repository.BoardRepository
import com.example.capstonedesign.viewmodel.BoardViewModel
import com.example.capstonedesign.viewmodel.BoardViewModelFactory

class PostDetailFragment: Fragment() {
    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<PostDetailFragmentArgs>()
    var boardId: Long = 0
    private lateinit var viewModel: BoardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = BoardRepository()
        val factory = BoardViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[BoardViewModel::class.java]

        initDataSettings()
        setObserver()


        binding.btnPostDetailMenu.setOnClickListener {
            val popup = PopupMenu(requireContext(), it)
            popup.menuInflater.inflate(R.menu.post_detail_menu, popup.menu)

            popup.setOnMenuItemClickListener {
                viewModel.deletePost(boardId)
                true
            }
            popup.show() //showing popup menu
        }

        binding.btnPdWriteCommentsComplete.setOnClickListener {
            val commentsText = binding.etPdWriteComments.text.toString()
            if (commentsText.isNotEmpty()) {
                viewModel.writeComments(boardId, commentsText)
                clearFocusEditText()
            } else {
                Toast.makeText(requireContext(), "댓글 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setObserver() {
        viewModel.postDetailResponse.observe(viewLifecycleOwner) {
            if (it.code == 200) {
                binding.tvPdNickname.text = it.result.nickname
                binding.tvPdPostDate.text = it.result.modifiedDate
                binding.tvPdTitle.text = it.result.title
                binding.tvPdContents.text = it.result.content
                if (it.result.image == null) {
                    binding.frameLayout6.visibility = View.GONE
                    binding.ivPdCardview.visibility = View.GONE
                } else {
                    Glide.with(requireContext()).load(it.result.image).into(binding.ivPd)
                }
            }
        }
        viewModel.postDeleteResultCode.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }

    private fun initDataSettings() {
        boardId = args.boardId
        viewModel.getPostDetailInfo(boardId)

    }

    private fun clearFocusEditText() {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.etPdWriteComments.windowToken, 0)
        binding.etPdWriteComments.text = null
        binding.etPdWriteComments.clearFocus()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}