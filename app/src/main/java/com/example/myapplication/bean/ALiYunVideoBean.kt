package com.example.syntheticalproject.data.bean

data class ALiYunVideoBean(
    val code: String,
    val data: Data,
    val message: String,
    val requestId: String,
    val result: String
)

data class Data(
    val accessKeyId: String,
    val accessKeySecret: String,
    val expiration: String,
    val securityToken: String,
    val videoId: String
)