package com.example.capstonedesign.view.main.directory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.capstonedesign.adapter.VirusImgAdapter
import com.example.capstonedesign.databinding.FragmentDiseaseDetailBinding
import com.example.capstonedesign.viewmodel.OpenApiViewModel

class DiseaseDetailFragment: Fragment() {
    private var _binding: FragmentDiseaseDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DiseaseDetailFragmentArgs>()
    private val viewModel: OpenApiViewModel by viewModels()
    private lateinit var virusImgAdapter: VirusImgAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDiseaseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        initData()
        setObserver()

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.ltDiseaseDetailPesticide.setOnClickListener {
            Toast.makeText(requireContext(),"농약정보",Toast.LENGTH_SHORT).show()


        }

    }

    private fun setObserver() {
        viewModel.diseaseDetailInfo.observe(viewLifecycleOwner) {
            setView()
        }
        viewModel.diseaseDetailInfoCompleted.observe(viewLifecycleOwner) {

        }
    }

    private fun setView() {
        val response = viewModel.diseaseDetailInfo.value!!

        if (response.imageList?.item?.isNotEmpty() == true) {
            Glide.with(requireActivity()).load(
                response.imageList.item[0].image.toString().replace("amp;","")
            ).into(binding.ivDiseaseDetailThumb)
        }
        binding.tvDiseaseDetailNameKor.text = response.sickNameKor
        binding.tvDiseaseDetailNameChi.text = response.sickNameChn
        binding.tvDiseaseDetailNameEng.text = response.sickNameEng
        binding.tvDiseaseDetailNameCrop.text = response.cropName

        if (response.virusList?.item?.isNotEmpty() == true) {
            binding.tvDiseaseDetailVirusName.text = response.virusList.item[0].virusName
            binding.tvDiseaseDetailVirusDescription.text = response.virusList.item[0].sfeNm?.replace("&lt;br/&gt;","")?.replace("&#xD;","")

            virusImgAdapter = VirusImgAdapter(requireContext(), requireActivity(), response.imageList?.item!!)
            binding.rvDiseaseDetailVirusImg.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
                adapter = virusImgAdapter
            }
        }

        binding.tvDiseaseDetailInfo1.text = response.developmentCondition?.replace("&lt;br/&gt;","")?.replace("&#xD;","")
        binding.tvDiseaseDetailInfo2.text = response.symptoms?.replace("&lt;br/&gt;","")?.replace("&#xD;","")
        binding.tvDiseaseDetailInfo3.text = response.preventionMethod?.replace("&lt;br/&gt;","")?.replace("&#xD;","")
    }

    private fun initData() {
        val sickKey = args.sickKey
        viewModel.searchDiseaseDetailInfo(sickKey)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}