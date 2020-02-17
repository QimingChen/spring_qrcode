package com.example.qrcode.repository;

import com.example.qrcode.model.QRcodeStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QRcodeStatusRepository extends CrudRepository<QRcodeStatus, String> {

  @Query("FROM QRcodeStatus WHERE uuid1 = :uuid1")
  public QRcodeStatus findByUuid1(@Param("uuid1") String uuid1);

  @Query("FROM QRcodeStatus WHERE uuid2 = :uuid2")
  public QRcodeStatus findByUuid2(@Param("uuid2") String uuid2);
}
