package com.jamin.rescue.io;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by wangjieming on 2017/8/2.
 */

public class FileUtil {


    /**
     * compress fileArr to zip
     *
     * @param fileArr
     * @param file    target zipFile
     * @return
     */
    public static boolean compress(File[] fileArr, File file) {
        try {
            byte[] bytes = new byte[1024];
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file));
            for (int i = 0; i < fileArr.length; i++) {
                if (fileArr[i].exists()) {
                    FileInputStream fileInputStream = new FileInputStream(fileArr[i]);
                    zipOutputStream.putNextEntry(new ZipEntry(fileArr[i].getName()));
                    while (true) {
                        int read = fileInputStream.read(bytes, 0, 1024);
                        if (read <= 0) {
                            break;
                        }
                        zipOutputStream.write(bytes, 0, read);
                    }
                    fileInputStream.close();
                }
            }
            zipOutputStream.flush();
            zipOutputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            Log.v("Rescue", "Rescue zip file not found");
            return false;
        } catch (IOException e2) {
            Log.v("Rescue", "Rescue zip file io err");
            return false;
        }
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
                } catch (IOException e) {
                }
        }
        return false;
    }


    public static void deleteDirWithFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWithFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }
}
