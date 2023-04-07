package com.example.capstonedesign.view.main

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.capstonedesign.adapter.CropAdapter
import com.example.capstonedesign.databinding.FragmentSearchCropInfoBinding
import com.example.capstonedesign.util.GridSpaceItemDecoration
import com.example.capstonedesign.viewmodel.OpenApiViewModel

class SearchCropInfoFragment: Fragment() {
    private var _binding: FragmentSearchCropInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OpenApiViewModel by viewModels()
    private lateinit var cropAdapter: CropAdapter
    private var cropImgList = mutableListOf<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchCropInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.etSearchContents.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                Toast.makeText(requireContext(), binding.etSearchContents.text.toString(), Toast.LENGTH_SHORT).show()

                binding.etSearchContents.clearFocus()
                binding.etSearchContents.text?.clear()
                val hideKeyboard = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                hideKeyboard.hideSoftInputFromWindow(binding.etSearchContents.windowToken, 0)
                true
            }
            false
        }

        initData()
        setObserver()
        setRv()
    }

    private fun setRv() {
        binding.rvSearchDisease.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = cropAdapter
            addItemDecoration(GridSpaceItemDecoration(requireContext(), 4,5,2))
        }

        cropAdapter.setItemClickListener(object : CropAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val cropName = cropAdapter.getCropName(position)
                val action = SearchCropInfoFragmentDirections.actionFragmentSearchCropInfoToFragmentCropDetailInfo(cropName)
                findNavController().navigate(action)
            }
        })
    }

    private fun setObserver() {
        viewModel.cropList.observe(viewLifecycleOwner) {
            cropAdapter.setData(it)
        }

        viewModel.pbCropList.observe(viewLifecycleOwner) {
            if (it) {
                binding.pbSearchCropInfo.visibility = View.GONE
            }
        }
    }

    private fun initData() {
        setCropImageList()
        viewModel.setCropList()
        cropAdapter = CropAdapter(requireContext(), cropImgList)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun setCropImageList() {
        cropImgList.add("https://blog.kakaocdn.net/dn/bRt8Ee/btqGG9WLJGu/O3jYuzQTW0glZ0jdOHwmwK/img.png")
        cropImgList.add("https://blog.kakaocdn.net/dn/boqDt1/btqDaiwEU5v/OOYqrEFLvQPSyhI5OAy9aK/img.png")
        cropImgList.add("https://mblogthumb-phinf.pstatic.net/MjAxNzA5MjhfMTQ5/MDAxNTA2NTkyNjA2NzQz.yhoavN-T0sjWHBqS5eGnvgUQE4CiGiVRKLkqJyZEkpMg.4itNtthFo6wRT8ncflCmV3_Htz-LcnVK8Ws2izdrXCIg.JPEG.thefigaro/%EC%A0%81%EA%B2%A8%EC%9E%90.jpg?type=w800")
        cropImgList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSanN8Faw5OKoovliRBWFBLlIscADgftDZdSQ&usqp=CAU")
        cropImgList.add("https://www.yyg.go.kr/contents/8581/img_pepper03.jpg?build_20230208002?quality=50")
        cropImgList.add("https://esingsing.co.kr/data/file/a12/991117883_s9XNZbpt_4f01a14024bcf72a7842f2c80a10eca5f7c18153.jpg")
        cropImgList.add("https://blog.kakaocdn.net/dn/r7Q4e/btqD1qTQjJi/AJcO4gnRneOPnJ4CVLKN1k/img.jpg")
        cropImgList.add("https://blog.kakaocdn.net/dn/Dk8Ks/btraVU9Pb3e/X9hI7qm0u7B7xxC5JKi8o1/img.png")
        cropImgList.add("https://blog.kakaocdn.net/dn/cEbGV5/btreOr5Cjhf/0OYwgK0fRK7dqknk4srg41/img.jpg")
        cropImgList.add("https://src.hidoc.co.kr/image/lib/2021/9/3/1630652987056_0.jpg")  // 10
        cropImgList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRz4GfTfHDwszjpRNM6IWh3wu6HsUT_JYs9Zem065iFMtJuPvUi6H8mL_dAHL6nhlNMeQc&usqp=CAU")
        cropImgList.add("https://www.incheonin.com/news/photo/202107/81330_109254_2026.jpg")
        cropImgList.add("https://upload.wikimedia.org/wikipedia/commons/thumb/b/bb/W_tougan4091.jpg/300px-W_tougan4091.jpg")
        cropImgList.add("https://www.hadong.go.kr/_res/center/img/sub/web02154_img06.jpg")
        cropImgList.add("https://img6.yna.co.kr/etc/inner/KR/2020/06/10/AKR20200610051100063_01_i_P2.jpg")
        cropImgList.add("https://news.imaeil.com/photos/2015/04/16/2015041618450123680_l.jpg")
        cropImgList.add("http://hahoe.invil.org/image/hahoe/invil/result_20090604140230000_2.jpg")
        cropImgList.add("http://health.chosun.com/site/data/img_dir/2019/05/15/2019051501493_0.jpg")
        cropImgList.add("https://talkimg.imbc.com/TVianUpload//tvian/image/2012/03/20/oE4U7qHrEPrr634678557474669236.jpg")
        cropImgList.add("https://cdn.thekpm.com/news/photo/202111/102498_82309_0220.jpg") // 20
        cropImgList.add("https://storage.doopedia.co.kr/upload/_upload/image5/2210/24/221024024460987/221024024460987_thumb_400.jpg")
        cropImgList.add("https://ecimg.cafe24img.com/pg25b54081053021/me4206/web/product/big/20221201/060f389f0c348a7e7a9dad624c1984e4.jpg")
        cropImgList.add("http://www.ecomedia.co.kr/news/data/20200508/p1065591295640804_829_thum.JPG")
        cropImgList.add("https://hcpeople.co.kr/web/upload/NNEditor/20200217/e7be3d23a74e663e870dc2594ad1cac0.jpg")
        cropImgList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWV2_2yt9vOJ5bod1sVd-Z-mgKTFDqcRmDuJukjguWKUalc7YWuNRodwVe45zCZVBFqNk&usqp=CAU")
        cropImgList.add("https://blog.kakaocdn.net/dn/lGbnv/btqZYlkROOL/2LLIO3Mb2qk8rqO94F4Yo0/img.png")
        cropImgList.add("https://t1.daumcdn.net/cfile/tistory/1272F54750C291221C")
        cropImgList.add("https://www.thespruce.com/thmb/FQEJK_TMRNcnZfQr97S6GZGaJVY=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/how-to-grow-watermelons-1403491-hero-2d1ce0752fed4ed599db3ba3b231f8b7.jpg")
        cropImgList.add("https://esingsing.co.kr/data/file/a3/3067172994_k1uvKq4D_1a2a67698e02cc38b771157849494d073c56b992.jpg")
        cropImgList.add("https://mblogthumb-phinf.pstatic.net/MjAyMDAxMjlfMjk2/MDAxNTgwMjg2MjIyNzY2.cGEPjfIBeD2HTrMuKU0lEeiL5cvnQznR5KHKkQaGeJIg.wssWwN21Ny8Bqw-NT-tVXIE9-MQ7zV19aUTTOAI44Kgg.JPEG.imnannaa/SE-9abf054e-07d9-4104-8dd1-fe3bd0c4a037.jpg?type=w800")  // 30
        cropImgList.add("http://www.nongsaro.go.kr/mig_img/nihhs/05_dataroom/05_07woori_html/img/pic11e.jpg")
        cropImgList.add("https://storage.doopedia.co.kr/upload/_upload/image4/1802/13/180213021785615/180213021785615_thumb_400.jpg")
        cropImgList.add("https://www.dailysecu.com/news/photo/201905/50776_42826_5545.jpg")
        cropImgList.add("http://www.kenews.co.kr/data/photos/20200835/art_15985913599492_2391c3.png")
        cropImgList.add("https://www.jejutwn.com/data/photos/tjtune/20191223/art_15770352733767.jpg")
        cropImgList.add("https://t1.daumcdn.net/cfile/tistory/995451365D6F9A8217")
        cropImgList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSP89FLQsjhITfNncKjJu8n7PQl_awC-Z0GgA&usqp=CAU")
        cropImgList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTp6dXMaDghRc9jagaXM6g_xwu-0S35hnvruA&usqp=CAU")
        cropImgList.add("https://static.educalingo.com/img/ko/800/okeula.jpg")
        cropImgList.add("https://upload.wikimedia.org/wikipedia/commons/thumb/9/9c/Arctium_lappa_9.JPG/1200px-Arctium_lappa_9.JPG")  // 40
        cropImgList.add("https://t1.daumcdn.net/cfile/tistory/99986D4C5C6277251C")
        cropImgList.add("https://mblogthumb-phinf.pstatic.net/MjAyMDA2MjJfMTE5/MDAxNTkyODEyMTk2NDM5.yEmSUmz8zC7NFY9xtxvHHT3F1otsqpx0MBxul1T2lYMg.tKh4t4qSBiRDGJqaWOeqn9CO5wzjZRvnKNzL9nYervkg.JPEG.dongmu61/%EC%A3%BC%ED%82%A4%EB%8B%88_%ED%95%B8%EB%93%9C_(6).jpg?type=w800")
        cropImgList.add("https://t1.daumcdn.net/cfile/tistory/99606333598FCCFF30")
        cropImgList.add("https://post-phinf.pstatic.net/MjAyMDEyMjlfMTY4/MDAxNjA5MjExNDk5OTc1.KfxBQ1Z0JLezq6QCJOHRxDJyHNfEKbH9x6ImqqGfhCsg.5rplCdiFQIsf52JKvFG4C9-10nOEdQS45pFVQjpJgX0g.PNG/%EC%B0%B8%EC%99%B812.png?type=w1200")
        cropImgList.add("https://t1.daumcdn.net/cfile/tistory/9979D23C5E6A411720")
        cropImgList.add("https://t1.daumcdn.net/cfile/tistory/235A6736593CF9F918")
        cropImgList.add("https://blog.kakaocdn.net/dn/tizZw/btrz2rvdOmC/1VgRiWx58MQklDvwxbd0V0/img.jpg")
        cropImgList.add("https://t1.daumcdn.net/cfile/tistory/9971FA425CEBE7F42F")
        cropImgList.add("https://uyjoqvxyzgvv9714092.cdn.ntruss.com/data2/content/image/2021/05/27/20210527311577.jpg")
        cropImgList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTrT-dImNAty7E6-EvqbMk7jJScI1nDd8pd5csYKglY3lk42HEtRye1HFJqJW-ciSljTWo&usqp=CAU")  // 50
        cropImgList.add("https://blog.kakaocdn.net/dn/cPrcfu/btq6U10uouo/FOTi4wcjjS1zUPUyOMuQa1/img.png")
        cropImgList.add("https://i2-prod.mirror.co.uk/incoming/article29035428.ece/ALTERNATES/s615b/0_Bell-pepper.jpg")
        cropImgList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSxqpegQFyp41r0IM9XULQwFFW1Y6jmVdKIt7flBdV9EE3bUYVNjMQnBZ4hB8OkRh2DqB4&usqp=CAU")
        cropImgList.add("https://blog.kakaocdn.net/dn/4M2rb/btqW1tKZPex/TSMtJl2vYZ7GkrY2gKA2rk/img.png")
        cropImgList.add("https://post-phinf.pstatic.net/MjAyMDA4MjBfMTg0/MDAxNTk3OTEzMDIyNjQy.FfMoYTa_GiI3j6UXvVAUDtKfrT7kTK7vOIYbb7e_9wMg.nkRgrFNCBnVNRUsVuYD6qUJxQSKeRQFxIw3UsIE5tGIg.JPEG/suxiywqv.jpg?type=w1200")
    }
}