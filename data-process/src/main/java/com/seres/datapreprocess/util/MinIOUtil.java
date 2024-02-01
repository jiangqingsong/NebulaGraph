package com.seres.datapreprocess.util;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/1 10:51
 */
public class MinIOUtil {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String bukect = "s-jqs";
        String localPath = "D:\\seres\\知识图谱\\MinIO\\test\\多模态知识图谱构建与应用.pdf";
        String targetName = "多模态知识图谱构建与应用.pdf";
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("https://play.min.io")
                            .credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG")
                            .build();

            // Make 'asiatrip' bucket if not exist.
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bukect).build());
            if (!found) {
                // Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bukect).build());
            } else {
                System.out.println("Bucket 'asiatrip' already exists.");
            }

            // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
            // 'asiatrip'.
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bukect)
                            .object(targetName)
                            .filename(localPath)
                            .build());
            System.out.println(
                    localPath + "is successfully uploaded as "
                            + "object " + targetName + " to bucket " + bukect + ".");
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }
    }

    public static MinioClient getClient(){

        return null;
    }
}
