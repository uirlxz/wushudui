package com.example.fms.service;

import com.example.fms.model.FileInfo;
import com.example.fms.model.vo.FileInfoVo;
import com.example.fms.model.vo.FileTypeVo;
import com.example.fms.utils.PageQuery;

import java.util.Date;
import java.util.List;

/**
 * Author: liuxingyu
 * DATE: 2017-07-17.17:11
 * description:
 * version:
 */
public interface FileService {
    FileInfo save(FileInfo fileInfo);

    List<FileTypeVo> getFileType() throws Exception;
//
    PageQuery getAllFileSign(int pageNo, int pageSize) throws Exception;
//
//
    PageQuery getAllFileSignByCondition(Long fileTypeId, Date startTime, Date endTime, int pageNo, int pageSize) throws Exception;

    void delFile(Long fileId);

    FileInfo getFileById(Long fileId);

    void signFile(String ids, String remark) throws Exception;

    List<FileInfoVo> getFileByIds(String fileIds) throws Exception;
}
