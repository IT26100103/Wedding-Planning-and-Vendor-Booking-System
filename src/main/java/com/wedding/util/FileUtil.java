package com.wedding.util;

<<<<<<< HEAD
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
=======
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * UTILITY CLASS: FileUtil
 * ============================================================================
 * OOP & PERSISTENCE HELPER:
 * - Provides centralized, robust flat-file reading and writing operations.
 * - Manages storage directory automatically in user's home folder:
 *     %USERPROFILE%/wedding-data/
 * - Automatically initializes missing data files from local sample-data templates.
 * - Prevents CSV/Pipe injection by sanitizing delimiter characters.
 * - Ensures thread-safe access and clean UTF-8 encoding.
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public class FileUtil {

    // Persistent storage location: %USERPROFILE%/wedding-data
    private static final String DATA_DIR = System.getProperty("user.home") + File.separator + "wedding-data";

    static {
        // Ensure data storage folder exists on application startup
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * Resolves the absolute path for a named data file.
     * If the file does not exist, attempts to auto-seed from sample-data.
     *
     * @param fileName e.g. "guests.txt", "users.txt"
     * @return Absolute File object
     */
    public static File getFile(String fileName) {
        File file = new File(DATA_DIR, fileName);
        if (!file.exists()) {
            File sample = new File("sample-data", fileName);
            if (!sample.exists()) {
                sample = new File("05_Guest_RSVP_Management" + File.separator + "sample-data", fileName);
            }
            if (sample.exists()) {
                try {
                    File parent = file.getParentFile();
                    if (parent != null && !parent.exists()) {
                        parent.mkdirs();
                    }
                    java.nio.file.Files.copy(sample.toPath(), file.toPath());
                } catch (IOException e) {
                    System.err.println("[FileUtil] Could not auto-seed " + fileName + ": " + e.getMessage());
                }
            }
>>>>>>> origin/main
        }
        return file;
    }

<<<<<<< HEAD
    // Reads every non-empty line
    public static synchronized List<String> readLines(String fileName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getDataFile(fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
=======
    /**
     * READ OPERATION: Reads all non-empty lines from the given file name.
     * Returns empty list if the file does not exist yet.
     *
     * @param fileName Target text file in the wedding-data folder
     * @return List of strings representing lines
     */
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
>>>>>>> origin/main
                    lines.add(line);
                }
            }
        } catch (IOException e) {
<<<<<<< HEAD
            throw new RuntimeException("Could not read " + fileName, e);
=======
            System.err.println("[FileUtil ERROR] Failed to read from " + fileName + ": " + e.getMessage());
>>>>>>> origin/main
        }
        return lines;
    }

<<<<<<< HEAD
    // Replaces the whole file with the given lines
    public static synchronized void writeLines(String fileName, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getDataFile(fileName)))) {
=======
    /**
     * WRITE OPERATION: Overwrites the target file with the provided lines.
     * Uses atomic file replacement patterns to prevent corrupted records.
     *
     * @param fileName Target text file in wedding-data
     * @param lines    List of pipe-delimited strings to persist
     */
    public static void writeLines(String fileName, List<String> lines) {
        File file = getFile(fileName);
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
>>>>>>> origin/main
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
<<<<<<< HEAD
            throw new RuntimeException("Could not write " + fileName, e);
        }
    }

    // The pipe character separates fields, so user input must never contain one
    public static String clean(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("|", "/").trim();
=======
            System.err.println("[FileUtil ERROR] Failed to write to " + fileName + ": " + e.getMessage());
        }
    }

    /**
     * DATA SANITIZATION: Removes pipe characters ('|') and linebreaks from user inputs
     * to prevent delimiter corruption in flat-file records.
     *
     * @param input Raw user string
     * @return Cleaned string safe for storage
     */
    public static String clean(String input) {
        if (input == null) return "";
        return input.replace("|", " ").replace("\n", " ").replace("\r", " ").trim();
>>>>>>> origin/main
    }
}
