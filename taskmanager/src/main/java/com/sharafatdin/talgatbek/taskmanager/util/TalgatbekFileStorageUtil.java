package com.sharafatdin.talgatbek.taskmanager.util;

import com.sharafatdin.talgatbek.taskmanager.exception.TalgatbekFileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

/** @author Talgatbek Sharafatdin */
@Component
@Slf4j
public class TalgatbekFileStorageUtil {

    private final Path uploadDir;

    public TalgatbekFileStorageUtil(@Value("${app.upload.dir:./uploads}") String uploadDirPath) {
        this.uploadDir = Paths.get(uploadDirPath).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.uploadDir);
            log.info("Upload directory: {}", this.uploadDir);
        } catch (IOException e) {
            throw new TalgatbekFileStorageException("Could not create upload directory", e);
        }
    }

    public String storeFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String storedName = UUID.randomUUID() + extension;
        try {
            Path targetLocation = uploadDir.resolve(storedName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("Stored file: {} as {}", originalFilename, storedName);
            return storedName;
        } catch (IOException e) {
            throw new TalgatbekFileStorageException("Failed to store file: " + originalFilename, e);
        }
    }

    public Path loadFile(String storedName) {
        return uploadDir.resolve(storedName).normalize();
    }

    public void deleteFile(String storedName) {
        try {
            Path filePath = uploadDir.resolve(storedName).normalize();
            Files.deleteIfExists(filePath);
            log.info("Deleted file: {}", storedName);
        } catch (IOException e) {
            log.error("Failed to delete file: {}", storedName, e);
        }
    }

    public String getUploadDir() {
        return uploadDir.toString();
    }
}
