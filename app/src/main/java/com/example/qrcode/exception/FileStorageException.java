package com.example.qrcode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileStorageException extends Exception {

  public FileStorageException(String msg) {
    super(msg);
  }

  public FileStorageException(String msg, Exception ex) {
    super(msg, ex);
  }
}
