package com.example.syntheticalproject.app

import com.example.syntheticalproject.app.network.NetWorkApi
import com.example.syntheticalproject.data.bean.ALiYunVideoBean

class HttpRequestManger {
    companion object {
        val instance: HttpRequestManger by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            HttpRequestManger()
        }
    }

    suspend fun getVideoPlay(): ALiYunVideoBean {
        return NetWorkApi.service.getVideoPlayUrlInfo()
    }
}