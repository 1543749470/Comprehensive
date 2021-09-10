package me.hgj.jetpackmvvm.util

import org.greenrobot.eventbus.EventBus

/**
 * @author heminghao
 * @description:EventBus工具类
 * @date :2021/4/16 5:40 PM
 */
object EventBusUtils {

    /**
     * 注册EventBus
     */
    fun registerEventBus(obj: Any) {
        EventBus.getDefault().register(obj)
    }

    /**
     * 接触注册EventBus
     */
    fun unregisterEventBus(obj: Any) {
        EventBus.getDefault().unregister(obj)
    }

    /**
     * 发送EventBus事件
     */
    fun sendEvent(obj: Any) {
        EventBus.getDefault().post(obj)
    }
}