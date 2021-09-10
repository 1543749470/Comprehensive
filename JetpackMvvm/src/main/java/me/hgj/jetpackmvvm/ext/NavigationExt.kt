package me.hgj.jetpackmvvm.ext

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import me.hgj.jetpackmvvm.ext.util.logi
import java.security.acl.Owner

/**
 * 作者　: hegaojian
 * 时间　: 2020/5/2
 * 描述　:
 */
fun Fragment.nav(): NavController {
    return NavHostFragment.findNavController(this)
}

fun nav(view: View): NavController {
    return Navigation.findNavController(view)
}

/**
 * 防止navigation连点发生崩溃
 */
private var lastClickTime = 0L
fun NavController.navigateNoRepeat(@IdRes resId: Int, args: Bundle?, interval: Long = 800) {

    val currentTime = System.currentTimeMillis()
    if (lastClickTime != 0L && (currentTime - lastClickTime < interval)) {
        return
    }
    lastClickTime = currentTime
    args?.let {
        previousBackStackEntry?.savedStateHandle?.set("args", args)
    }
    navigate(resId, args)
}

fun NavController.navigateNoRepeat(@IdRes resId: Int, interval: Long = 800) {
    val currentTime = System.currentTimeMillis()
    if (lastClickTime != 0L && (currentTime - lastClickTime < interval)) {
        return
    }
    lastClickTime = currentTime
    navigate(resId)
}

/**
 * navigate向上一层回传值
 * <p>
 *     在Navigation 2.3.0-alpha02及更高版本中，NavBackStackEntry允许访问SavedStateHandle。SavedStateHandle是一个键值映射，
 *     可用于存储和检索数据。这些值会在进程终止（包括配置更改）时保持不变，并通过同一对象保持可用。通过使用给定的SavedStateHandle，
 *     您可以在目标之间访问和传递数据。作为将数据从堆栈中弹出后从目的地取回数据的机制，这特别有用。
 *     若要将数据从目标B传递回目标A，请首先设置目标A在其SavedStateHandle上侦听结果。为此，请使用getCurrentBackStackEntry（）
 *     API检索NavBackStackEntry，然后观察SavedStateHandle提供的LiveData。
 * </p>
 */
fun NavController.navigateUpNoRepeat(args: Bundle? = null, interval: Long = 800) {
    val currentTime = System.currentTimeMillis()
    if (lastClickTime != 0L && (currentTime - lastClickTime < interval)) {
        return
    }
    lastClickTime = currentTime
    args?.let {
        previousBackStackEntry?.savedStateHandle?.set("args", args)
    }
    navigateUp()
}

/**
 * 此方法是注册一个observe来监听变化。所有建议注册在[BaseFragment.createObserver]中
 * <p>
 *     在目标B中，必须使用getPreviousBackStackEntry（）API在目标A的SavedStateHandle上设置结果。
 * </p>
 */
fun NavController.getResult(owner: LifecycleOwner, block: Bundle.() -> Unit) {
    currentBackStackEntry?.savedStateHandle?.getLiveData<Bundle>("args")
        ?.observe(owner, Observer {
            it.apply(block)
        })
}

