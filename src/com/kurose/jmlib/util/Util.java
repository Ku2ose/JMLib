package com.kurose.jmlib.util;

import com.google.gson.Gson;
import com.kurose.jmlib.setup.ConfigPojo;
import com.mpatric.mp3agic.ID3v1Genres;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Util
{
    public static Boolean writeConfigJsonFile(String homeDir, String mxm_api_key)
    {
        Boolean result = false;
        Gson       gson = new Gson();
        ConfigPojo pojo = new ConfigPojo();
        FileWriter writer;

        pojo.setHome_dir(homeDir);
        pojo.setMxm_api_key(mxm_api_key);
        
        String json = gson.toJson(pojo);
        
        if (new File(Refs.CONFIG_FILE).exists()) {
            if (!new File(Refs.CONFIG_FILE).delete()) {
                System.out.println("CRITICAL ERROR! Cannot delete the older config file.");
                // Todo terminare in qualche modo il programma o risolvere!
            }
        }
    
        try {
            writer = new FileWriter(Refs.CONFIG_FILE);
            writer.write(json);
            writer.flush();
            writer.close();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ConfigPojo getPOJO()
    {
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(Refs.CONFIG_FILE));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gson.fromJson(br, ConfigPojo.class);
    }

    public static String capitalizeFirstLetter(String in)
    {
        if (in == null || in.length() == 0) return "";
        StringBuilder out = new StringBuilder();

        String[] splits = in.split(" ");

        for (String s : splits){
            out.append(s.substring(0, 1).toUpperCase()).append(s.substring(1, s.length())).append(" ") ;
        }
        return out.toString().trim();
    }
    
    public static ObservableList<String> getGenreOptions(){
        ArrayList<String> temp = new ArrayList<>(Arrays.asList(ID3v1Genres.GENRES));
        temp.add("NONE");
        Collections.sort(temp);
        return FXCollections.observableArrayList(temp);
    }
    
    public static Image byteArrayToFXImage(byte[] imageBytes)
    {
        InputStream   is = new ByteArrayInputStream(imageBytes);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(is);
        } catch (IOException e) {
            System.err.printf("Failed to convert byte[] to BufferedImage: %s", e.getMessage());
        }
        return SwingFXUtils.toFXImage(bi, null);
    }

    public static byte[] fromImageToByteArray(Image image){
        byte[] fin = new byte[0];

        BufferedImage buffImage = SwingFXUtils.fromFXImage(image, null);
        //BufferedImage temp = qualityUpgrade(buffImage, 150, 150);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(buffImage, "png", baos);
            fin = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
        return fin;
    }

}
