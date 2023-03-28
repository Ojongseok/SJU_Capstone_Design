package com.example.capstonedesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.marginBottom
import androidx.core.view.setMargins
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.capstonedesign.databinding.ActivityMainBinding
import com.example.capstonedesign.view.board.BoardFragment
import com.example.capstonedesign.view.board.BoardFragmentDirections
import com.example.capstonedesign.view.home.HomeFragment
import com.example.capstonedesign.view.home.HomeFragmentDirections

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var navController: NavController
    var navState : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupJetpackNavigation()

    }

    private fun setupJetpackNavigation() {
        val host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.findNavController()

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.menu.getItem(1).isEnabled = false

        navController.addOnDestinationChangedListener { _, destination, _ ->
            // 바텀 네비게이션이 표시되는 Fragment
            if(destination.id == R.id.fragment_home || destination.id == R.id.fragment_board
                || destination.id == R.id.dialog_bottom_sheet) {
                binding.bottomAppBar.visibility = View.VISIBLE
                binding.fab.visibility = View.VISIBLE

                navState = true
                binding.navState = navState
//                val layoutParams = CoordinatorLayout.LayoutParams(
//                    CoordinatorLayout.LayoutParams.MATCH_PARENT,CoordinatorLayout.LayoutParams.MATCH_PARENT
//                )
//                layoutParams.setMargins(0,0,0, 100)
//
//                binding.frameLayout.layoutParams = layoutParams
            }
            // 바텀 네비게이션이 표시되지 않는 Fragment
            else {
                binding.bottomAppBar.visibility = View.GONE
                binding.fab.visibility = View.GONE

                navState = false
                binding.navState = navState
//                val layoutParams = CoordinatorLayout.LayoutParams(
//                    CoordinatorLayout.LayoutParams.MATCH_PARENT,CoordinatorLayout.LayoutParams.MATCH_PARENT
//                )
//                layoutParams.setMargins(0,0,0,0)
//
//                binding.frameLayout.layoutParams = layoutParams
            }
        }

        binding.fab.setOnClickListener {
            val currentFrg = NavHostFragment.findNavController(host).currentDestination!!.id
            if (currentFrg == R.id.fragment_home) {
                navController.navigate(HomeFragmentDirections.actionFragmentHomeToDialogBottomSheet())
            } else if (currentFrg == R.id.fragment_board) {
                navController.navigate(BoardFragmentDirections.actionFragmentBoardToDialogBottomSheet())
            }
        }
    }
}