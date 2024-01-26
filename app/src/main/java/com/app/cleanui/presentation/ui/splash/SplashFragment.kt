package com.app.cleanui.presentation.ui.splash

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.app.cleanui.databinding.FragmentSplashBinding
import com.app.cleanui.presentation.MainViewModel
import com.app.cleanui.supports.extensions.setFullScreen
import com.sscom.sdk.score.bases.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    companion object {
        private const val TIME_LOAD_FULL = 3000L
    }

    private var timer: CountDownTimer? = null
    private val viewModel: MainViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setFullScreen(true)
    }

    override fun onDetach() {
        super.onDetach()
        setFullScreen(false)
    }

    override fun getViewBinding(): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(layoutInflater)
    }

    override fun initializeView() {
        binding?.progressIndicator?.max = TIME_LOAD_FULL.toInt()
        binding?.progressIndicator?.setProgress(0, true)
    }

    override fun initializeData() {
        showFullAds()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
        timer = null
    }

    private fun showFullAds() {
        timer?.cancel()
        timer = object : CountDownTimer(TIME_LOAD_FULL, 1000L) {
            override fun onFinish() {
                Log.d(javaClass.name, "onFinish: FINISHED")
                showHomeScreen()
            }
            override fun onTick(millisUntilFinished: Long) {
                Log.d(javaClass.name, "onTick: $millisUntilFinished")
                binding?.progressIndicator?.setProgress(((TIME_LOAD_FULL - millisUntilFinished + 1000L) / 1000L).toInt(), true)
            }
        }.start()
    }

    private fun showHomeScreen() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.checkNotificationPermission()
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment(),
                NavOptions.Builder().setPopUpTo(findNavController().graph.startDestinationId, true)
                    .build())
        }
    }

}