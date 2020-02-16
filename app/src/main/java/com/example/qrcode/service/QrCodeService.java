package com.example.qrcode.service;

import com.example.qrcode.model.DBFile;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class QrCodeService {

  public String requestQrCodeUri(String uuid1){
    // create uuid
    String uuid2 = UUID.randomUUID().toString();
    // get QRcode

    // store into db

    // return a url
    String qrCodeUri = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/downloadFile/")
        .path(DBFile.getId())
        .toUriString();

  }
}
