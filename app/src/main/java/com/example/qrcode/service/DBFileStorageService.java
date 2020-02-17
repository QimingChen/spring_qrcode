package com.example.qrcode.service;

import com.example.qrcode.exception.DatabaseException;
import com.example.qrcode.exception.FileStorageException;
import com.example.qrcode.exception.MyFileNotFoundException;
import com.example.qrcode.model.DBFile;
import com.example.qrcode.repository.DBFileRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DBFileStorageService {

  @Autowired
  private DBFileRepository dBFileRepository;

  public DBFile storeFile(MultipartFile file) throws FileStorageException {
    // Normalize file name
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());

    try {
      // Check if the file's name contains invalid characters
      if (fileName.contains("..")) {
        throw new FileStorageException(
            "Sorry! Filename contains invalid path sequence " + fileName);
      }

      DBFile DBFile = new DBFile(fileName, file.getContentType(), file.getBytes());

      return dBFileRepository.save(DBFile);
    } catch (IOException | FileStorageException ex) {
      throw new FileStorageException("Could not store file " + fileName + ". Please try again!",
          ex);
    }
  }

  public DBFile getFile(String fileId) throws MyFileNotFoundException {
    return dBFileRepository.findById(fileId)
        .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
  }

  public Boolean deleteFile(String fileId){
    try {
      dBFileRepository.deleteById(fileId);
    }catch(Exception e){
      throw new DatabaseException("fail to delete file");
    }
    return true;
  }
}
