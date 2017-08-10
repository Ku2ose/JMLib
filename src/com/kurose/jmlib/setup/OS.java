package com.kurose.jmlib.setup;

import java.util.Locale;

public enum OS
{
    WIN, LINUX, MAC, NULL;
    
    public static OS getOS(){
        String so = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        if (so.indexOf("mac") >= 0 || so.indexOf("darwin") >= 0) return OS.MAC;
        else if (so.indexOf("win") >= 0) return OS.WIN;
        else if (so.indexOf("nux") >= 0) return OS.LINUX;
        return OS.NULL;
    }
}
