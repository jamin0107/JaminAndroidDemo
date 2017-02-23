package com.jamin.framework.deeplink;

import android.content.Intent;
import android.content.UriMatcher;
import android.net.Uri;

import com.airbnb.deeplinkdispatch.DeepLink;
import com.jamin.framework.util.LogUtil;

import java.util.List;

/**
 * Created by jamin on 2017/2/23.
 * adb shell
 * am start -W -a android.intent.action.VIEW -d "jamin://com.jamin.demo/mainpage/123?f=bac" com.jamin.android.demo
 */

public class JaminDeepLinkDispatcher {

    public static final String SCHEME = "jamin://";

    public static final String HOST = "com.jamin.demo";

    public static final String DOMAIN = SCHEME + HOST;

    public static final String PATH_MAIN_PAGE = "mainpage/";

    public static final String PATH_MAIN_PAGE_FOR_DP = "/mainpage/{id}";
//    public static final String PARAMS_MAIN_PAGE = "{id}";

    public static final String PATH_FRAMEWORK = "framework/";


    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int CODE_MAIN_PAGE = 1;


    static {
        sURIMatcher.addURI(HOST, PATH_MAIN_PAGE + "#", CODE_MAIN_PAGE);
    }


    public static void dispatch(Intent intent) {
        if (intent == null) {
            return;
        }
        if (!intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false)) {
            return;
        }
        Uri uri = intent.getData();
        LogUtil.d(uri.toString() + "," + intent.getDataString());
        LogUtil.d("getAuthority = " + uri.getAuthority());
        LogUtil.d("getLastPathSegment = " + uri.getLastPathSegment());
        LogUtil.d("getPath = " + uri.getPath());
        LogUtil.d("getSchemeSpecificPart = " + uri.getSchemeSpecificPart());
        LogUtil.d("match code = " + sURIMatcher.match(uri));
        switch (sURIMatcher.match(uri)) {
            case CODE_MAIN_PAGE:
                List<String> params = uri.getPathSegments();
                for (String param : params) {
                    LogUtil.d("param = " + param);
                }
                String f = uri.getQueryParameter("f");
                LogUtil.d("f = " + f);
                break;
        }
    }


}
