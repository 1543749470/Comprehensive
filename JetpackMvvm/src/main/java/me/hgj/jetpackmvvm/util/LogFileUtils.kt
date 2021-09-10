package me.hgj.jetpackmvvm.util

import me.hgj.jetpackmvvm.network.interceptor.logging.RequestLog
import me.hgj.jetpackmvvm.network.interceptor.logging.ResponseResult
import java.util.*

/**
 * @author heminghao
 * @description:用户图像工具类;存储用户活动轨迹
 * @date :2021/4/22 11:04 AM
 */
object LogFileUtils {
    //存储activity的浏览记录
    private val activityUserImage by lazy {
        mutableListOf<String>()
    }

    //存储fragment的浏览记录
    private val fragmentUserImage by lazy {
        mutableListOf<String>()
    }

    //响应结果的log
    private val responseLogList by lazy {
        /**
         * 将list调用[Collections.synchronizedCollection]包装为线程安全的集合
         */
        Collections.synchronizedCollection(mutableListOf<ResponseResult>())
    }

    //请求结果的Log
    private val requestLogList by lazy {
        /**
         * 将list调用[Collections.synchronizedCollection]包装为线程安全的集合
         */
        Collections.synchronizedCollection(mutableListOf<RequestLog>())
    }

    fun addActivity(activityName: String) {
        activityUserImage.add(activityName)
    }

    fun addFragment(fragmentName: String) {
        fragmentUserImage.add(fragmentName)
    }

    fun addResponseResult(responseResult: ResponseResult) {
        responseLogList.add(responseResult)
    }

    fun addRequestLogList(requestLog: RequestLog) {
        requestLogList.add(requestLog)
    }

    //获取activity的浏览记录
    fun getActivities(): MutableList<String> = activityUserImage

    //获取fragment的浏览记录
    fun getFragments(): MutableList<String> = fragmentUserImage

    //获取响应log记录
    fun getResponseLogs(): MutableCollection<ResponseResult> = responseLogList

    //获取请求log记录
    fun getRequestLogs(): MutableCollection<RequestLog> = requestLogList


}