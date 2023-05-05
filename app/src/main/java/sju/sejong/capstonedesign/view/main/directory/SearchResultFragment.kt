package sju.sejong.capstonedesign.view.main.directory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import sju.sejong.capstonedesign.adapter.SearchResultAdapter
import sju.sejong.capstonedesign.databinding.FragmentSearchResultBinding
import sju.sejong.capstonedesign.viewmodel.OpenApiViewModel

class SearchResultFragment: Fragment() {
    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OpenApiViewModel by viewModels()
    private val args by navArgs<SearchResultFragmentArgs>()
    private lateinit var searchResultAdapter: SearchResultAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        setObserver()
        setRv()

        binding.tvSearchResultKeyword.text = "- '" + args.diseaseName + "'에 대한 검색 결과입니다."

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setRv() {
        binding.rvSearchResult.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchResultAdapter
        }

        searchResultAdapter.setItemClickListener(object : SearchResultAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val sickKey = searchResultAdapter.getSickKey(position)
                val action = SearchResultFragmentDirections.actionFragmentSearchResultToFragmentDiseaseDetail(sickKey)
                findNavController().navigate(action)
            }
        })
    }

    private fun setObserver() {
        viewModel.searchDiseaseListResult.observe(viewLifecycleOwner) {
            if (it.list?.item != null) {
                searchResultAdapter.setData(it.list.item)
            }
            binding.pbSearchResult.visibility = View.GONE
        }
    }

    private fun initData() {
        viewModel.searchDiseaseForKeyword(args.searchType, args.diseaseName)

        searchResultAdapter = SearchResultAdapter(requireContext(), args.diseaseName)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}