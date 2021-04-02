package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.SimpleFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SimpleFileMapper {
    @Select("SELECT * FROM FILES WHERE fileid = #{fileId} AND userid = #{userId}")
    SimpleFile getFileById(Integer fileId, Integer userId);

    @Select("SELECT * FROM FILES WHERE filename = #{fileName} AND userid = #{userId}")
    SimpleFile getFileByFileName(String fileName, Integer userId);

    @Select("SELECT * FROM FILES WHERE filename = #{fileName} AND userid = #{userId}")
    List<SimpleFile> getFileByFileNameAndUserId(String fileName, Integer userId);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<SimpleFile> getAllFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    SimpleFile downloadFile(Integer fileId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int uploadFile(SimpleFile file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    void deleteFile(Integer fileId);



}
