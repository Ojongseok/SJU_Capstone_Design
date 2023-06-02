package sju.sejong.capstonedesign.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sju.sejong.capstonedesign.R
import sju.sejong.capstonedesign.databinding.DialogCommentDeleteBinding
import sju.sejong.capstonedesign.databinding.ItemCommentBinding
import sju.sejong.capstonedesign.model.board.AllCommentResult
import sju.sejong.capstonedesign.util.Constants.MEMBER_ID
import sju.sejong.capstonedesign.viewmodel.BoardViewModel

class CommentAdapter(
    private val context: Context,
    private val viewModel: BoardViewModel,
    private val boardId: Long) : RecyclerView.Adapter<CommentAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var commentReAdapter: CommentReAdapter
    private var commentsList = listOf<AllCommentResult>()

    inner class CustomViewHolder(private val binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AllCommentResult) {
            binding.tvItemCommentNickname.text = item.nickname
            binding.tvItemCommentContents.text = item.content
            binding.tvItemCommentDate.text = item.createdDate.removeRange(16, 19)

            if (item.memberId == MEMBER_ID) {
                binding.ltItemCommentMenu.visibility = View.VISIBLE
            } else {
                binding.ltItemCommentMenu.visibility = View.GONE
            }

            commentReAdapter = CommentReAdapter(context)
            binding.rvItemCommentRe.apply {
                val reCommentsList = commentsList[position].childComments

                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = commentReAdapter

                commentReAdapter.setData(reCommentsList)
            }
            commentReAdapter.setItemClickListener(object : CommentReAdapter.OnItemClickListener {
                override fun onClickDelete(v: View, position: Int) {
                    setCommentDeleteDialog(item.childComments[position].commentId)
                }
            })

            binding.btnItemCommentModify.setOnClickListener {
                itemClickListener.onClickModify(it, position)
            }
            binding.btnItemCommentDelete.setOnClickListener {
                itemClickListener.onClickDelete(it, position)
            }
            binding.btnItemCommentReComment.setOnClickListener {
                itemClickListener.onClickCommentRe(it, position)
            }
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(commentsList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    interface OnItemClickListener {
        fun onClickDelete(v: View, position: Int)
        fun onClickModify(v: View, position: Int)
        fun onClickCommentRe(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    fun setData(list: List<AllCommentResult>) {
        commentsList = list
        notifyDataSetChanged()
    }

    override fun getItemCount()= commentsList.size

    private fun setCommentDeleteDialog(commentId: Long) {
        val dialog = Dialog(context)
        val binding = DialogCommentDeleteBinding.inflate(LayoutInflater.from(context))

        dialog.setContentView(binding.root)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        binding.dialogCommentDeleteComplete.setOnClickListener {
            viewModel.deleteComment(boardId, commentId)
            dialog.dismiss()
        }

        binding.dialogCommentDeleteCancel.setOnClickListener {
            dialog.dismiss()
        }
    }
}