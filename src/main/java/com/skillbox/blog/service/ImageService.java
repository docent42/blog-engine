package com.skillbox.blog.service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.IllegalClassFormatException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.skillbox.blog.utils.RandomStringGenerator;

@Service
@Transactional
public class ImageService {


    public String uploadImage(MultipartFile uploadFile) throws IllegalClassFormatException {
        String uploadFileExtension = FilenameUtils.getExtension(uploadFile.getOriginalFilename());

        if (!uploadFileExtension.equals("jpg") &&
                !uploadFileExtension.equals("jpeg") &&
                !uploadFileExtension.equals("png")) {
            throw new IllegalClassFormatException("Illegal file format");
        }

        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("upload/")
                .append(RandomStringGenerator.randomString(10))
                .append(".")
                .append(uploadFileExtension);

        try (InputStream inputStream = uploadFile.getInputStream()) {
            Files.copy(inputStream, Paths.get(pathBuilder.toString()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pathBuilder.toString();
    }

}