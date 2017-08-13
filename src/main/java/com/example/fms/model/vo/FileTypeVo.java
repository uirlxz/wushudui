package com.example.fms.model.vo;

/**
 * Author: liuxingyu
 * DATE: 2017-07-19.14:35
 * description:
 * version:
 */
public class FileTypeVo extends BaseVo {
    /**
     * 文件类型
     */
    private String type;

    /**
     * 是否使用 1,是 0 ，否
     */
    private Integer status;

    /**
     * 该jar的目录结构
     */
    private String catalog;
    /**
     * 方法
     */
    private String method;

    /**
     * 格式
     */
    private String swf;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSwf() {
        return swf;
    }

    public void setSwf(String swf) {
        this.swf = swf;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
