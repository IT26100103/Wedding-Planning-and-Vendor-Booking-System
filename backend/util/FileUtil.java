package com.wedding.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    // Persistent storage location
    private static final String DATA_DIR = System.getProperty("user.home") + File.separator + "wedding-data";

    static {
        // Ensure data storage folder exists on application startup
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    //Resolves the absolute path for a named data file.
    public static File getFile(String fileName) {
        return new File(DATA_DIR, fileName);
    }
    
    
    public static List<String> readLines(String fileName) {
        List<String> lines = new ArrayList<>();
        File file = getFile(fileName);
        if (!file.exists()) {
            return lines;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("[FileUtil ERROR] Failed to read from " + fileName + ": " + e.getMessage());
        }
        return lines;
    }
    
    
    public static void writeLines(String fileName, List<String> lines) {
        File file = getFile(fileName);
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("[FileUtil ERROR] Failed to write to " + fileName + ": " + e.getMessage());
        }
    }
    
    public static String clean(String input) {
        if (input == null) return "";
        return input.replace("|", " ").replace("\n", " ").replace("\r", " ").trim();
    }
}
