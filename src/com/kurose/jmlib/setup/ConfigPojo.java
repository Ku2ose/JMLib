package com.kurose.jmlib.setup;

import com.google.gson.annotations.SerializedName;

public class ConfigPojo
{
    @SerializedName("home_dir")
    private String home_dir;

    @SerializedName("mxm_api_key")
    private String mxm_api_key;

    
    public ConfigPojo(){
        
    }
    
    public ConfigPojo(String home_dir)
    {
        setHome_dir(home_dir);
    }
    
    public String getHome_dir()
    {
        return home_dir;
    }
    
    public void setHome_dir(String home_dir)
    {
        this.home_dir = home_dir;
    }

    public String getMxm_api_key() { return mxm_api_key; }

    public void setMxm_api_key(String mxm_api_key) { this.mxm_api_key = mxm_api_key; }
}
