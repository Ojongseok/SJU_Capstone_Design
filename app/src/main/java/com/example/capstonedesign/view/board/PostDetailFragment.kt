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
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
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
import com.example.capstonedesign.model.board.SolveRequest
import com.example.capstonedesign.repository.BoardRepository
import com.example.capstonedesign.util.Constants.MEMBER_ID
import com.example.capstonedesign.viewmodel.BoardViewModel
import com.example.capstonedesign.viewmodel.factory.BoardViewModelFactory
import kotlinx.android.synthetic.main.dialog_comment_delete.*
import kotlinx.android.synthetic.main.dialog_comment_modify.*
import kotlinx.android.synthetic.main.dialog_post_delete.*
import kotlinx.android.synthetic.main.dialog_post_update.*
import kotlinx.android.synthetic.main.dialog_request_post_solve.*
import kotlinx.android.synthetic.main.item_comment.*


class PostDetailFragment: Fragment() {
    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BoardViewModel
    private val args by navArgs<PostDetailFragmentArgs>()
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        binding.btnPostDetailSolve.setOnClickListener {
            setRequestSolve()
        }

        binding.btnPdWriteCommentsComplete.setOnClickListener {
            val commentsText = binding.etPdWriteComments.text.toString()
            if (commentsText.isNotEmpty()) {
                viewModel.writeComments(args.boardId, commentsText)
            } else {
                Toast.makeText(requireContext(), "댓글을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnPostDetailLike.setOnClickListener {
            viewModel.postLike(args.boardId)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.contlayout5.setOnClickListener {
            if (activity != null && requireActivity().currentFocus != null) {
                val inputManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
    }

    private fun setCommentRecyclerView() {
        binding.rvPdComments.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = commentAdapter
        }

        commentAdapter.setItemClickListener(object : CommentAdapter.OnItemClickListener {
            override fun onClickDelete(v: View, position: Int) {
                setCommentDeleteDialog(viewModel.getAllCommentsResponse.value?.result!![position].commentId)
            }
            override fun onClickModify(v: View, position: Int) {
                setCommentModifyDialog(
                    viewModel.getAllCommentsResponse.value?.result!![position].commentId,
                    viewModel.getAllCommentsResponse.value?.result!![position].content
                )
            }

            override fun onClickCommentRe(v: View, position: Int) {
                binding.etPdWriteComments.post {
                    binding.etPdWriteComments.hint = "답글을 입력하세요."
                    binding.etPdWriteComments.isFocusableInTouchMode = true
                    binding.etPdWriteComments.requestFocus()
                    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(binding.etPdWriteComments, 0)

                    binding.btnPdWriteCommentsComplete.setOnClickListener {
                        val parentId =  viewModel.getAllCommentsResponse.value?.result!![position].commentId
                        val reCommentText = binding.etPdWriteComments.text.toString()
                        if (reCommentText.isNotEmpty()) {
                            viewModel.writeComments(args.boardId, reCommentText, parentId)
                        } else {
                            Toast.makeText(requireContext(), "답글을 입력해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
        })
    }

    private fun setObserver() {
        viewModel.postDetailResponse.observe(viewLifecycleOwner) {
            if (it.code == 200) {
                if (viewModel.postDetailResponse.value?.result?.memberId == MEMBER_ID) {
                    binding.btnPostDetailMenu.visibility = View.VISIBLE
                    binding.btnPostDetailSolve.visibility = View.VISIBLE
                }
                binding.tvPdNickname.text = it.result.nickname
                binding.tvPdPostDate.text = it.result.createdDate.removeRange(16,19)
                binding.tvPdTitle.text = it.result.title
                binding.tvPdContents.text = it.result.content
                binding.tvPostDetailLikeCnt.text = "추천 ${it.result.likeNum}개"

                if (it.result.image == null) {
                    binding.frameLayout6.visibility = View.GONE
                    binding.ivPdCardview.visibility = View.GONE
                } else {
                    Glide.with(requireContext()).load(it.result.image).into(binding.ivPd)
                }

                if (it.result.tag == "QUESTION") {
                    binding.tvPostDetailSolveTag.visibility = View.VISIBLE
                    if (it.result.isSolved) {
                        binding.tvPostDetailSolveTag.text = "해결완료"
                        binding.tvPostDetailSolveTag.setBackgroundResource(R.drawable.background_rec_8dp_green)
                    }
                }

                if (it.result.likeMemberIds.contains(MEMBER_ID)) {
                    binding.btnPostDetailLike.setBackgroundColor(resources.getColor(R.color.main_green))
                } else {
                    binding.btnPostDetailLike.setBackgroundColor(resources.getColor(R.color.sub_text))
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



    private fun setCommentModifyDialog(commentId: Long, contents: String) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_comment_modify)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        dialog.et_comment_modify.setText(contents)

        dialog.btn_dialog_modify_complete.setOnClickListener {
            if (dialog.et_comment_modify.text.isNotEmpty()) {
                viewModel.modifyComment(args.boardId, commentId, dialog.et_comment_modify.text.toString())
                Toast.makeText(requireContext(), "댓글 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.btn_dialog_modify_close.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun setCommentDeleteDialog(commentId: Long) {
        val dialog = Dialog(requireContext())

//        val binding = DialogCommentDeleteBinding.inflate(LayoutInflater.from(context))
//        dialog.setContentView(binding.root)  // 이렇게 뷰바인딩 가능

        dialog.setContentView(R.layout.dialog_comment_delete)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

//        binding.dialogCommentDeleteCancel.setOnClickListener {
//            dialog.dismiss()
//        }
        dialog.dialog_comment_delete_complete.setOnClickListener {
            viewModel.deleteComment(args.boardId, commentId)
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
            viewModel.deletePost(args.boardId)
            dialog.dismiss()
        }

        dialog.dialog_post_delete_cancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun setRequestSolve() {
        val dialog = Dialog(requireContext())

        dialog.setContentView(R.layout.dialog_request_post_solve)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        dialog.dialog_post_solve_complete.setOnClickListener {
            viewModel.postSolve(args.boardId, SolveRequest(true))
            dialog.dismiss()
        }
        dialog.dialog_post_solve_cancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun initDataSettings() {
        val repository = BoardRepository()
        val factory = BoardViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[BoardViewModel::class.java]

        viewModel.getPostDetailInfo(args.boardId)
        viewModel.getAllComments(args.boardId)

        commentAdapter = CommentAdapter(requireContext(), viewModel, args.boardId)

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}