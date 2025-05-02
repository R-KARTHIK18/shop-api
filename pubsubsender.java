package com.pubsub.process.service;


import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.AckMode;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.Header;


@Configuration

public class pubsubsender {



    @MessagingGateway(name = "myPubSubGateway",defaultRequestChannel = "outboundmsgchannel")
    public interface Outboundchannel {
        //4
        void sendtoPubsub(String msg);

    }

    //5
    @Bean
    @ServiceActivator(inputChannel = "outboundmsgchannel")
    public PubSubMessageHandler messagesend(PubSubTemplate pubSubTemplate) {
        return new PubSubMessageHandler(pubSubTemplate, "ayhtin");
    }


//6. create channel

    @Bean
    MessageChannel pubsubInputChannel() { return new DirectChannel(); }

    @Bean
    MessageChannel outboundmsgchannel() { return new DirectChannel(); }
    // 7 create recevicer adapter

@Bean
    public PubSubInboundChannelAdapter inboundChannelAdapter(
            @Qualifier("pubsubInputChannel") MessageChannel messageChannel,
            PubSubTemplate pubSubTemplate){
        PubSubInboundChannelAdapter adapter=new PubSubInboundChannelAdapter(pubSubTemplate,"ayhtin-sub");
        adapter.setOutputChannel(messageChannel);
    adapter.setAckMode(AckMode.MANUAL);
    adapter.setErrorChannelName("handleerrors");
         return adapter;

    }

    //msg recevier

    @ServiceActivator(inputChannel = "pubsubInputChannel")
    public void messageReceiver(
            String payload,
            @Header (GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        String messageId =message.getPubsubMessage().getMessageId();
        System.out.println("logssssss  --  :"+payload);



    }


    @ServiceActivator(inputChannel =  "handleerrors")
    public void pubsubErrorHandler(Message<MessagingException> exceptionMessage) {
        System.out.println("This message will be automatically acked because error handler completes successfully");
        BasicAcknowledgeablePubsubMessage originalMessage =
                (BasicAcknowledgeablePubsubMessage)exceptionMessage.getPayload().getFailedMessage()
                        .getHeaders().get(GcpPubSubHeaders.ORIGINAL_MESSAGE);
        System.out.println("PubSub Error: " + originalMessage.getPubsubMessage());
    }
}
