package com.example.qrcode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
  @Column(unique = true)
  @NotNull
  private String uuid1;

  @Column(unique = true)
  @NotNull
  private String uuid2;

  @Column
  private String qrCodeId;

  @Column
  private Boolean status;

  @Column
  private String token;
}
