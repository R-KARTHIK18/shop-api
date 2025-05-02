package com.pubsub.process.controller;

import com.pubsub.process.service.pubsubsender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class pubsub {

    @Autowired
    pubsubsender.Outboundchannel outboundchannel;
    @PostMapping("/sendmsg")
    public  String sendmessage(@RequestParam (required = false,name = "Message") String message){
        outboundchannel.sendtoPubsub(message);

        return  "Message sent to pubsub";
    }

}
