package me.hgj.jetpackmvvm.network.interceptor.logging;

import android.os.Environment;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.hgj.jetpackmvvm.ext.util.LogExtKt;
import me.hgj.jetpackmvvm.util.FileUtils;
import me.hgj.jetpackmvvm.util.GsonUtils;

/**
 * @author heminghao
 * @description:
 * @date :2021/4/19 3:29 PM
 */
public class WriteFile {
    private static final int FILE_ITEM_COUNT = 20;

    public static synchronized <T> void writeFile(T writeBean, String fileName) throws IOException {
        //创建文件。判断文件是否存在不存在就创建
        String xnwPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/XNW";
        if (!FileUtils.INSTANCE.isDirExists("XNW")) {
            FileUtils.INSTANCE.createFolder(xnwPath);
        }

        String responsePath = xnwPath + "/log";
        if (!FileUtils.INSTANCE.isDirExists("XNW", "log")) {
            FileUtils.INSTANCE.createFolder(responsePath);
        }
        String filePath = responsePath + File.separator + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                file = null;
                e.printStackTrace();
            }
        }
        if (file != null) {
            /**
             * 读取文件中的内容解析成[ArrayList<ResponseResult>],若集合长度大于等于10
             * 先将集合清空在加入本次的[ResponseResult],向文件中写入空,在将本次集合写入
             * 文件.若小于10则直接添加到集合中向文件中写入空,在将本次集合写入文件.
             */
            String content = readFile(file);
            List<T> list;
            if (content.equals("")) {
                list = new ArrayList<>();
            } else {
                list = GsonUtils.INSTANCE.getInstance().fromJson(content, new TypeToken<ArrayList<T>>() {
                }.getType());
            }
            println((fileName.equals("requestLog.txt") ? "请求LOG - " : "响应LOG - ") + "集合大小 : " + list.size());
            if (list.size() >= FILE_ITEM_COUNT) {
                list.clear();
            }
            list.add(writeBean);
            String writeStr = GsonUtils.INSTANCE.entity2Gson(list);
            writeFile(file, writeStr);
            println((fileName.equals("requestLog.txt") ? "请求LOG - " : "响应LOG - ") + "写入完成======");
        }
    }

    private static String readFile(File file) throws IOException {
        if (file == null) {
            return "";
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        int length;
        StringBuilder sb = new StringBuilder();
        while ((length = fileInputStream.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, length));
        }
        fileInputStream.close();
        return sb.toString();
    }

    private static void writeFile(File file, String writeStr) throws IOException {
        if (writeStr == null || file == null) {
            return;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(writeStr.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public static <T> int readListSize(String fileName) {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/XNW" + "/log" + File.separator + fileName;
        try {

            List<T> list = GsonUtils.INSTANCE.getInstance().fromJson(readFile(new File(filePath)), new TypeToken<ArrayList<T>>() {
            }.getType());
            return list.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static void println(String logStr) {
        LogExtKt.logi(logStr, "WriteFile");
    }

}
