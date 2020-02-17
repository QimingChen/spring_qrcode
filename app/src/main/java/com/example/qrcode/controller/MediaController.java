package com.example.qrcode.controller;

import com.example.qrcode.exception.MyFileNotFoundException;
import com.example.qrcode.model.DBFile;
import com.example.qrcode.service.DBFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MediaController {

  @Autowired
  private DBFileStorageService dbFileStorageService;

  @GetMapping("/downloadFile/{fileId}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileId)
      throws MyFileNotFoundException {
    // Load file from database
    DBFile dbFile = dbFileStorageService.getFile(fileId);

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(dbFile.getFileType()))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
        .body(new ByteArrayResource(dbFile.getData()));
  }
}
