package com.example.fms.repository;

import com.example.fms.model.FileType;

import java.util.List;

/**
 * Author: liuxingyu
 * DATE: 2017-07-19.14:38
 * description:
 * version:
 */
public interface FileTypeRepo  extends BaseRepo<FileType>{
    List<FileType> findByStatus(int yesFileType);
}
