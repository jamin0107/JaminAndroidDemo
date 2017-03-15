package com.jamin.android.demo.remote;

import android.os.Parcel;
import android.os.Parcelable;

import com.jamin.framework.util.LogUtil;

/**
 * Created by jamin on 2017/3/15.
 */

public class RemoteObj implements Parcelable {

    private String name;
    private String password;
    private int age;

    public RemoteObj() {

    }

    public RemoteObj(Parcel parcelable) {
        name = parcelable.readString();
        password = parcelable.readString();
        age = parcelable.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static final Parcelable.Creator<RemoteObj> CREATOR = new Creator<RemoteObj>() {

        @Override
        public RemoteObj createFromParcel(Parcel source) {
            RemoteObj remoteObj = new RemoteObj(source);
            LogUtil.d("RemoteObj createFromParcel name:" + remoteObj.name + " , password:" + remoteObj.password + ", age:" + remoteObj.age);
            return remoteObj;
        }

        @Override
        public RemoteObj[] newArray(int size) {
            return new RemoteObj[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        LogUtil.d("RemoteObj writeToParcel name:" + name + " , password:" + password + ", age:" + age);
        dest.writeString(name);
        dest.writeString(password);
        dest.writeInt(age);
    }

    @Override
    public String toString() {
        return "RemoteObj{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }
}
