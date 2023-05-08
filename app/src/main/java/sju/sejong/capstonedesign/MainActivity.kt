package sju.sejong.capstonedesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import sju.sejong.capstonedesign.databinding.ActivityMainBinding
import sju.sejong.capstonedesign.view.board.BoardFragmentDirections
import sju.sejong.capstonedesign.view.home.HomeFragmentDirections

@AndroidEntryPoint
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
        val host = supportFragmentManager.findFragmentById(sju.sejong.capstonedesign.R.id.nav_host_fragment) as NavHostFragment
        navController = host.findNavController()

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.menu.getItem(1).isEnabled = false

        navController.addOnDestinationChangedListener { _, destination, _ ->
            // 바텀 네비게이션이 표시되는 Fragment
            if(destination.id == sju.sejong.capstonedesign.R.id.fragment_home || destination.id == sju.sejong.capstonedesign.R.id.fragment_board
                || destination.id == sju.sejong.capstonedesign.R.id.dialog_bottom_sheet
            ) {
                binding.bottomAppBar.visibility = View.VISIBLE
                binding.fab.visibility = View.VISIBLE

                navState = true
                binding.navState = navState
            }
            // 바텀 네비게이션이 표시되지 않는 Fragment
            else {
                binding.bottomAppBar.visibility = View.GONE
                binding.fab.visibility = View.GONE

                navState = false
                binding.navState = navState
            }
        }

        binding.fab.setOnClickListener {
            val currentFrg = NavHostFragment.findNavController(host).currentDestination!!.id
            if (currentFrg == sju.sejong.capstonedesign.R.id.fragment_home) {
                navController.navigate(HomeFragmentDirections.actionFragmentHomeToDialogBottomSheet())
            } else if (currentFrg == sju.sejong.capstonedesign.R.id.fragment_board) {
                navController.navigate(BoardFragmentDirections.actionFragmentBoardToDialogBottomSheet())
            }
        }
    }
}