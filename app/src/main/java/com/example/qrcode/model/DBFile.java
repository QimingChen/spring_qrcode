package com.example.qrcode.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "mediafiles")
@Getter
@Setter
@NoArgsConstructor
public class DBFile {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  private String fileName;

  private String fileType;

  @Lob
  private byte[] data;

  public DBFile(String fileName, String fileType, byte[] data) {
    this.fileName = fileName;
    this.fileType = fileType;
    this.data = data;
  }
}