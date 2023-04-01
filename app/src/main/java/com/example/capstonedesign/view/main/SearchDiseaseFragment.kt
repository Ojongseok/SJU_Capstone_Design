package com.example.capstonedesign.view.main

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign.adapter.BoardPostAdapter
import com.example.capstonedesign.adapter.CropAdapter
import com.example.capstonedesign.databinding.FragmentSearchDiseaseBinding
import com.example.capstonedesign.retrofit.OpenApiRetrofitInstance.API_KEY
import com.example.capstonedesign.retrofit.OpenApiRetrofitInstance.OpenApiRetrofitService
import com.example.capstonedesign.view.board.BoardFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class SearchDiseaseFragment: Fragment() {
    private var _binding: FragmentSearchDiseaseBinding? = null
    private val binding get() = _binding!!
    private lateinit var cropAdapter: CropAdapter
    private lateinit var cropList: Elements

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchDiseaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            val response = OpenApiRetrofitService.searchDisease(API_KEY,"SVC01","AA001","상추")

            setCropData()
            withContext(Dispatchers.Main) {
                setRv()

            }
        }



    }

    private fun setCropData() {
        val url = "https://ncpms.rda.go.kr/npms/VegitablesImageListR.np"
        val doc = Jsoup.connect(url).get()

        cropList = doc.select("ul.floatDiv.mt20.ce.photoSearch").select("li")    // 작물 목록
    }

    private fun setRv() {
        cropAdapter = CropAdapter(requireContext(), cropList)

        binding.rvSearchDisease.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = cropAdapter
            // spanCount -> grid 수, space->여백
            val spanCount = 3
            val space = 20
            addItemDecoration(GridSpaceItemDecoration(spanCount, space))
        }

        cropAdapter.setItemClickListener(object : CropAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                Toast.makeText(requireContext(),position.toString(),Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
    inner class GridSpaceItemDecoration(private val spanCount: Int, private val space: Int): RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount + 1      // 1부터 시작

            if (position < spanCount){
                outRect.top = space
            }

            if (column == spanCount){
                outRect.right = space
            }

            outRect.left = space
            outRect.bottom = space
        }
    }
}