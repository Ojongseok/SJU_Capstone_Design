package sju.sejong.capstonedesign.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import sju.sejong.capstonedesign.databinding.ActivityHomeWebViewBinding

class HomeWebViewActivity : AppCompatActivity() {
    private val binding: ActivityHomeWebViewBinding by lazy {
        ActivityHomeWebViewBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.webviewHome.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }
        binding.webviewHome.loadUrl("https://ncpms.rda.go.kr/mobile/NewIndcUserListR.ms")

    }
}