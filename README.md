# spring_qrcode


## UML sequence diagram
![uml_sequence_diagram](https://github.com/QimingChen/spring_qrcode/blob/master/readme_files/QRcode_login.png)

## feature breakdown

* /request-qrcode: webpage sends a request, server creates a uuid1 and a qrcode with a uuid2 (identify the internal object) encoded, return uuid1 and the address to locate the qrcode

```d9706def-3638-4b40-aa04-0aeb9dfd7c4e http://localhost:8080/downloadFile/33a46dce-042c-4856-bd80-4034445f3845```

* /check-uuid={uuid1} : webpage can check with uuid1 to know if uuid2 has been used for logging in, return false or the token

* /login : phone login with credential, response has a token

* /verify-qrcode/uuid={uuid2}&token={token}: uuid2 is extracted by scanning qrcode, server receives the uuid2 and the token, uuid2 is linked with uuid1, so it can get knowledge of which browser user it is handling. By updating the token field in database, so the next request from webpage request to /check-uuid={uuid1} will get a token, the webpage can save the token to upcoming requests

[sample_qrcode]()


# credit

https://backendless.com/how-to-implement-mobile-to-web-cross-login-using-a-qr-code/

https://aboullaite.me/generate-qrcode-with-logo-image-using-zxing/
