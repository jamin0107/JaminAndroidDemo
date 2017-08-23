package com.jamin.http.cache;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by wangjieming on 2017/8/22.
 */

public class IOUtils {

    public static final int BUFFER_SIZE = 4096;
    public static final int MAX_STRING_LENGTH = 1024 * 1024;// 超过这个值的string，就不解析了，防止oom


    /**
     * 读取文件到字符串，大文件勿用
     *
     * @param file
     * @param charset
     * @return
     */
    public static String readFileToString(File file, String charset) {
        if (file == null)
            return null;
        String data;
        byte[] buffer = new byte[BUFFER_SIZE];
        ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            for (int len = -1; (len = in.read(buffer)) != -1; ) {
                out.write(buffer, 0, len);
                if (out.size() > MAX_STRING_LENGTH) {
                    Log.e("IoUtils", "File too large, maybe not a string. " + file.getAbsolutePath());
                    return null;
                }
            }
            data = out.toString(charset);
        } catch (IOException e) {
            e.printStackTrace();
            data = null;
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException ignore) {
                }
            try {
                out.close();
            } catch (IOException ignore) {
            }
            in = null;
            out = null;
            buffer = null;
        }
        return data;
    }


    /**
     * 字符串保存到文件
     *
     * @param data
     * @param file
     * @param charset
     * @return 是否保存成功
     */
    public static boolean saveStringToFile(String data, File file, String charset) {
        if (file == null || data == null)
            return false;
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(data.getBytes(charset));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null)
                try {
                    out.close();
                } catch (IOException ignore) {
                }
        }
        return false;
    }

}
