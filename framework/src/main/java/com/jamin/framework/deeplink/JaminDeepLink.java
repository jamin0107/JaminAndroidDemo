package com.jamin.framework.deeplink;

import com.airbnb.deeplinkdispatch.DeepLinkSpec;

/**
 * Created by jamin on 2017/2/15.
 */

@DeepLinkSpec(prefix = {JaminDeepLinkConstant.SCHEME})
public @interface JaminDeepLink {
    String[] value();
}