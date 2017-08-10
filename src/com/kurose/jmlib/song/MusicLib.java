package com.kurose.jmlib.song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicLib
{
    private List<Song> lib = new ArrayList<>();
    
    public MusicLib(String path_directory) {

        if (path_directory.length() < 1 || !new File(path_directory).isDirectory()){
            System.out.printf("\"&s\" is not a valid path or it's not a a directory.\n");
            return;
        }

        File[] dir = new File(path_directory).listFiles();

        if (dir == null) return;

        for (File f : dir) {
            if (f.getName().endsWith(".mp3")) {
                Song song = new Song(f.getAbsolutePath());
                lib.add(song);
            }
        }
    }

    public void print()
    {
        lib.forEach(t -> System.out.printf("%s\n", t.getPath()));
    }
    
    public List<Song> getLib()
    {
        return lib;
    }
}
