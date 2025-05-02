package com.pubsub.process.controller;


import com.google.gson.Gson;
import com.pubsub.process.model.userData;
import com.pubsub.process.utill.EncryptDecryptUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class EnDecryptController {


    @Autowired
    EncryptDecryptUtil encryptDecryptUtil;
    @GetMapping("/encrypt")
    public String encriptData(userData req) {
        String request = new Gson().toJson(req);
        try {
            return encryptDecryptUtil.encryptData(request);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    @GetMapping("/decrypte")
    public String decriptData(@RequestParam(name = "decreq") String req) {
        try {
            return encryptDecryptUtil.decript(req);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }


}
