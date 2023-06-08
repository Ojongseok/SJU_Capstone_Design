package sju.sejong.capstonedesign.view.board

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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import sju.sejong.capstonedesign.R
import sju.sejong.capstonedesign.adapter.CommentAdapter
import sju.sejong.capstonedesign.databinding.FragmentPostDetailBinding
import sju.sejong.capstonedesign.model.board.SolveRequest
import sju.sejong.capstonedesign.util.Constants.MEMBER_ID
import sju.sejong.capstonedesign.viewmodel.BoardViewModel
import sju.sejong.capstonedesign.databinding.DialogCommentDeleteBinding
import sju.sejong.capstonedesign.databinding.DialogCommentModifyBinding
import sju.sejong.capstonedesign.databinding.DialogPostDeleteBinding
import sju.sejong.capstonedesign.databinding.DialogPostUpdateBinding
import sju.sejong.capstonedesign.databinding.DialogRequestPostSolveBinding

@AndroidEntryPoint
class PostDetailFragment: Fragment() {
    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<BoardViewModel>()
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
                binding.textView11.text = "댓글 ${it.result.commentNum}개"

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
                        binding.btnPostDetailSolve.visibility = View.GONE
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
        val binding = DialogCommentModifyBinding.inflate(LayoutInflater.from(context))

        dialog.setContentView(binding.root)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        binding.etCommentModify.setText(contents)

        binding.btnDialogModifyComplete.setOnClickListener {
            if (binding.etCommentModify.text.isNotEmpty()) {
                viewModel.modifyComment(args.boardId, commentId, binding.etCommentModify.text.toString())
                Toast.makeText(requireContext(), "댓글 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

       binding.btnDialogModifyClose.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun setCommentDeleteDialog(commentId: Long) {
        val dialog = Dialog(requireContext())
        val binding = DialogCommentDeleteBinding.inflate(LayoutInflater.from(context))

        dialog.setContentView(binding.root)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        binding.dialogCommentDeleteComplete.setOnClickListener {
            viewModel.deleteComment(args.boardId, commentId)
            dialog.dismiss()
        }

        binding.dialogCommentDeleteCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun setUpdateDialog() {
        val dialog = Dialog(requireContext())
        val binding = DialogPostUpdateBinding.inflate(LayoutInflater.from(context))

        dialog.setContentView(binding.root)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        binding.dialogPostUpdateComplete.setOnClickListener {
            Toast.makeText(requireContext(), "준비중입니다.", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        binding.dialogPostUpdateCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun setDeleteDialog() {
        val dialog = Dialog(requireContext())
        val binding = DialogPostDeleteBinding.inflate(LayoutInflater.from(context))

        dialog.setContentView(binding.root)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        binding.dialogPostDeleteComplete.setOnClickListener {
            viewModel.deletePost(args.boardId)
            dialog.dismiss()
        }

        binding.dialogPostDeleteCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun setRequestSolve() {
        val dialog = Dialog(requireContext())
        val binding = DialogRequestPostSolveBinding.inflate(LayoutInflater.from(context))

        dialog.setContentView(binding.root)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        binding.dialogPostSolveComplete.setOnClickListener {
            viewModel.postSolve(args.boardId, SolveRequest(true))
            dialog.dismiss()
        }
        binding.dialogPostSolveCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun initDataSettings() {
        viewModel.getPostDetailInfo(args.boardId)
        viewModel.getAllComments(args.boardId)

        commentAdapter = CommentAdapter(requireContext(), viewModel, args.boardId)

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}