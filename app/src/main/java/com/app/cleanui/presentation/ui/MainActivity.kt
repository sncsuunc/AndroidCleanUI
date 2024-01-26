package com.app.cleanui.presentation.ui

import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.app.cleanui.R
import com.app.cleanui.base.BaseActivity
import com.app.cleanui.databinding.ActivityMainBinding
import com.app.cleanui.presentation.MainViewModel
import com.app.cleanui.supports.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var hasBackPress: Boolean = true
    private val viewModel: MainViewModel by viewModels()

    override fun onConnectionState(isConnected: Boolean) {
        if (isConnected) {
            LoadingDialog.dismiss()
        } else {
            LoadingDialog.show(this, "DISCONNECTED")
        }
    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initializeView() {
        val navController =
            binding?.mainNavigationController?.getFragment<NavHostFragment>()?.navController
        navController?.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> {
                    hasBackPress = false
                    binding?.bottomNavigation?.visibility = View.GONE
                }
                R.id.homeFragment -> {
                    hasBackPress = false
                    binding?.bottomNavigation?.visibility = View.VISIBLE
                }
                else -> {
                    hasBackPress = true
                    binding?.bottomNavigation?.visibility = View.GONE
                }
            }
        }
        navController?.let {
            binding?.bottomNavigation?.setupWithNavController(it)
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (hasBackPress) {
                    supportFragmentManager.popBackStack()
                }
            }
        })
    }

    override fun initializeData() {
        lifecycleScope.launch {
            viewModel.permission.collect {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    checkNotificationPermission()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkNotificationPermission() {
        val permission = android.Manifest.permission.POST_NOTIFICATIONS
        when {
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {

            }

            shouldShowRequestPermissionRationale(permission) -> {

            }

            else -> {
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private fun checkMicrophonePermission() {
        val permission = android.Manifest.permission.RECORD_AUDIO
        when {
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {

            }

            shouldShowRequestPermissionRationale(permission) -> {

            }

            else -> {
                requestPermissionLauncher.launch(permission)
            }
        }
    }

}