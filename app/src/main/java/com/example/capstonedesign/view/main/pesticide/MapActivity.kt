package com.example.capstonedesign.view.main.pesticide

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.example.capstonedesign.R
import com.example.capstonedesign.databinding.ActivityMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource


class MapActivity : FragmentActivity(), OnMapReadyCallback {
    private val binding: ActivityMapBinding by lazy {
        ActivityMapBinding.inflate(layoutInflater)
    }
    private val PERMISSIONS = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    private val LOCATION_PERMISSION_REQUEST_CODE = 1000
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        // 뷰 역할을 하는 프래그먼트 객체 얻기
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(nm: NaverMap) {
        naverMap = nm
        naverMap.locationSource = locationSource
        naverMap.uiSettings.isLocationButtonEnabled = true

        ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE);
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // 권한 획득 여부 확인
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
            } else {
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
        }
    }
}