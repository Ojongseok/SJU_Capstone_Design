package com.example.capstonedesign.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var list1: Elements
    private lateinit var list2: Elements
    private lateinit var list3: Elements
    private lateinit var alertMonthAdapter1: AlertMonthAdapter
    private lateinit var alertMonthAdapter2: AlertMonthAdapter
    private lateinit var alertMonthAdapter3: AlertMonthAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            initData()

            withContext(Dispatchers.Main) {
                alertMonthAdapter2 = AlertMonthAdapter(list2)

                binding.rvHomeAlertMonth2.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = alertMonthAdapter2
                }
                binding.tvHomeAlert2.text = list2.size.toString()


                alertMonthAdapter3 = AlertMonthAdapter(list3)

                binding.rvHomeAlertMonth3.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = alertMonthAdapter3
                }
                binding.tvHomeAlert3.text = list3.size.toString()


            }
        }
    }

    private fun initData() {
        val url = "https://ncpms.rda.go.kr/npms/NewIndcUserR.np?indcMon=&indcSeq=206&ncpms.cmm.token.html.TOKEN=d9158d3782321ff65ee9da4ca2ac9ef6&pageIndex=1&sRegistDatetm=&eRegistDatetm=&sCrtpsnNm=&sIndcSj="
        val doc = Jsoup.connect(url).get()

//        list1 = doc.select("li.watch").select("ul.afterClear")    // 경보
        list2 = doc.select("li.watch").select("ul.afterClear").select("li")    // 주의보
        list3 = doc.select("li.forecast").select("ul.afterClear").select("li")    // 예보

    }



    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}