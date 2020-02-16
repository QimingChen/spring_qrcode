# spring_qrcode


## UML sequence diagram
![uml_sequence_diagram](https://github.com/QimingChen/spring_qrcode/blob/master/readme_files/QRcode_login.png)

## feature breakdown

* /request-qrcode?id={uuid1} : webpage create a uuid1 (identify web user) return qrcode with a uuid2 (identify the internal object) encoded

* /check-uuid={uuid1} : webpage can check with uuid1 to know if uuid2 has been used for logging in, return nothing or a token

* /login : phone login with credential, response has a token

* /verify-qrcode?id={uuid2} : uuid2 is extracted by scanning qrcode, server updates the manipulation, so the next request from webpage request to /check-uuid={uuid1} will get a token, the webpage can save the token to upcoming requests



# credit

https://backendless.com/how-to-implement-mobile-to-web-cross-login-using-a-qr-code/

https://aboullaite.me/generate-qrcode-with-logo-image-using-zxing/
