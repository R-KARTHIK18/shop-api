package com.pubsub.process.controller;

import com.pubsub.process.GCPStorage.GcpStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GCPFilecontoller {

    @Autowired
    GcpStorage gcpStorage;

    @GetMapping("/getfiles")
    public List<String>getallfiles(){

        return  gcpStorage.listOfFiles();
    }
}
