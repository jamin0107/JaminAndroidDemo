package com.jamin.android.demo.deeplink;

import android.app.Activity;
import android.os.Bundle;

import com.airbnb.deeplinkdispatch.DeepLinkHandler;
import com.jamin.framework.deeplink.FrameworkDeepLinkModule;
import com.jamin.framework.deeplink.FrameworkDeepLinkModuleLoader;

/**
 * Created by jamin on 2017/2/15.
 * adb shell
 * am start -W -a android.intent.action.VIEW -d "jamin://com.jamin/deeplink/framework" com.jamin.android.demo
 * 如果设定的是http这种scheme，多app都可以截获的情况下，指定包名，可以保证唯一
 */

@DeepLinkHandler({AppDeepLinkModule.class , FrameworkDeepLinkModule.class})
public class DeepLinkActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // DeepLinkDelegate, LibraryDeepLinkModuleLoader and AppDeepLinkModuleLoader
        // are generated at compile-time.
        DeepLinkDelegate deepLinkDelegate =
                new DeepLinkDelegate(new AppDeepLinkModuleLoader() , new FrameworkDeepLinkModuleLoader());
        // Delegate the deep link handling to DeepLinkDispatch.
        // It will start the correct Activity based on the incoming Intent URI
        deepLinkDelegate.dispatchFrom(this);
        // Finish this Activity since the correct one has been just started
        finish();
    }
}