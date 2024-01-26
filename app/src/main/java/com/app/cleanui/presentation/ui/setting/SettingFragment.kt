package com.app.cleanui.presentation.ui.setting

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.cleanui.databinding.FragmentSettingBinding
import com.app.cleanui.presentation.MainViewModel
import com.sscom.sdk.score.bases.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun getViewBinding(): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(layoutInflater)
    }

    override fun initializeView() {
        binding?.root?.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun initializeData() {
        lifecycleScope.launch {
            viewModel.notes.collect {
                Log.d("SETTING_FRAGMENT", "initializeData: ${it.size}")
            }
        }
    }

}