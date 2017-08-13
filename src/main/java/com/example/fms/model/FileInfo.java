package com.example.fms.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: liuxingyu
 * DATE: 2017-07-17.15:10
 * description:文件
 * version:
 */
@Entity
@Table(name = "t_file_info")
public class FileInfo extends BaseModel{

    /**
     * 文件路径
     */
    private String path;
    @Column(name="createTime")
    private Date createTime;

    private String uuid;
    @Column(name="fileName")
    private String fileName;

    private String  comments;

    private Integer status = 0;
    @OneToOne
    private FileType fileType;


    public String getRemark() {
        return comments;
    }

    public void setRemark(String remark) {
        this.comments = remark;
    }



    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getTime() {
        return createTime;
    }

    public void setTime(Date time) {
        this.createTime = time;

    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
}
