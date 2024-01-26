package com.app.cleanui.base

import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.app.cleanui.supports.AlertDialog
import com.app.cleanui.supports.ConnectionService

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("BASE_ACTIVITY", "PERMISSION SUCCESS")
        } else {
            Log.d("BASE_ACTIVITY", "PERMISSION ERROR")
        }
    }

    protected var binding: VB? = null

    abstract fun onConnectionState(isConnected: Boolean)
    abstract fun getViewBinding(): VB
    abstract fun initializeView()
    abstract fun initializeData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        initializeView()
        initializeData()
        initializeConnect()
        setContentView(binding?.root)
    }

    open fun showMessage(
        title: String = "",
        message: String = "",
    ) {
        AlertDialog.Builder(this)
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
        AlertDialog.Builder(this)
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
        AlertDialog.Builder(this)
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
        AlertDialog.Builder(this)
            .setTitle(title)
            .setListener(listener)
            .setMessage(message)
            .enableOK(true)
            .enableCancel(true)
            .build()
            .show()
    }

    private fun initializeConnect() {
        ConnectionService(this).observe(this) {
            onConnectionState(isConnected = it)
        }
    }

}