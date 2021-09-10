package me.hgj.jetpackmvvm.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.getVmClazz
import me.hgj.jetpackmvvm.network.manager.NetState
import me.hgj.jetpackmvvm.network.manager.NetworkStateManager
import me.hgj.jetpackmvvm.util.LogFileUtils

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/12
 * 描述　: ViewModelFragment基类，自动把ViewModel注入Fragment和Databind注入进来了
 * 需要使用Databind的清继承它
 */
abstract class BaseVmDbFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    //是否第一次加载
    public var isFirst: Boolean = false

    //该类负责绑定视图数据的Viewmodel
    lateinit var mViewModel: VM

    //该类绑定的ViewDataBinding
    lateinit var mDatabind: DB
    var lastView: View? = null//记录上次创建的view


    /**
     * 当前Fragment绑定的视图布局
     */
    abstract fun layoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LogFileUtils.addFragment("${javaClass.`package`?.name}.${javaClass.simpleName}")
        if (lastView == null) {
            mDatabind = DataBindingUtil.inflate(inflater, layoutId(), container, false)
            mDatabind.lifecycleOwner = this
            lastView = mDatabind.root
        }

        return lastView
    }

    //设置延迟跳转来优化跳转卡顿现象
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (enter) {
            //如何nextAnim是-1或0那么就是没有设置转场动画，直接走super就行了
            if (nextAnim > 0) {
                val animation = AnimationUtils.loadAnimation(requireActivity(), nextAnim)
                //延迟100毫秒执行让View有一个初始化的时间，防止初始化时刷新页面与动画刷新冲突造成卡顿
                animation.startOffset = 100
                animation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        onEnterAnimEnd()
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                    }

                })
                return animation
            } else {
                onEnterAnimEnd()
            }
        } else {
            if (nextAnim > 0) {
                val animation = AnimationUtils.loadAnimation(requireActivity(), nextAnim)
                //延迟100毫秒执行让View有一个初始化的时间，防止初始化时刷新页面与动画刷新冲突造成卡顿
                animation.startOffset = 100
                return animation
            }
        }
        return super.onCreateAnimation(transit, enter, nextAnim)
    }

    /**
     * 如果真的要延迟初始化，那么重写这个方法，等动画结束了再初始化
     */
    fun onEnterAnimEnd() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isFirst) {
            super.onViewCreated(view, savedInstanceState)
            mViewModel = createViewModel()
            initView(savedInstanceState)
            onVisible()
            registorDefUIChange()
            initData()
            isFirst = true
        }

    }

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(netState: NetState) {}

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(this.requireActivity().application)
        ).get(getVmClazz(this))
    }

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 懒加载
     */
    abstract fun lazyLoadData()

    /**
     * 创建观察者
     */
    abstract fun createObserver()


    override fun onResume() {
        super.onResume()
        onVisible()
    }

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            lazyLoadData()
            isFirst = false
            createObserver()
            NetworkStateManager.instance.mNetworkStateCallback.observe(this, Observer {
                onNetworkStateChanged(it)
            })
        }
    }

    /**
     * Fragment执行onCreate后触发的方法
     */
    open fun initData() {}

    abstract fun showLoading(message: String = "请求网络中...")

    abstract fun dismissLoading()


    /**
     * 注册 UI 事件
     */
    private fun registorDefUIChange() {
        mViewModel.loadingChange.showDialog.observe(viewLifecycleOwner, Observer {
            showLoading()
        })
        mViewModel.loadingChange.dismissDialog.observe(viewLifecycleOwner, Observer {
            dismissLoading()
        })
    }


}