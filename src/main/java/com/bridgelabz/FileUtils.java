package com.bridgelabz;

import java.io.File;

public class FileUtils {
    public static boolean deleteFiles(File filesToBedeleted) {
        File path[] = filesToBedeleted.listFiles();
        if (path != null) {
            for (File eachFile : path) {
                deleteFiles(eachFile);
            }
        }
        return filesToBedeleted.delete();
    }
}