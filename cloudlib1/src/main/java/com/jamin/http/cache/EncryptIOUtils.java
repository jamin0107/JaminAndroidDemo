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

class EncryptIOUtils {

  private static final int BUFFER_SIZE = 4096;
  private static final int MAX_STRING_LENGTH = 1024 * 1024;// 超过这个值的string，就不解析了，防止oom
  private static final byte KEY = (byte) 777;

  /**
   * 读取文件到字符串，大文件勿用
   */
  static String readFileToString(File file, String charset) {
    if (file == null) return null;
    String data;
    byte[] buffer = new byte[BUFFER_SIZE];
    ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
    FileInputStream in = null;
    try {
      in = new FileInputStream(file);
      for (int len = -1; (len = in.read(buffer)) != -1; ) {
        out.write(decrypt(buffer, KEY), 0, len);
        if (out.size() > MAX_STRING_LENGTH) {
          Log.e("IoUtils", "File too large, maybe not a string. " + file.getAbsolutePath());
          return null;
        }
      }
      data = out.toString(charset);
    } catch (IOException e) {
      e.printStackTrace();
      data = null;
    } catch (Exception e) {
      e.printStackTrace();
      data = null;
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException ignore) {
        }
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
   * @return 是否保存成功
   */
  static boolean saveStringToFile(String data, File file, String charset) {
    if (file == null || data == null) return false;

    OutputStream out = null;
    try {
      out = new FileOutputStream(file);
      out.write(encrypt(data, KEY, charset));
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (IOException ignore) {
        }
      }
    }
    return false;
  }

  private static byte[] encrypt(String str, byte skey, String charset) throws Exception {
    byte[] bytes = str.getBytes(charset);

    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte) (bytes[i] ^ skey);
    }
    return bytes;
  }


  private static byte[] decrypt(byte[] bytes, byte skey) throws Exception {

    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte) (bytes[i] ^ skey);
    }
    return bytes;
  }
}
