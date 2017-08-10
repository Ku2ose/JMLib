package com.kurose.jmlib.util;

import com.kurose.jmlib.setup.OS;

/**    REF STANDS FOR REFERENCES
 * */

public class Refs
{
    // basic app info
    public static final String VERSION = "0.1.4";
    public static final String APPNAME = "JMLib";
    public static final String APP_LOGO = "com/kurose/jmlib/resources/Icon64x64.png";

    // app's data about it's paths
    public static final String USERHOME = System.getProperty("user.home").replace("\\", "/").concat("/");
    public static final String CONFIG_HOME = OS.getOS() == OS.WIN ?
            USERHOME + "AppData/Roaming/JMLib" : USERHOME + ".JMLib";
    public static final String CONFIG_FILE = CONFIG_HOME.concat("/Config.json");

    public static String LIB_HOME = "";

    // MXM settings
    public static String MXM_API_KEY = "";
    public static int    MAX_MXM_FILTER = 15; //TODO implement a better way of this filter?!?

    // COVER FIND SETTINGS
    public static double COVER_SAVE_SIZE = 150d;
    


}
