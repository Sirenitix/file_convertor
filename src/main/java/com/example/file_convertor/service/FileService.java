package com.example.file_convertor.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface FileService {

    String createZip(List<MultipartFile> files, String zipName) throws IOException;

}
