package com.example.qrcode.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.overviewproject.mime_types.MimeTypeDetector;
import org.springframework.web.multipart.MultipartFile;

public class Base64DecodedMultipartFile implements MultipartFile {

  protected static final Logger log = LogManager.getLogger(Base64DecodedMultipartFile.class);

  private MimeTypeDetector mimeTypeDetector = new MimeTypeDetector();
  private byte[] imgContent;
  private String fileName;
  private String ext;

  public Base64DecodedMultipartFile(byte[] imgContent, String fileName, String ext) {
    this.imgContent = imgContent;
    this.fileName = fileName;
    this.ext = ext;
  }

  public String getExt() {
    return ext;
  }

  @Override
  public String getName() {
    return fileName;
  }

  @Override
  public String getOriginalFilename() {
    return fileName;
  }

  @SneakyThrows
  @Override
  public String getContentType() {
    if(getExt() == null) {
      return null;
    }
    InputStream stream = new ByteArrayInputStream(imgContent);
    String mimeType = mimeTypeDetector.detectMimeType(fileName, stream);
    ext = mimeType;
    return mimeType;
  }

  @Override
  public boolean isEmpty() {
    return imgContent == null || imgContent.length == 0;
  }

  @Override
  public long getSize() {
    return imgContent.length;
  }

  @Override
  public byte[] getBytes() throws IOException {
    return imgContent;
  }

  @Override
  public InputStream getInputStream() throws IOException {
    return new ByteArrayInputStream(imgContent);
  }

  @Override
  public void transferTo(File dest) throws IOException {
    try (FileOutputStream f = new FileOutputStream(dest)) {
      f.write(imgContent);
    }
  }
}