package com.stardevllc.starlib.helper;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * A collection of file utilities
 */
public final class FileHelper {
    private FileHelper() {
    }
    
    /**
     * Renames a file
     *
     * @param file    The file
     * @param newName The new name and extension
     */
    public static void renameFile(Path file, String newName) {
        Path parent = file.toAbsolutePath().getParent();
        Path newPath = subPath(parent, newName);
        try {
            Files.move(file, newPath, REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Creates a file if it does not exist
     *
     * @param file The file
     */
    public static void createFileIfNotExists(File file) {
        createFileIfNotExists(file.toPath());
    }
    
    /**
     * Creates a file if it does not exist
     *
     * @param path The file
     */
    public static void createFileIfNotExists(Path path) {
        createDirectoryIfNotExists(path.getParent());
        
        if (Files.notExists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Creates a subpath from the parent and the children
     *
     * @param parent The parent
     * @param child  The child nodes after the parent
     * @return The new end file
     */
    public static File subPath(File parent, String... child) {
        return subPath(parent.toPath(), child).toFile();
    }
    
    /**
     * Creates a sub path from the parent and the children
     *
     * @param parent The parent
     * @param child  The child nodes after the parent
     * @return The new path
     */
    public static Path subPath(Path parent, String... child) {
        return FileSystems.getDefault().getPath(parent.toString(), child);
    }
    
    /**
     * Creates the director if it does not exist
     *
     * @param file The directory
     */
    public static void createDirectoryIfNotExists(File file) {
        createDirectoryIfNotExists(file.toPath());
    }
    
    /**
     * Creates the director if it does not exist
     *
     * @param path The directory
     */
    public static void createDirectoryIfNotExists(Path path) {
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Downloads a file
     *
     * @param downloadUrl The url to download from
     * @param downloadDir The download directory to use
     * @param fileName    The file to download to
     * @param userAgent   The useragent to use downloading
     * @return The file that was downloaded
     */
    public static File downloadFile(String downloadUrl, File downloadDir, String fileName, boolean userAgent) {
        return downloadFile(downloadUrl, downloadDir.toPath(), fileName, userAgent).toFile();
    }
    
    /**
     * Downloads a file
     *
     * @param downloadUrl The url to download from
     * @param downloadDir The download directory to use
     * @param fileName    The file to download to
     * @param userAgent   The useragent to use downloading
     * @return The file that was downloaded
     */
    public static Path downloadFile(String downloadUrl, Path downloadDir, String fileName, boolean userAgent) {
        try {
            Path targetFile = FileSystems.getDefault().getPath(downloadDir.toString(), fileName);
            if (downloadUrl.startsWith("file://")) {
                Files.copy(Path.of(downloadUrl.replace("file://", "")), targetFile, REPLACE_EXISTING);
            } else {
                URL url = new URI(downloadUrl).toURL();
                Path tmpFile = FileSystems.getDefault().getPath(downloadDir.toString(), fileName + ".tmp");
                if (Files.exists(tmpFile)) {
                    Files.delete(tmpFile);
                }
                Files.createFile(tmpFile);
                URLConnection connection = url.openConnection();
                if (userAgent) {
                    connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
                }
                try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream()); FileOutputStream out = new FileOutputStream(tmpFile.toFile())) {
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = in.read(buffer, 0, 1024)) != -1) {
                        out.write(buffer, 0, read);
                    }
                }
                
                Files.move(tmpFile, targetFile, REPLACE_EXISTING);
            }
            return targetFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Recursive copy the folder
     *
     * @param src  The source folder
     * @param dest The destination folder
     */
    public static void copyFolder(File src, File dest) {
        copyFolder(src.toPath(), dest.toPath());
    }
    
    /**
     * Recursive copy the folder
     *
     * @param src  The source folder
     * @param dest The destination folder
     */
    public static void copyFolder(Path src, Path dest) {
        try {
            Files.walkFileTree(src, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                        throws IOException {
                    Files.createDirectories(dest.resolve(src.relativize(dir)));
                    return FileVisitResult.CONTINUE;
                }
                
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                        throws IOException {
                    Files.copy(file, dest.resolve(src.relativize(file)), REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Copy files
     *
     * @param source The source file
     * @param dest   The destination file
     */
    public static void copy(File source, File dest) {
        copy(source.toPath(), dest.toPath());
    }
    
    /**
     * Copy files
     *
     * @param source The source file
     * @param dest   The destination file
     */
    public static void copy(Path source, Path dest) {
        try {
            Files.copy(source, dest, REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    /**
     * Recursive delete a directory
     *
     * @param directory The directory
     */
    public static void deleteDirectory(File directory) {
        deleteDirectory(directory.toPath());
    }
    
    /**
     * Recursive delete a directory
     *
     * @param directory The directory
     */
    public static void deleteDirectory(Path directory) {
        try {
            Files.walkFileTree(directory, new SimpleFileVisitor<>() {
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }
                
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Exception e) {
            String osName = System.getProperty("os.name").toLowerCase();
            String[] cmd;
            if (osName.contains("windows")) {
                cmd = new String[]{"powershell.exe", "Remove-Item", "-Path", "'" + directory.toAbsolutePath() + "'", "-r", "-fo"};
            } else if (osName.contains("ubuntu") || osName.contains("linux")) {
                cmd = new String[]{"rm", "-rf", directory.toAbsolutePath().toString()};
            } else {
                cmd = null;
            }
            
            try {
                Process process = new ProcessBuilder().command(cmd).start();
                process.waitFor();
                process.getOutputStream().close();
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Unzips a file
     *
     * @param zipFile     The zip file
     * @param destination The destination folder
     */
    public static void unzipFile(Path zipFile, Path destination) {
        byte[] buffer = new byte[1024];
        try {
            ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFile));
            ZipEntry zipEntry = zis.getNextEntry();
            
            while (zipEntry != null) {
                Path newFile = newFileFromZip(destination, zipEntry);
                if (zipEntry.isDirectory()) {
                    FileHelper.createDirectoryIfNotExists(newFile);
                } else {
                    // fix for Windows-created archives
                    Path parent = newFile.getParent();
                    if (!Files.isDirectory(parent)) {
                        FileHelper.createDirectoryIfNotExists(parent);
                        if (Files.notExists(parent)) {
                            throw new IOException("Failed to create directory " + parent);
                        }
                    }
                    
                    // write file content
                    FileOutputStream fos = new FileOutputStream(newFile.toFile());
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static Path newFileFromZip(Path destinationDir, ZipEntry zipEntry) throws IOException {
        Path path = FileHelper.subPath(destinationDir, zipEntry.getName());
        String destDirPath = destinationDir.toFile().getCanonicalPath();
        String destFilePath = path.toFile().getCanonicalPath();
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        } else {
            return path;
        }
    }
}