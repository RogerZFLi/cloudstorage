package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.SimpleFileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.SimpleFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class SimpleFileService {
    private SimpleFileMapper simpleFileMapper;
    final UserService userService;

    public SimpleFileService(SimpleFileMapper simpleFileMapper, UserService userService) {
        this.simpleFileMapper = simpleFileMapper;
        this.userService = userService;
    }

    public boolean fileExists(MultipartFile file, Integer userId){
        return simpleFileMapper.getFileByFileName(file.getOriginalFilename(),userId)!=null;
    }

    public List<SimpleFile> getAllFiles(Integer userId) {
        return simpleFileMapper.getAllFiles(userId);
    }

    public SimpleFile getFileById(Integer fileId, Integer userId) {
        return simpleFileMapper.getFileById(fileId,userId);
    }

    public int uploadFile(MultipartFile file, Integer userId) {
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        String fileSize = String.valueOf(file.getSize());
        byte[] fileData = null;
        try {
            fileData = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return simpleFileMapper.uploadFile(new SimpleFile(null, fileName,contentType, fileSize, userId, fileData));
    }

    public SimpleFile downloadFile(Integer fileId) {
        return simpleFileMapper.downloadFile(fileId);
//        try {
//            InputStream is = fileMapper.downloadFile(fileId).getFileData().getBinaryStream();
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
    }

    public void deleteFile(Integer fileId) {
        simpleFileMapper.deleteFile(fileId);
    }


}
