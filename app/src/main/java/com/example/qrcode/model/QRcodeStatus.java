package com.example.qrcode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="qrcode_status")
@Getter
@Setter
@NoArgsConstructor
public class QRcodeStatus {

  @Id
  @Column
  private String uuid1;

  @Column
  private String uuid2;

  @Column
  private boolean status;
}
