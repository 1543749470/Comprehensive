package me.hgj.jetpackmvvm.network.interceptor.logging

import android.os.Environment
import com.google.gson.reflect.TypeToken
import me.hgj.jetpackmvvm.ext.util.logi
import me.hgj.jetpackmvvm.util.EventBusUtils
import me.hgj.jetpackmvvm.util.FileUtils
import me.hgj.jetpackmvvm.util.GsonUtils
import me.hgj.jetpackmvvm.util.LogFileUtils
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileLock
import java.util.concurrent.locks.Lock

/**
 * @author heminghao
 * @description: 写入文件需要文件存储权限,但是在当前拿不到[Context]对象。
 *                  默认用户是有存储权限的,但是如果没有请求权限写入文件会失败.
 *                  所有需要在首页请求文件写入权限,并让用户同意。
 * @date :2021/2/1 4:05 PM
 */
object WriteLogFile {
    init {
        EventBusUtils.registerEventBus(this)
    }

    /**
     * 写入log文件
     * @param url:请求的url地址
     * @param body:响应体的json
     * @param requestLog:
     */
    fun writeFile(url: String, body: String, requestLog: RequestLog?) {
        if (requestLog == null) {
//            val responseResult = getResponseResult(url, body)
//            WriteFile.writeFile(responseResult, "responseLog.txt")
//            addLogItem(responseResult, requestLog)
        } else {
//            WriteFile.writeFile(requestLog, "requestLog.txt")
            addLogItem(null, requestLog)
        }
//        addLogItem(url, body, requestLog)
    }

    private fun addLogItem(responseResult: ResponseResult?, requestLog: RequestLog?) {
        responseResult?.let {
            LogFileUtils.addResponseResult(it)
        }
        requestLog?.let {
            LogFileUtils.addRequestLogList(it)
        }
    }

    /**
     * 从response中获取到信息构建[ResponseResult]对象
     */
    private fun getResponseResult(url: String, responseStr: String): ResponseResult {
        "响应中的url - $url".logi("WriteResponseFilesssss")
        val jsonObject = JSONObject(responseStr)
        val code = jsonObject.getInt("code")
        val msg = jsonObject.getString("msg")
        return ResponseResult(url, code, msg)
    }


    private fun <T> writeFile(writeList: Collection<T>, fileName: String) {
        //创建文件。判断文件是否存在不存在就创建
        val xnwPath = "${Environment.getExternalStorageDirectory().absolutePath}/XNW"
        if (!FileUtils.isDirExists("XNW")) {
            FileUtils.createFolder(xnwPath)
        }
        val responsePath = "$xnwPath/log"
        if (!FileUtils.isDirExists("XNW", "log")) {
            FileUtils.createFolder(responsePath)
        }
        val filePath = responsePath + File.separator + fileName
        var file: File? = File(filePath)
        if ((file?.exists()) != true) {
            try {
                file!!.createNewFile()
            } catch (e: IOException) {
                file = null
                e.printStackTrace()
            }
        }

        file?.let {
            val fileOutputStream = FileOutputStream(it)
            fileOutputStream.write(GsonUtils.entity2Gson(writeList).toByteArray())
            fileOutputStream.flush()
            fileOutputStream.close()
            //=============log=============
            val fileInputStream3 = FileInputStream(it)
            val readBytes3 = fileInputStream3.readBytes()
            fileInputStream3.close()
            "写入内容的json - ${String(readBytes3)}".logi("WriteResponseFilesssss")
            "$fileName 写入完成======".logi("WriteResponseFile")
        }
    }

    /**
     * 获取Log文件夹
     */
    fun getLogDir(): File? {
        val file = File("${Environment.getExternalStorageDirectory().absolutePath}/XNW/log")
        return if (file.exists()) {
            file
        } else null
    }

    /**
     * 获取Log文件夹路径
     */
    fun getLogDirPath(): String {
        return "${Environment.getExternalStorageDirectory().absolutePath}/XNW/log"
    }

    fun writeLogFiles() {
        writeFile(LogFileUtils.getResponseLogs(), "responseLog.txt")
        writeFile(LogFileUtils.getRequestLogs(), "requestLog.txt")
        writeFile(LogFileUtils.getActivities(), "activity.txt")
        writeFile(LogFileUtils.getFragments(), "fragment.txt")

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getNetworkStatus(networkStatus: RequestLog) {
        writeFile(networkStatus.url, "", networkStatus)
    }

}