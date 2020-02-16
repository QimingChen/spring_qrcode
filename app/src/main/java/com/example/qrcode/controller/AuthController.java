package com.example.qrcode.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  @PostMapping(value = "/login")
  public ResponseEntity<String> login(){
    return new ResponseEntity<String>("hellotoken", HttpStatus.OK);
  }
}
