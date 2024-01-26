package com.sscom.sdk.score.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.app.cleanui.supports.AlertDialog

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected var binding: VB? = null

    abstract fun getViewBinding(): VB
    abstract fun initializeView()
    abstract fun initializeData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        initializeView()
        return binding?.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        initializeData()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    open fun showMessage(
        title: String = "",
        message: String = "",
    ) {
        AlertDialog.Builder(requireActivity())
            .setTitle(title)
            .setMessage(message)
            .enableCancel(true)
            .build()
            .show()
    }

    open fun showMessage(
        title: String = "",
        message: String = "",
        listener: AlertDialog.OnAlertDialogListener?,
    ) {
        AlertDialog.Builder(requireActivity())
            .setTitle(title)
            .setListener(listener)
            .setMessage(message)
            .enableOK(true)
            .enableCancel(true)
            .build()
            .show()
    }

    open fun showQuestion(
        title: String = "",
        message: String = "",
    ) {
        AlertDialog.Builder(requireActivity())
            .setTitle(title)
            .setMessage(message)
            .enableCancel(true)
            .build()
            .show()
    }

    open fun showQuestion(
        title: String = "",
        message: String = "",
        listener: AlertDialog.OnAlertDialogListener?,
    ) {
        AlertDialog.Builder(requireActivity())
            .setTitle(title)
            .setListener(listener)
            .setMessage(message)
            .enableOK(true)
            .enableCancel(true)
            .build()
            .show()
    }

}