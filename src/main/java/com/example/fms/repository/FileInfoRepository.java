package com.example.fms.repository;

import com.example.fms.model.FileInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by www on 2016/9/10.
 */
public interface  FileInfoRepository extends CrudRepository<FileInfo, Long> {
    FileInfo findOneByUuid(String uuid);
}
