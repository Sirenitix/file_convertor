package com.example.file_convertor.service.impl;

import com.example.file_convertor.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private final StringBuilder fileNames = new StringBuilder();

    @Override
    public String createZip(List<MultipartFile> files, String zipName) throws IOException {
        try (ZipOutputStream out =
                     new ZipOutputStream(
                             new FileOutputStream(System.getProperty("user.dir") + "/src/main/resources/static/" + zipName + ".zip")))
        {
            for (MultipartFile file : files) {
                try (InputStream in = file.getInputStream()){
                    fileNames.append(" ").append(file.getOriginalFilename());
                    log.info(file.getOriginalFilename() + " - file name");
                    out.putNextEntry(new ZipEntry(Objects.requireNonNull(file.getOriginalFilename())));
                    byte[] b = new byte[1024];
                    int count;
                    while ((count = in.read(b, 0, 1024)) > 0) out.write(b, 0, count);
                    out.closeEntry();
                }
            }
        }
        fileNames.append(" - ").append("this files compressed to").append(" ").append(zipName);
        return fileNames.toString();
    }
}
