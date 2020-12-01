/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadFile(String bucketName, MultipartFile file) throws IOException;
    String getFileUrl(String bucketName, String fileName);
    void createBucket(String bucketName);
    public boolean saveFile(MultipartFile multipartFile, String filePath);
    public boolean isFileExist(String fileFullName);
    public boolean isPathExist(String filePath);
}
