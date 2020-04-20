package com.skillbox.blog.service;

import com.skillbox.blog.config.StorageConfig;
import com.skillbox.blog.exception.StorageException;
import com.skillbox.blog.utils.RandomStringGenerator;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.IllegalClassFormatException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ImageService {

  private final Path location;

  @Autowired
  public ImageService(StorageConfig storageConfig) {
    this.location = Paths.get(storageConfig.getLocation());
  }

  public String uploadImage(MultipartFile uploadFile) throws IllegalClassFormatException {
    StringBuilder pathBuilder = new StringBuilder();
    try {
      String uploadFileExtension = FilenameUtils.getExtension(uploadFile.getOriginalFilename());

      if (!uploadFileExtension.equals("jpg") &&
          !uploadFileExtension.equals("jpeg") &&
          !uploadFileExtension.equals("png")) {
        throw new IllegalClassFormatException("Illegal file format");
      }

      pathBuilder
          .append(RandomStringGenerator.randomString(10))
          .append(".")
          .append(uploadFileExtension);

      Path file = this.location.resolve(pathBuilder.toString());

      try (InputStream inputStream = uploadFile.getInputStream()) {
        Files.copy(inputStream, file,
            StandardCopyOption.REPLACE_EXISTING);
      }
      return "/" + file;
    } catch (IOException e) {
      throw new StorageException("Failed to store file " + pathBuilder, e);
    }
  }

  public void init() {
    try {
      Files.createDirectories(location);
    } catch (IOException e) {
      throw new StorageException("Could not initialize storage", e);
    }
  }

}