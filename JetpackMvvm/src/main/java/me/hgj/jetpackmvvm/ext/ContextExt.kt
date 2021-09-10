package me.hgj.jetpackmvvm.ext

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

/**
 * @author heminghao
 * @description:
 * @date :2020/11/10 4:41 PM
 */
fun Context.toast(toastStr: String) {
    Toast.makeText(this, toastStr, Toast.LENGTH_SHORT).show()
}

/**
 * 为了兼容低版本的手机。如果直接调用[Context.getColor]会产生崩溃
 */
fun Context.getCol(@ColorRes resId: Int): Int {
    return resources.getColor(resId)
}

fun Context.getStr(@StringRes resId: Int): String {
    return resources.getString(resId)
}

fun Context.getDra(@DrawableRes resId: Int): Drawable {
    return resources.getDrawable(resId)
}
