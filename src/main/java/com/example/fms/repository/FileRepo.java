package com.example.fms.repository;

import com.alibaba.fastjson.JSONArray;
import com.example.fms.model.FileInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Author: liuxingyu
 * DATE: 2017-07-17.17:12
 * description:
 * version:
 */
public interface FileRepo extends BaseRepo<FileInfo>{
    Page<FileInfo> findByStatus(int yesFileSign, Pageable page);

    FileInfo findById(Long fileId);

    FileInfo findByUuid(String uuid);

    List<FileInfo> findByIdIn(JSONArray jsonArray);
}
