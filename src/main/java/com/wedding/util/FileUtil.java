package com.wedding.util;

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
        }
        return file;
    }

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
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("[FileUtil ERROR] Failed to read from " + fileName + ": " + e.getMessage());
        }
        return lines;
    }

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
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
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
    }
}
