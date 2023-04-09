package com.example.capstonedesign.view.main.directory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.capstonedesign.databinding.ActivityImageBinding

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