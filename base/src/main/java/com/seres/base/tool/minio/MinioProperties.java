package com.seres.base.tool.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    private String url = "http://localhost:9000";
    private String accessKey = "minioadmin";
    private String secretKey = "minioadmin";
    private String bucketName = "public";
}