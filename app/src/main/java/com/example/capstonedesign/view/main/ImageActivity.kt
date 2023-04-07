package com.example.capstonedesign.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.capstonedesign.R
import com.example.capstonedesign.databinding.ActivityImageBinding
import com.example.capstonedesign.databinding.ActivityMainBinding

class ImageActivity : AppCompatActivity() {
    private val binding: ActivityImageBinding by lazy {
        ActivityImageBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val img = intent.getStringExtra("img").toString().replace("amp;","")

        Glide.with(this).load(img).into(binding.imageFull)

        binding.imageFull.setOnClickListener {
            supportFinishAfterTransition()
        }
    }
}