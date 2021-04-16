package com.bridgelabz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

class FileOperationsTest {
    public static String HOME = System.getProperty("user.home");
    public static String PLAY_WITH_IO = "TempPlay";

    @Test
    public void givenPath_CheckIfExists() throws IOException {
        //Checking if Home Path Exists
        Path homePath = Paths.get(HOME);
        Assertions.assertTrue(Files.exists(homePath));

        // To Delete file
        Path playPath = Paths.get(HOME + "/" + PLAY_WITH_IO);
        if (Files.exists(playPath)) {
            FileUtils.deleteFiles(playPath.toFile());
        }
        Assertions.assertTrue(Files.notExists(playPath));

        // To create directory
        Files.createDirectory(playPath);
        Assertions.assertTrue(Files.exists(playPath));

        // To create file
        IntStream.range(1, 10).forEach(i -> {
            Path tempFile = Paths.get(playPath + "/temp" + i);
            Assertions.assertTrue(Files.notExists(tempFile));
            try {
                Files.createFile(tempFile);
            } catch (IOException e) { }
            Assertions.assertTrue(Files.exists(tempFile));
        });

        // To list files
        Files.list(playPath).filter(Files::isRegularFile).forEach(System.out::println);
        Files.newDirectoryStream(playPath).forEach(System.out::println);
        Files.newDirectoryStream(playPath, path -> path.toFile().isFile() && path.toString().startsWith("temp"))
                .forEach(System.out::println);

    }

    @Test
    public void givenADirectory_WhenWatched_ShouldListAllTheActivities() throws IOException{
        Path dir = Paths.get(HOME + "/" + PLAY_WITH_IO);
        Files.list(dir).filter(Files::isRegularFile).forEach(System.out::println);
        new Java8WatchService(dir).processEvents();
    }
}

