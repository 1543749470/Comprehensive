package me.hgj.jetpackmvvm.ext

import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

/**
 * @author heminghao
 * @description:
 * @date :2020/11/10 4:42 PM
 */
fun Fragment.toast(toastStr: String) {
    Toast.makeText(requireContext(), toastStr, Toast.LENGTH_SHORT).show()
}

fun Fragment.getCol(@ColorRes resId: Int): Int {

    return requireContext().resources.getColor(resId)
}

fun Fragment.getStr(@StringRes resId: Int): String {
    return requireContext().resources.getString(resId)
}

fun Fragment.getDra(@DrawableRes resId: Int): Drawable {
    return requireContext().resources.getDrawable(resId)
}