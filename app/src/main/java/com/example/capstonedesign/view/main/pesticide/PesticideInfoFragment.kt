package com.example.capstonedesign.view.main.pesticide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign.adapter.PesticideInfoAdapter
import com.example.capstonedesign.databinding.FragmentPesticideInfoBinding
import com.example.capstonedesign.viewmodel.OpenApiViewModel


class PesticideInfoFragment: Fragment() {
    private var _binding: FragmentPesticideInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OpenApiViewModel by viewModels()
    private val args by navArgs<PesticideInfoFragmentArgs>()

    private lateinit var pesticideInfoAdapter: PesticideInfoAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPesticideInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDataSettings()
        setRecyclerView()
        setObserver()

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setRecyclerView() {
        binding.rvPesticideInfo.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pesticideInfoAdapter
            addItemDecoration(DividerItemDecoration(requireContext(),LinearLayoutManager(requireContext()).orientation))
        }

        pesticideInfoAdapter.setItemClickListener(object : PesticideInfoAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val pestiCode = viewModel.pesticideInfoResult.value?.list?.item?.get(position)?.pestiCode!!
                val diseaseUseSeq = viewModel.pesticideInfoResult.value?.list?.item?.get(position)?.diseaseUseSeq!!
                val action = PesticideInfoFragmentDirections.actionFragmentPesticideInfoToFragmentPesticideDialog(pestiCode, diseaseUseSeq)
                findNavController().navigate(action)
            }
        })
    }

    private fun setObserver() {
        viewModel.pesticideInfoResult.observe(viewLifecycleOwner) {
            if (it.list?.item != null) {
                pesticideInfoAdapter.setData(it.list.item)
                setView()
            } else {
                binding.pbPesticideInfo.visibility = View.GONE
                binding.tvPesticideInfoDescription.text = "등록된 농약 정보가 없습니다."
            }
        }
    }

    private fun setView() {
        binding.pbPesticideInfo.visibility = View.GONE
        binding.tvPesticideInfoDescription.text = "- '${args.cropName}, ${args.diseaseName}'에 대한 관련 농약 총 ${viewModel.pesticideInfoResult.value?.list?.item?.size}개 검색"


    }

    private fun initDataSettings() {
        viewModel.getPesticideInfo(args.cropName, args.diseaseName)

        pesticideInfoAdapter = PesticideInfoAdapter(requireContext())
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}