package com.example.fms.model.vo;

import java.util.Date;

/**
 * Created by liu on 2017/7/24.
 */
public class FileInfoVo extends BaseVo{
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String path;
    /**
     * 添加时间
     */
    private Date time;

    /**
     * 文件唯一id
     */
    private String uuid;

    /**
     * 备注
     */
    private String remark;

    private Integer status = 0;




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
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }






}
