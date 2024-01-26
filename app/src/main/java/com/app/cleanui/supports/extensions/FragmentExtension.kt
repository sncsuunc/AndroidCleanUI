package com.app.cleanui.supports.extensions

import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.app.cleanui.R

@Suppress("DEPRECATION")
fun Fragment.setFullScreen(isFullScreen: Boolean = true) {
    if (isFullScreen) {
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        activity?.actionBar?.hide()
    } else {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        activity?.actionBar?.show()
    }
}

fun Fragment.addFragment(fragment: Fragment, container: ViewGroup, backStackName: String? = null) {
    val transaction = activity?.supportFragmentManager?.beginTransaction()
    transaction?.setCustomAnimations(
        R.anim.slide_in_right,
        R.anim.fade_out,
        R.anim.fade_in,
        R.anim.slide_out_right
    )
    transaction?.add(container.id, fragment, fragment.javaClass.name)
    transaction?.addToBackStack(backStackName)
    transaction?.commit()
}

fun Fragment.replaceFragment(fragment: Fragment, container: ViewGroup, backStackName: String? = null) {
    val transaction = activity?.supportFragmentManager?.beginTransaction()
    transaction?.setCustomAnimations(
        R.anim.slide_in_right,
        R.anim.fade_out,
        R.anim.fade_in,
        R.anim.slide_out_right
    )
    transaction?.replace(container.id, fragment, fragment.javaClass.name)
    transaction?.addToBackStack(backStackName)
    transaction?.commit()
}

fun Fragment.navigate(direction: NavDirections, isRoot: Boolean) {
    if (!isRoot) {
        findNavController().navigate(direction)
    } else {
        val options = NavOptions.Builder()
            .setPopUpTo(findNavController().graph.startDestinationId, true)
            .build()
        findNavController().navigate(direction, options)
    }
}