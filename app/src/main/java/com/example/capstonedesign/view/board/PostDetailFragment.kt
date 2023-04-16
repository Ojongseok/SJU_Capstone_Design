package com.example.capstonedesign.view.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.capstonedesign.R
import com.example.capstonedesign.databinding.FragmentPostDetailBinding
import com.example.capstonedesign.databinding.FragmentPostWriteBinding
import com.example.capstonedesign.repository.BoardRepository
import com.example.capstonedesign.view.main.directory.DiseaseDetailFragmentArgs
import com.example.capstonedesign.viewmodel.BoardViewModel
import com.example.capstonedesign.viewmodel.BoardViewModelFactory

class PostDetailFragment: Fragment() {
    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<PostDetailFragmentArgs>()
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
    }

    private fun initDataSettings() {
        viewModel.getPostDetailInfo(args.boardId)

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}