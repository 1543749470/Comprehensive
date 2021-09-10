package me.hgj.jetpackmvvm.util

import com.google.gson.Gson

/**
 * @author heminghao
 * @description: gson工具类
 * @date :2020/11/4 2:19 PM
 */
object GsonUtils {
    private val gson: Gson by lazy { Gson() }

    fun getInstance(): Gson {
        return gson
    }

    /**
     * 将gson字符换转换为实体类
     */
    fun <T> gson2Entity(gsonStr: String, clazz: Class<T>): T = gson.fromJson(gsonStr, clazz)

    /**
     * 将实体类转换为gson字符串
     */
    fun <T> entity2Gson(entity: T): String = gson.toJson(entity)
}