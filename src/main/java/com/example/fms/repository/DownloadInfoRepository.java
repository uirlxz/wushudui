package com.example.fms.repository;

import com.example.fms.model.DownloadInfoEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by www on 2016/8/26.
 */
public interface  DownloadInfoRepository extends CrudRepository<DownloadInfoEntity, Long> {
    DownloadInfoEntity findOneByUuid(String uuid);
}
