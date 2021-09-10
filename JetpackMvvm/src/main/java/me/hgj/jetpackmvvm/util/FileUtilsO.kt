package me.hgj.jetpackmvvm.util

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.os.StatFs
import android.provider.MediaStore
import me.hgj.jetpackmvvm.ext.util.logi
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * @author heminghao
 * @description:
 * @date :2020/8/4 7:25 PM
 */
object FileUtils {
    val filePackagePath: String = Environment.getExternalStorageDirectory().absolutePath

    /**
     * 判断文件是否已经存在
     *
     * @param fileName 要检查的文件名
     * @return boolean, true表示存在，false表示不存在
     */
    fun isFileExist(fileName: String): Boolean {
        val fileUri = "$filePackagePath/$fileName"
        "fileUri is : $fileUri".logi("FileUtils")
        val file = File(fileUri)
        return file.exists()
    }

    /**
     * 判断文件夹是否存在
     *
     * @param dirName
     * @return true存在 false不存在
     */
    fun isDirExists(dirName: String): Boolean {
        val file = File("$filePackagePath/$dirName")
        return file.exists() || file.isDirectory
    }

    /**
     * 判断文件夹是否存在
     *
     * @param dirName
     * @return true存在 false不存在
     */
    fun isDirExists(parentsDirName: String, dirName: String): Boolean {
        val file = File("$filePackagePath/$parentsDirName")
        if (file.exists()) {
            val fileList =
                getFileList(file.absolutePath)
            for (file1 in fileList!!) {
                if (dirName.endsWith(file1.name)) return true
            }
        }
        return false
    }

    /**
     * 新建目录
     *
     * @param path 目录的绝对路径
     * @return 创建成功则返回true
     */
    fun createFolder(path: String): Boolean {
        val file = File(path)
        return file.mkdirs()
    }

