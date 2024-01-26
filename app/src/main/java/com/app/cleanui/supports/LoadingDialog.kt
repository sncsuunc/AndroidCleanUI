package com.app.cleanui.supports

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.app.cleanui.R
import com.app.cleanui.databinding.DialogLoadingBinding

object LoadingDialog {

    private var dialog: InnerLoadingDialog? = null

    fun show(context: Context, title: String = ""): Dialog {
        if (dialog?.isShowing == true)
            dismiss()
        val inflater = (context as Activity).layoutInflater
        val binding = DialogLoadingBinding.inflate(inflater)
        if (title.isNotEmpty()) {
            binding.textMessage.text = title
            binding.textMessage.visibility = View.VISIBLE
        }
        setColorFilter(
            binding.progressBar.indeterminateDrawable,
            ResourcesCompat.getColor(context.resources, R.color.md_blue_500, null)
        )

        dialog = InnerLoadingDialog(context)
        dialog?.setContentView(binding.root)
        run {
            dialog?.show()
        }
        return dialog!!
    }

    fun dismiss() {
        try {
            dialog?.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        dialog = null
    }

    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    @Suppress("DEPRECATION")
    class InnerLoadingDialog(context: Context) : Dialog(context, R.style.LoadingDialog) {
        init {
            window?.decorView?.rootView?.setBackgroundResource(android.R.color.transparent)
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }
        }
    }
}