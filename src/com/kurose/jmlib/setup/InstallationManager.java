package com.kurose.jmlib.setup;

import com.kurose.jmlib.util.Refs;
import com.kurose.jmlib.util.Util;

import javax.swing.*;
import java.io.File;

public class InstallationManager
{
    
    private final String defaultLibDir = Refs.USERHOME.concat("Music/JMLib");

    /***
     *  Installs the files need for the application. If it fails the will be closed and the user prompted.
     */
    public void install()
    {
        if (!isAlreadyInstalled()) {

            if (!createFirstConfigFile()) {
                String msg = "***** INSTALLATION UNSUCCESSFUL!!! KILLING THE APP!!! *****";
                System.out.println(msg);
                JOptionPane.showMessageDialog(null, msg, "FATAL ERROR", JOptionPane.ERROR_MESSAGE);

                System.exit(-1);
            }

            createMusicLibrary();
    
            System.out.println("Installation configuration files completed! :)\n");
        }
    }

    /***
     * At every start of the app check if the necessary files are already installed
     * @return true if those are already installed, false otherwise
     */
    private Boolean isAlreadyInstalled(){
        if (new File(Refs.CONFIG_FILE).exists()) {
            System.out.printf("A config file has been found and is being loaded: %s\n", new File(Refs.CONFIG_FILE).getAbsolutePath());
            return true;
        }
        System.out.println("No config file found! Creating config file at deafault location...\n");
        return false;
    }


    /***
     *  This method is called to create the folder of the settings of the app on the hard disk.
     */
    private void createJMLibConfigDir()
    {
        File dir = new File(Refs.CONFIG_HOME);
    
        if (dir.mkdirs()) {
            String msg = "JMLib Configuration folder has been created to the path: ".concat(dir.getAbsolutePath());
            System.out.println(msg + " ...\n");
        } else {
            String msg = "failed to create or already existed a configuration folder at: ".concat(dir.getAbsolutePath());
            System.out.println(msg + " ...\n");
        }
    }

    /***
     * Launches first a method to create a folder that will contain the file used for setting. This will be a Json file
     * and will contain a path of the media library. Will prompt the user the result of the operation.
     *
     * @return true if written successfully, false otherwise
     */
    private Boolean createFirstConfigFile()
    {
        createJMLibConfigDir();

        if (Util.writeConfigJsonFile(defaultLibDir, "")) {
            String msg = "Json config file successfully written at " + Refs.CONFIG_FILE;
            System.out.println(msg + " ...\n");
            JOptionPane.showMessageDialog(null, msg, "Alert", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false; // something bad it's going to happen...
    }


    /***
     *  Creates a folder that is going to store the mp3 read by the application. Prompts the user if the creation
     *  was successful. It doesn't warn in any way if the directory haven't been create or already existed
     */
    private void createMusicLibrary()
    {
        File f = new File(defaultLibDir);
        
        if (f.mkdir()) {
            
            String msg = "Created Library folder at: ".concat(f.getAbsolutePath());
            System.out.println(msg + " ...\n");
            JOptionPane.showMessageDialog(null, msg,
                                          "Alert", JOptionPane.INFORMATION_MESSAGE);
            
        }
    }
    
}
