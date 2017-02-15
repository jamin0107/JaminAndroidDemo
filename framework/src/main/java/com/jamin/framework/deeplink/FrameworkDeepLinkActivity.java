package com.jamin.framework.deeplink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.airbnb.deeplinkdispatch.DeepLink;

/**
 * Created by jamin on 2017/2/15.
 */

@JaminDeepLink("/framework")
public class FrameworkDeepLinkActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false)) {
            Toast.makeText(this, "Got deep link " + intent.getStringExtra(DeepLink.URI),
                    Toast.LENGTH_SHORT).show();
        }

    }
}
