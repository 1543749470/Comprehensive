package com.example.syntheticalproject.app.network

import com.example.syntheticalproject.app.App
import com.example.syntheticalproject.app.api.ApiService
import com.google.gson.GsonBuilder
import me.hgj.jetpackmvvm.network.BaseNetworkApi
import me.hgj.jetpackmvvm.network.CoroutineCallAdapterFactory
import me.hgj.jetpackmvvm.network.interceptor.CacheInterceptor
import me.hgj.jetpackmvvm.network.interceptor.logging.LogInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
*
*@作者 JiangXiaoBai
*
*@描述 网络请求构建器，继承BasenetworkApi 并实现setHttpClientBuilder/setRetrofitBuilder方法，
*
*@创建日期 2021/7/21 10:40
*/

class NetWorkApi: BaseNetworkApi() {

    companion object{
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetWorkApi()
        }

        //双重校验锁式-单例 封装NetApiService 方便直接快速调用
        val service by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            instance.getApi(ApiService::class.java,ApiService.SERVICE_URL)
        }
    }
    /**
     * 实现重写父类的setHttpClientBuilder方法，
     * 在这里可以添加拦截器，可以对 OkHttpClient.Builder 做任意操作
     */
    override fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.apply {
            //设置缓存配置 缓存最大10M
            cache(Cache(File(App.instance.cacheDir, "cxk_cache"), 10 * 1024 * 1024))
            //添加Cookies自动持久化
//            cookieJar(cookieJar)
            //添加公共heads 注意要设置在日志拦截器之前，不然Log中会不显示head信息
//            addInterceptor(XNWHeaderInterception())
            //添加缓存拦截器 可传入缓存天数，不传默认7天
//            addInterceptor(CacheInterceptor())
            // 日志拦截器
            addInterceptor(LogInterceptor())
            //超时时间 连接、读、写
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
        }
        return builder
    }
    /**
     * 实现重写父类的setRetrofitBuilder方法，
     * 在这里可以对Retrofit.Builder做任意操作，比如添加GSON解析器，protobuf等
     */
    override fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
        return builder.apply {
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            addCallAdapterFactory(CoroutineCallAdapterFactory())
        }
    }
}