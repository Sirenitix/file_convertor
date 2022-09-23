package com.example.file_convertor.controller;

import com.example.file_convertor.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController("/zipFiles")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @RequestMapping(method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> zipFiles(@RequestParam List<MultipartFile> files,@RequestParam String zipName) throws IOException {
        return ResponseEntity.ok(fileService.createZip(files, zipName));
    }



}
