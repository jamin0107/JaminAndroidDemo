package com.jamin.deeplink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.airbnb.deeplinkdispatch.DeepLink;

/**
 * Created by jamin on 2017/2/15.
 */

@DeepLink("jamin://com.jamin/deeplink/{id}")
public class CloudDeepLinkActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false)) {
            Bundle parameters = intent.getExtras();
            if (parameters != null) {
                String queryParameter = parameters.getString("hello");
                String idString = parameters.getString("id");
                Toast.makeText(this, "hello + " + queryParameter + ", id = " + idString, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
