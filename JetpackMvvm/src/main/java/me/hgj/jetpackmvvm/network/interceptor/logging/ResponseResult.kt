package me.hgj.jetpackmvvm.network.interceptor.logging

/**
 * @author heminghao
 * @description:
 * @date :2021/4/22 2:19 PM
 */
data class ResponseResult(
    val url: String,//请求的url
    val result: Int,//请求的结果
    val message: String//请求结果的msg
)
