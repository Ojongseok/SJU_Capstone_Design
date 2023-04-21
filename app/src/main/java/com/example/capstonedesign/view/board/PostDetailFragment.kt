package com.example.capstonedesign.view.board

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.capstonedesign.R
import com.example.capstonedesign.adapter.CommentAdapter
import com.example.capstonedesign.databinding.FragmentPostDetailBinding
import com.example.capstonedesign.repository.BoardRepository
import com.example.capstonedesign.util.Constants.MEMBER_ID
import com.example.capstonedesign.viewmodel.BoardViewModel
import com.example.capstonedesign.viewmodel.factory.BoardViewModelFactory
import kotlinx.android.synthetic.main.dialog_comment_delete.*
import kotlinx.android.synthetic.main.dialog_post_delete.*
import kotlinx.android.synthetic.main.dialog_post_update.*

class PostDetailFragment: Fragment() {
    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BoardViewModel
    private val args by navArgs<PostDetailFragmentArgs>()
    private lateinit var commentAdapter: CommentAdapter
    private var boardId: Long = 0

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
        setCommentRecyclerView()


        binding.btnPostDetailMenu.setOnClickListener {
            val popup = PopupMenu(requireContext(), it)
            popup.menuInflater.inflate(R.menu.post_detail_menu, popup.menu)

            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.post_detail_menu_1 -> {
                        setUpdateDialog()
                    }
                    R.id.post_detail_menu_2 -> {
                        setDeleteDialog()
                    }
                }
                true
            }
            popup.show() //showing popup menu
        }

        binding.btnPdWriteCommentsComplete.setOnClickListener {
            val commentsText = binding.etPdWriteComments.text.toString()
            if (commentsText.isNotEmpty()) {
                viewModel.writeComments(boardId, commentsText)
            } else {
                Toast.makeText(requireContext(), "댓글 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnPostDetailLike.setOnClickListener {
            binding.btnPostDetailLike.setBackgroundColor(resources.getColor(R.color.grey_divide))
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setCommentRecyclerView() {
        binding.rvPdComments.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = commentAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager(requireContext()).orientation))
        }

        commentAdapter.setItemClickListener(object : CommentAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                setCommentDeleteDialog(viewModel.getAllCommentsResponse.value?.result!![position].commentId)
            }
        })
    }

    private fun setObserver() {
        viewModel.postDetailResponse.observe(viewLifecycleOwner) {
            if (it.code == 200) {
                if (viewModel.postDetailResponse.value?.result?.memberId == MEMBER_ID) {
                    binding.btnPostDetailMenu.visibility = View.VISIBLE
                }
                binding.tvPdNickname.text = it.result.nickname
                binding.tvPdPostDate.text = it.result.modifiedDate.removeRange(16,19)
                binding.tvPdTitle.text = it.result.title
                binding.tvPdContents.text = it.result.content
                if (it.result.image == null) {
                    binding.frameLayout6.visibility = View.GONE
                    binding.ivPdCardview.visibility = View.GONE
                } else {
                    Glide.with(requireContext()).load(it.result.image).into(binding.ivPd)
                }
                if (it.result.tag == "QUESTION") {
                    binding.tvPostDetailSolveTag.visibility = View.VISIBLE
                }
            }
        }

        viewModel.getAllCommentsResponse.observe(viewLifecycleOwner) {
            if (it.code == 200) {
                commentAdapter.setData(it.result)
                binding.textView11.text = "댓글 ${it.result.size}개"
            }
        }

        viewModel.writeCommentsResultCode.observe(viewLifecycleOwner) {
            if (it == 200) {
                val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.etPdWriteComments.windowToken, 0)
                binding.etPdWriteComments.text = null
                binding.etPdWriteComments.clearFocus()
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
        viewModel.getAllComments(boardId)

        commentAdapter = CommentAdapter(requireContext())

    }

    private fun setCommentDeleteDialog(commentId: Long) {
        val dialog = Dialog(requireContext())

        dialog.setContentView(R.layout.dialog_comment_delete)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        dialog.dialog_comment_delete_complete.setOnClickListener {
            viewModel.deleteComment(boardId, commentId)
            dialog.dismiss()
        }

        dialog.dialog_comment_delete_cancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun setUpdateDialog() {
        val dialog = Dialog(requireContext())

        dialog.setContentView(R.layout.dialog_post_update)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        dialog.dialog_post_update_complete.setOnClickListener {
            // 수정 뷰모텔 코드 추가
            dialog.dismiss()
        }

        dialog.dialog_post_update_cancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun setDeleteDialog() {
        val dialog = Dialog(requireContext())

        dialog.setContentView(R.layout.dialog_post_delete)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        dialog.dialog_post_delete_complete.setOnClickListener {
            viewModel.deletePost(boardId)
            dialog.dismiss()
        }

        dialog.dialog_post_delete_cancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}