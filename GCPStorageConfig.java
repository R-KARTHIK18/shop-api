package com.pubsub.process.config;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GCPStorageConfig {


    @Value("${gcp.project.id}")
    private String gcpProjectId;
    @Bean
    public Storage storage() {
        return StorageOptions.newBuilder().setProjectId(gcpProjectId).build().getService();
    }


}
