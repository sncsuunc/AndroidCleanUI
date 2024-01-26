@file:Suppress("UNUSED")
package com.app.cleanui.supports

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnDismissListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.isVisible
import com.app.cleanui.R
import com.app.cleanui.databinding.DialogAlertBinding

class AlertDialog(context: Context) : Dialog(context, R.style.AlertDialog), OnTouchListener,
    OnDismissListener {

    private var mListener: OnAlertDialogListener? = null
    private var binding: DialogAlertBinding

    fun interface OnAlertDialogListener {
        fun onPressedDialog(isOK: Boolean)
    }

    init {
        binding = getViewBinding()
        requestLayout()
        initializeView()
    }

    class Builder(val context: Context) {

        private var title: String? = null
        private var message: String? = null
        private var isEnableOK: Boolean = false
        private var isEnableCancel: Boolean = true
        private var mListener: OnAlertDialogListener? = null

        fun setTitle(title: String?): Builder {
            this.title = title
            return this
        }

        fun setMessage(message: String?): Builder {
            this.message = message
            return this
        }

        fun enableOK(isEnable: Boolean): Builder {
            isEnableOK = isEnable
            return this
        }

        fun enableCancel(isEnable: Boolean): Builder {
            isEnableCancel = isEnable
            return this
        }

        fun setListener(listener: OnAlertDialogListener?): Builder {
            mListener = listener
            return this
        }

        fun build(): AlertDialog {
            mNotifyDialog?.dismiss()
            mNotifyDialog = null
            mNotifyDialog = AlertDialog(context)
            mNotifyDialog?.setTitle(title)
            mNotifyDialog?.setMessage(message)
            mNotifyDialog?.enableOK(isEnableOK)
            mNotifyDialog?.enableCancel(isEnableCancel)
            mNotifyDialog?.setListener(mListener)
            return mNotifyDialog!!
        }
    }

    private fun setTitle(title: String?) {
        binding.textTitle.text = title
        binding.textTitle.isVisible = !title.isNullOrEmpty()
    }

    private fun setMessage(message: String?) {
        binding.textMessage.text = message
        binding.textMessage.isVisible = !message.isNullOrEmpty()
    }

    private fun enableOK(isEnable: Boolean) {
        binding.buttonOk.visibility = if (isEnable) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun enableCancel(isEnable: Boolean) {
        binding.buttonCancel.visibility = if (isEnable) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun setListener(listener: OnAlertDialogListener?) {
        mListener = listener
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mNotifyDialog: AlertDialog? = null
    }

    private fun requestLayout() {
        val window = this.window ?: return
        window.setFlags(
            WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
        )
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initializeView() {
        binding.root.isClickable = true
        binding.root.isFocusableInTouchMode = true
        binding.root.setOnTouchListener(this)
        binding.buttonOk.setOnClickListener {
            dismiss()
            mListener?.onPressedDialog(true)
        }
        binding.buttonCancel.setOnClickListener {
            dismiss()
            mListener?.onPressedDialog(false)
        }
    }

    private fun hideKeyboard() {
        val view: View? = this.currentFocus
        if ((view != null) && view !is EditText) {
            (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(
                view.windowToken,
                0
            )
            view.clearFocus()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
        hideKeyboard()
        return false
    }

    override fun onDismiss(dialogInterface: DialogInterface) {
        try {
            if ((context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).isAcceptingText)
                ownerActivity?.let {
                    val inputMethodManager =
                        it.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    if (it.currentFocus != null) inputMethodManager.hideSoftInputFromWindow(
                        it.window.decorView
                            .windowToken, InputMethodManager.HIDE_NOT_ALWAYS
                    )
                }
        } catch (ignored: Exception) {

        }
    }

    private fun getViewBinding(): DialogAlertBinding {
        return DialogAlertBinding.inflate(layoutInflater)
    }

}