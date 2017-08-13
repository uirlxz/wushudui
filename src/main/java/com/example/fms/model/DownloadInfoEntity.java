package com.example.fms.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by www on 2016/8/26.
 */
@Entity
@Table(name = "t_download_info")
public class DownloadInfoEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @Column(name="createTime")
    private Date createTime;

    private String uuid;
    private String ip;
    @Column(name="userAgent")
    private String userAgent;

    private String browser;
    @Column(name="browserVersion")
    private String browserVersion;
    private String system;
    @Column(name="systemVersion")
    private String systemVersion;
    private String device;

    private String isp;
    private String country;
    private String region;
    private String city;

    public DownloadInfoEntity(String uuid, String ip, String userAgent, String browser,
                              String browserVersion, String system, String systemVersion,
                              String device, String isp, String country, String region,
                              String city) {
        this.uuid = uuid;
        this.ip = ip;
        this.userAgent = userAgent;
        this.createTime = new Date();

        this.browser = browser;
        this.browserVersion = browserVersion;
        this.system = system;
        this.systemVersion = systemVersion;
        this.device = device;
        this.isp = isp;

        this.country = country;
        this.region = region;
        this.city = city;
    }

    @Override
    public String toString() {
        return String.format(
                "download[id=%d, ip='%s', userAgent='%s']",
                id, ip, userAgent);
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getUuid(){
        return uuid;
    }

    public void setUuid(String uuid){
        this.uuid = uuid;
    }

    public String getIp(){
        return ip;
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public String getUserAgent(){
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Date getCreateTime(){
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
