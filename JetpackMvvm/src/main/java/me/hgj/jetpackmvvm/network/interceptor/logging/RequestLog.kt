package me.hgj.jetpackmvvm.network.interceptor.logging

/**
 * @author heminghao
 * @description:
 * @date :2021/4/16 5:46 PM
 */
data class RequestLog(
    val url: String,//请求地址
    val network: Boolean,//网络是否可用
    val networkType: Int//当前网络类型 0:无网络;1:wifi;2;4G;3:3G;4:2G
)

