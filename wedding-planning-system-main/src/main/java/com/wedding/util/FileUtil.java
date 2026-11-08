package com.wedding.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    // Every data file lives in a folder called wedding-data inside the user's home folder
    private static final String DATA_FOLDER = System.getProperty("user.home") + File.separator + "wedding-data";

    // Returns the file, creating the folder and the file if they do not exist yet
    public static synchronized File getDataFile(String fileName) {
        File folder = new File(DATA_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(folder, fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create file " + fileName, e);
        }
        return file;
    }

    // Reads every non-empty line
    public static synchronized List<String> readLines(String fileName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getDataFile(fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read " + fileName, e);
        }
        return lines;
    }

    // Replaces the whole file with the given lines
    public static synchronized void writeLines(String fileName, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getDataFile(fileName)))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not write " + fileName, e);
        }
    }

    // The pipe character separates fields, so user input must never contain one
    public static String clean(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("|", "/").trim();
    }
}
