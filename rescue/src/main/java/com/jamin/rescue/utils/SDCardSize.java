package com.jamin.rescue.utils;

public class SDCardSize {

    public long total = 0;
    public long used = 0;

    @Override
    public String toString() {
        return "{" +
                "total=" + total / (1024 * 1024) +
                "M, used=" + used / (1024 * 1024) +
                "M}";
    }

}
