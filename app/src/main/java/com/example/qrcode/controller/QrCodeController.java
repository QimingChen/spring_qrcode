package com.example.qrcode.controller;

import com.example.qrcode.exception.FileStorageException;
import com.example.qrcode.exception.MyFileNotFoundException;
import com.example.qrcode.model.DBFile;
import com.example.qrcode.model.QRcodeStatus;
import com.example.qrcode.service.DBFileStorageService;
import com.example.qrcode.service.QrCodeService;
import com.google.zxing.WriterException;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class QrCodeController {

  @Autowired
  private DBFileStorageService dBFileStorageService;

  @Autowired
  private QrCodeService qrCodeService;

  @PostMapping("/request-qrcode")
  public String requestQrCode() throws FileStorageException, IOException, WriterException {
    String uuid1 = UUID.randomUUID().toString();
    String qrCodeUri = qrCodeService.requestQrCodeUri(uuid1);
    return uuid1 + " " + qrCodeUri;
  }

  @GetMapping("/check-uuid={uuid1}")
  public ResponseEntity<?> checkQrCodeStatus(@PathVariable String uuid1) {
    QRcodeStatus status = qrCodeService.findQrCodeStatusByUuid1(uuid1);
    if (status.getStatus()) {
      String dbFileId = status.getQrCodeId();
      if (dBFileStorageService.deleteFile(dbFileId) && qrCodeService
          .deleteStatus(status.getUuid1())) {
        return new ResponseEntity<String>(status.getToken(), HttpStatus.OK);
      }
    }
    return new ResponseEntity<Boolean>(status.getStatus(), HttpStatus.OK);
  }

  @PutMapping("/verify-qrcode/uuid={uuid2}&token={token}")
  public String verifyQrCodeStatus(@PathVariable String uuid2, @PathVariable String token) {
    try {
      qrCodeService.updateQrCodeStatus(uuid2, token);
    } catch (Exception e) {
      return "failure";
    }
    return "success";
  }
}