    /**
     * 创建文件
     *
     * @param path     文件所在目录的目录名
     * @param fileName 文件名
     * @return 文件新建成功则返回true
     */
    fun createFile(path: String, fileName: String): Boolean {
        val file = File(path + File.separator + fileName)
        if (file.exists()) {
            return false
        } else {
            try {
                return file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return false
    }

    /**
     * 创建文件
     *
     * @param path     文件所在目录的目录名
     * @param fileName 文件名
     * @return 文件新建成功则返回true
     */
    fun createFile2(path: String, fileName: String): File? {
        val file = File(path + File.separator + fileName)
        file.createNewFile()
        return file

    }

    /**
     * 删除单个文件
     *
     * @param path     文件所在的绝对路径
     * @param fileName 文件名
     * @return 删除成功则返回true
     */
    fun deleteFile(path: String, fileName: String): Boolean {
        val file = File(path + File.separator + fileName)
        return file.exists() && file.delete()
    }

    /**
     * 删除一个目录（可以是非空目录）
     *
     * @param dir 目录绝对路径
     */
    fun deleteDirection(dir: File?): Boolean {
        if (dir == null || !dir.exists() || dir.isFile) {
            return false
        }
        val listFiles = dir.listFiles() ?: return false
        for (file in listFiles) {
            if (file.isFile) {
                file.delete()
            } else if (file.isDirectory) {
                deleteDirection(file) //递归
            }
        }
        dir.delete()
        return true
    }

    /**
     * 计算某个文件的大小
     *
     * @param path 文件的绝对路径
     * @return 文件大小
     */
    fun getFileSize(path: String?): Long {
        if (path.isNullOrEmpty()) {
            return 0L
        }
        val file = File(path)
        return file.length()
    }

    /**
     * 计算某个文件夹的大小
     *
     * @param file 目录所在绝对路径
     * @return 文件夹的大小
     */
    private fun getDirSize(file: File): Double {
        return if (file.exists()) { //如果是目录则递归计算其内容的总大小
            if (file.isDirectory) {
                var size = 0.0
                val children = file.listFiles() ?: return size
                for (f in children) size += getDirSize(
                    f
                )
                size
            } else { //如果是文件则直接返回其大小,以“兆”为单位
                file.length().toDouble() / 1024 / 1024
            }
        } else {
            0.0
        }
    }

    /**
     * 获取某个路径下的文件列表
     *
     * @param path 文件路径
     * @return 文件列表File[] files
     */
    private fun getFileList(path: String?): Array<File>? {
        if (path.isNullOrEmpty()) {
            return null
        }
        val file = File(path)
        return if (file.isDirectory) {
            val files = file.listFiles()
            files
        } else {
            null
        }
    }

    /**
     * 计算某个目录包含的文件数量
     *
     * @param path 目录的绝对路径
     * @return 文件数量
     */
    fun getFileCount(path: String?): Int {
        if (path.isNullOrEmpty()) {
            return 0
        }
        val directory = File(path)
        val files = directory.listFiles() ?: return 0
        return files.size
    }

    /**
     * 获取SDCard 总容量大小(MB)
     *
     * @param path 目录的绝对路径
     * @return 总容量大小
     */
    fun getSDCardTotal(path: String?): Long {
        return if (null != path && path == "") {
            val statfs = StatFs(path)
            //获取SDCard的Block总数
            val totalBlocks = statfs.blockCount.toLong()
            //获取每个block的大小
            val blockSize = statfs.blockSize.toLong()
            //计算SDCard 总容量大小MB
            totalBlocks * blockSize / 1024 / 1024
        } else {
            0
        }
    }

    /**
     * 获取SDCard 可用容量大小(MB)
     *
     * @param path 目录的绝对路径
     * @return 可用容量大小
     */
    fun getSDCardFree(path: String?): Long {
        return if (null != path && path == "") {
            val statfs = StatFs(path)
            //获取SDCard的Block可用数
            val availaBlocks = statfs.availableBlocks.toLong()
            //获取每个block的大小
            val blockSize = statfs.blockSize.toLong()
            //计算SDCard 可用容量大小MB
            availaBlocks * blockSize / 1024 / 1024
        } else {
            0
        }
    }

    /**
     * copy文件
     *
     * @param fromPath
     * @param toPath
     * @return
     */
    fun copy(fromPath: String?, toPath: String?): Boolean {
        if (fromPath.isNullOrEmpty() || toPath.isNullOrEmpty()) {
            return false
        }

        val fromFile = File(fromPath)
        val toFile = File(toPath)
        var fis: FileInputStream? = null
        var fos: FileOutputStream? = null
        try {
            fis = FileInputStream(fromFile)
            fos = FileOutputStream(toFile)
            val bytes = ByteArray(1024)
            var len: Int
            while (fis.read(bytes).also { len = it } != -1) {
                fos.write(bytes, 0, len)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                fis?.close()
                if (fos != null) {
                    fos.flush()
                    fos.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        "fromFile length  =  ${fromFile.length()}".logi("FileUtils")
        "toFile length  =  ${+toFile.length()}".logi("FileUtils")
        return toFile.length() > 0
    }

    /**
     * 将/external/images/media/xxx路径格式的文件转为
     * /storage/emulated/0/Pictures/Screenshots/xxx路径格式
     */
    fun getRealPathFromUri(context: Context, contentUri: Uri?): String? {
        if (contentUri == null) {
            return null
        }
        var cursor: Cursor? = null
        return try {
            val pro =
                arrayOf<String>(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, pro, null, null, null)
            if (cursor == null) {
                return null
            }
            val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(columnIndex)
        } finally {
            cursor?.close()
        }
    }

    @SuppressLint("Recycle")
    fun path2Uri(context: Context, imgLocation: String): Uri? {
        val mediaUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor = context.contentResolver.query(
            mediaUri,
            null,
            MediaStore.Images.Media.DISPLAY_NAME + "= ?",
            arrayOf(imgLocation.substring(imgLocation.lastIndexOf("/") + 1)),
            null
        ) ?: return null
        var uri: Uri? = null
        if (cursor.moveToFirst()) {
            uri = ContentUris.withAppendedId(
                mediaUri,
                cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID))
            )
        }
        cursor.close()
        return uri!!
    }
}