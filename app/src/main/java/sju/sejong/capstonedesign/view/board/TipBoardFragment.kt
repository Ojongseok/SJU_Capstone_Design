package sju.sejong.capstonedesign.view.board

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import sju.sejong.capstonedesign.databinding.FragmentTipBoardBinding
import sju.sejong.capstonedesign.model.board.ContentList
import sju.sejong.capstonedesign.util.Constants.LOGIN_STATUS
import sju.sejong.capstonedesign.util.SeggeredGridSpaceItemDecoration
import sju.sejong.capstonedesign.viewmodel.BoardViewModel
import sju.sejong.capstonedesign.adapter.BoardPostAdapter
import sju.sejong.capstonedesign.databinding.DialogLoginBinding
import sju.sejong.capstonedesign.dialog.LoginDialog

@AndroidEntryPoint
class TipBoardFragment: Fragment() {
    private var _binding: FragmentTipBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var tipBoardPostAdapter: BoardPostAdapter
    private val viewModel by viewModels<BoardViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTipBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        tipBoardPostAdapter =
            BoardPostAdapter(
                requireContext(),
                tipBoardList
            )

        binding.rvTipPost.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = tipBoardPostAdapter
            addItemDecoration(SeggeredGridSpaceItemDecoration(requireContext(),2))
        }

        tipBoardPostAdapter.setItemClickListener(object :BoardPostAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val loginDialog = LoginDialog(requireContext())

                if (LOGIN_STATUS) {
                    val action = BoardFragmentDirections.actionFragmentBoardToFragmentPostDetail(tipBoardList[position].boardId)
                    findNavController().navigate(action)
                } else {
                    loginDialog.showLoginDialog()

                    loginDialog.setItemClickListener(object : LoginDialog.OnItemClickListener {
                        override fun onCompleteClick(v: View) {
                            val action = BoardFragmentDirections.actionFragmentBoardToFragmentLogin()
                            findNavController().navigate(action)
                        }
                    })
                }
            }
        })
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}