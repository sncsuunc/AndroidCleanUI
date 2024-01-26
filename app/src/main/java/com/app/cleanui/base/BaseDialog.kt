package com.app.cleanui.base

import android.content.DialogInterface.OnDismissListener
import android.view.View.OnTouchListener
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.app.cleanui.R

abstract class BaseDialog<VB: ViewBinding>(private val themeId: Int = R.style.FullScreenDialog): DialogFragment(), OnTouchListener, OnDismissListener {

    protected lateinit var binding: VB

    override fun getTheme(): Int {
        return themeId
    }

}