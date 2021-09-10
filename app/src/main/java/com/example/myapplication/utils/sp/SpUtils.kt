package com.fatoan.android.xnwapp.utils.sp

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.syntheticalproject.app.App
import com.tencent.mmkv.MMKV

/**
*
*@作者 JiangXiaoBai
*
*@描述 SP工具类
*
*@创建日期 2021/7/21 17:57
*/
object SpUtils {
    private val clear by lazy { MMKV.mmkvWithID("XNW", 1) }
    private val noClear by lazy { MMKV.mmkvWithID("XNW_NO", 2) }

    init {
        MMKV.initialize(App.instance)
    }

    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    private fun <T> decodeValue(key: String, defaultValue: T, mmkv: MMKV): T {
        return when (defaultValue) {
            is Int -> mmkv.decodeInt(key, defaultValue as Int)
            is Boolean -> mmkv.decodeBool(key, defaultValue as Boolean)
            is Float -> mmkv.decodeFloat(key, defaultValue as Float)
            is Long -> mmkv.decodeLong(key, defaultValue as Long)
            else -> mmkv.decodeString(key, defaultValue as String)
        } as T
    }

    private fun <T> saveValue(key: String, value: T, sp: SharedPreferences?) {
        sp!!.edit {
            when (value) {
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                else -> putString(key, value as String)
            }
        }
    }

    fun <T> getValue(key: String, defaultValue: T): T =
        decodeValue(
            key,
            defaultValue,
            clear
        )

    fun <T> saveValue(key: String, value: T) {
        saveValue(
            key,
            value,
            clear
        )
    }

    fun <T> getNoClearValue(key: String, defaultValue: T): T =
        decodeValue(
            key,
            defaultValue,
            noClear
        )

    fun <T> saveNoClearValue(key: String, value: T) {
        saveValue(
            key,
            value,
            noClear
        )
    }

    fun getAll(): Map<String, *> {
        return clear.all
    }

    fun clearAll() {
        clear.clearAll()
    }
}