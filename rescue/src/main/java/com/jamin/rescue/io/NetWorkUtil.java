package com.jamin.rescue.io;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * Created by wangjieming on 2017/8/2.
 */

public class NetWorkUtil {


    public static final int   NET_OFF       = 0;    //无网络
    public static final int   NET_UNKNOWN   = 1;    //未知网络
    public static final int   NET_WIFI      = 2;    //WIFI网络
    public static final int   NET_2G        = 4;    //2G网络
    public static final int   NET_3G        = 8;    //3G网络
    public static final int   NET_4G        = 16;   //4G网络
    public static final int   NET_EXCEPTION = 32;   //获取网络状态失败


    public boolean wifiEnable(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }

        return false ;
    }


    public static boolean isWiFiActive(Application application) {
        if (application == null)
            return false;
        boolean bReturn = false;
        WifiManager mWifiManager = (WifiManager)application.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = null;
        try{
            wifiInfo = mWifiManager.getConnectionInfo();
        }catch(Exception ignored){
        }
        int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
        if (mWifiManager.isWifiEnabled() && ipAddress != 0) {
            bReturn = true;
        }
        return bReturn;
    }

    /**
     * 获取网络类型
     *
     * @param application
     * @return 网络类型
     */
    public static int getNetworkType(Application application) {
        if (application == null)
            return NET_UNKNOWN;

        int nReturn = NET_OFF;

        try {
            ConnectivityManager cm = (ConnectivityManager) application
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                int type = info.getType();
                int subType = info.getSubtype();
                if (type == ConnectivityManager.TYPE_WIFI) {
                    if (isWiFiActive(application)) {
                        nReturn = NET_WIFI;
                    }
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    switch (subType) {
                        case TelephonyManager.NETWORK_TYPE_CDMA: // = 4 ~ 14-64 kbps
                        case TelephonyManager.NETWORK_TYPE_IDEN: // = 11 ~ 25 kbps
                        case TelephonyManager.NETWORK_TYPE_1xRTT: // = 7 2.5G或者
                            // 2.75G ~
                            // 50-100 kbps
                        case TelephonyManager.NETWORK_TYPE_GPRS: // = 1 2.5G ~ 171.2
                            // kbps
                        case TelephonyManager.NETWORK_TYPE_EDGE: // = 2 2.75G ~
                            // 384-473.6
                            // kbps
                            nReturn = NET_2G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_EVDO_0: // = 5 ~ 400-1000
                            // kbps
                        case TelephonyManager.NETWORK_TYPE_UMTS: // = 3 ~ 400-7000
                            // kbps
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // = 6 ~ 600-1400
                            // kbps
                        case TelephonyManager.NETWORK_TYPE_HSPA: // = 10 3G ~
                            // 700-1700 kbps
                        case TelephonyManager.NETWORK_TYPE_EHRPD: // = 14 3.75g ~
                            // 1-2 Mbps
                        case TelephonyManager.NETWORK_TYPE_HSUPA: // = 9 ~ 1-23 Mbps
                        case TelephonyManager.NETWORK_TYPE_HSDPA: // = 8 ~ 2-14 Mbps
                        case TelephonyManager.NETWORK_TYPE_EVDO_B: // = 12 ~ 9 Mbps
                        case TelephonyManager.NETWORK_TYPE_HSPAP: // = 15 ~ 10-20
                            // Mbps
                            nReturn = NET_3G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE: // = 13 4G ~ 10+
                            // Mbps
                            nReturn = NET_4G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_UNKNOWN:// = 0
                        default:
                            nReturn = NET_UNKNOWN;
                            break;
                    }
                } else {
                    nReturn = NET_UNKNOWN;
                }
            }
        } catch (NullPointerException e) {
            nReturn = NET_EXCEPTION;
        } catch (Exception e) {
            e.printStackTrace();
            nReturn = NET_EXCEPTION;
        }
        return nReturn;
    }
}
