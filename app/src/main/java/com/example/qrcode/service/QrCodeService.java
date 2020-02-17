package com.example.qrcode.service;

import com.example.qrcode.exception.DatabaseException;
import com.example.qrcode.exception.FileStorageException;
import com.example.qrcode.model.Base64DecodedMultipartFile;
import com.example.qrcode.model.DBFile;
import com.example.qrcode.model.QRcodeStatus;
import com.example.qrcode.repository.QRcodeStatusRepository;
import com.example.qrcode.utils.QRcodeUtils;
import com.google.zxing.WriterException;
import java.io.IOException;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class QrCodeService {

  @Autowired
  private QRcodeUtils qRcodeUtils;

  @Autowired
  private DBFileStorageService dbFileStorageService;

  @Autowired
  private QRcodeStatusRepository qRcodeStatusRepository;

  public String requestQrCodeUri(String uuid1)
      throws IOException, WriterException, FileStorageException {
    // create uuid
    String uuid2 = UUID.randomUUID().toString();
    // get QRcode
    byte[] qrcodeByteArray = qRcodeUtils.generate(uuid2);
    MultipartFile file = new Base64DecodedMultipartFile(qrcodeByteArray, uuid2 + "_qrcode", ".png");

    // store qrcode into db
    DBFile dbFile = dbFileStorageService.storeFile(file);
    // store uuid1 and uuid2 into db
    QRcodeStatus qRcodeStatus = new QRcodeStatus();
    qRcodeStatus.setUuid1(uuid1);
    qRcodeStatus.setUuid2(uuid2);
    qRcodeStatus.setStatus(false);
    qRcodeStatus.setQrCodeId(dbFile.getId());
    try {
      qRcodeStatusRepository.save(qRcodeStatus);
    } catch (Exception e) {
      throw new DatabaseException("uuid existed, please try again");
    }

    // return a url
    String qrCodeUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
        .path(dbFile.getId()).toUriString();
    return qrCodeUri;
  }

  public QRcodeStatus findQrCodeStatusByUuid1(String uuid1) {
    return qRcodeStatusRepository.findById(uuid1).orElseThrow(EntityNotFoundException::new);
  }

  public QRcodeStatus updateQrCodeStatus(String uuid2, String token) {
    QRcodeStatus qRcodeStatus = qRcodeStatusRepository.findByUuid2(uuid2);
    qRcodeStatus.setStatus(true);
    qRcodeStatus.setToken(token);
    qRcodeStatus = qRcodeStatusRepository.save(qRcodeStatus);
    return qRcodeStatus;
  }

  public Boolean deleteStatus(String uuid1) {
    try {
      qRcodeStatusRepository.deleteById(uuid1);
    } catch (Exception e) {
      throw new DatabaseException("fail to delete status");
    }
    return true;
  }
}
