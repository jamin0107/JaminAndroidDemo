// IJaminServiceAIDL.aidl
package com.jamin.android.demo.remote;

// Declare any non-default types here with import statements
import com.jamin.android.demo.remote.RemoteObj;



interface IJaminServiceAIDL {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


    void testMethod(in RemoteObj remoteobj);

}