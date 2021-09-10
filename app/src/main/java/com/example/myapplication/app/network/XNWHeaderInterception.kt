package com.example.syntheticalproject.app.network

import me.hgj.jetpackmvvm.ext.util.logi
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
*
*@作者 JiangXiaoBai
*
*@描述 
*
*@创建日期 2021/7/21 10:45
*/
class XNWHeaderInterception : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url()
        "url is : $url".logi("HttpLog")
        val builder = request.newBuilder()
        //TODO 方便测试写死token  测试完之后更改
//        builder.addHeader("token", "Bearer test").build()
//        builder.addHeader("token", SpUtils.getToken()).build()
        val proceed = chain.proceed(builder.build())
        //请求信息
        return proceed
    }


}