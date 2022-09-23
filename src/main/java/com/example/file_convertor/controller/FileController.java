package com.example.file_convertor.controller;

import com.example.file_convertor.entity.Game;
import com.example.file_convertor.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @RequestMapping(value = "/filesToZip",method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> zipFiles(@RequestParam List<MultipartFile> files, @RequestParam(defaultValue = "file") String zipName) throws IOException {
        return ResponseEntity.ok(fileService.createZip(files, zipName));
    }

    @RequestMapping(value = "/objectToJson",method = RequestMethod.POST)
    public ResponseEntity<?> convertObjectToJson(@RequestBody Game game) {
        return ResponseEntity.ok(fileService.convertToJson(game));
    }

}
