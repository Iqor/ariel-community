package com.igor.amazon;

// Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0 (For details, see https://github.com/awsdocs/amazon-s3-developer-guide/blob/master/LICENSE-SAMPLECODE.)

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.igor.entity.Model;
import com.igor.entity.User;
import com.igor.utils.Patterns;
import com.igor.utils.Utils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class UploadObject {

    public static void upload(Model model, User user, File file) {

        String bucketName = System.getenv("S3_BUCKET_NAME") + "/" + user.getId();
        String fileObjKeyName = model.getFileHash();


        AWSCredentials credentials = new BasicAWSCredentials(
                System.getenv("AWS_ACCESS_KEY_ID"),
                System.getenv("AWS_SECRET_ACCESS_KEY")
        );

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.SA_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .build();

            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, file);
            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentType();
//            metadata.addUserMetadata("x-amz-meta-title", "someTitle");
            request.setMetadata(metadata);
            s3Client.putObject(request);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }


    public static int saveImage(Model model, Long userId, File file) {

        try {

            File destFile = new File(Patterns.IMAGES_PATH_LOCAL + Patterns.SLASH + userId + Patterns.SLASH);
            FileUtils.copyFileToDirectory(file, destFile);
            model.setFilePath(Patterns.IMAGES_PATH_LOCAL + Patterns.SLASH + userId + Patterns.SLASH + model.getFileHash());

        } catch (IllegalStateException | IOException ex) {
            Utils.showErrors(ex);
        }
        return 0;
    }


}
