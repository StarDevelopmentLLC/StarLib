package com.stardevllc.starlib.helper;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public final class FileHelper {
    public static void createFileIfNotExists(File file) {
        createFileIfNotExists(file.toPath());
    }
    
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
    
    public static File subPath(File parent, String... child) {
        return subPath(parent.toPath(), child).toFile();
    }

    public static Path subPath(Path parent, String... child) {
        return FileSystems.getDefault().getPath(parent.toString(), child);
    }

    public static void createDirectoryIfNotExists(File file) {
        createDirectoryIfNotExists(file.toPath());
    }
    
    public static void createDirectoryIfNotExists(Path path) {
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static File downloadFile(String downloadUrl, File downloadDir, String fileName, boolean userAgent) {
        return downloadFile(downloadUrl, downloadDir.toPath(), fileName, userAgent).toFile();
    }

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
    
    public static void copyFolder(File src, File dest) {
        copyFolder(src.toPath(), dest.toPath());
    }

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
    
    public static void copy(File source, File dest) {
        copy(source.toPath(), dest.toPath());
    }

    public static void copy(Path source, Path dest) {
        try {
            Files.copy(source, dest, REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public static void deleteDirectory(File directory) {
        deleteDirectory(directory.toPath());
    }

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