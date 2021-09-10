package com.example.syntheticalproject.app.api

import com.example.syntheticalproject.data.bean.ALiYunVideoBean
import retrofit2.http.GET

interface ApiService {
    companion object{
        val SERVICE_URL="https://alivc-demo.aliyuncs.com"
    }


    @GET("/player/getVideoSts")
    suspend fun getVideoPlayUrlInfo():ALiYunVideoBean

}