package com.app.cleanui.presentation.ui.home

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.app.cleanui.databinding.FragmentHomeBinding
import com.app.cleanui.domain.entities.NoteModel
import com.app.cleanui.presentation.MainViewModel
import com.sscom.sdk.score.bases.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun initializeView() {
        binding?.root?.setOnClickListener {
            viewModel.addNote(NoteModel().apply {
                note = "Hello"
                timestamp = 1000
            })
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSettingFragment())
        }
    }

    override fun initializeData() {

    }


}