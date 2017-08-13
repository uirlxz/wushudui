package com.example.fms.utils;

/**
 * Author: liuxingyu
 * DATE: 2017-06-27.11:12
 * description:
 * version:
 */
public class Constants {
    /**
     * 国际化  zh_CN：中文简体 en_US：英文  zh_TW:中文繁体
     */
    public static  final  String  DEFAULT_LANGUE = "zh_CN";
    public static final String ERROR_MSG = "error"; //错误提示文件
    /**
     * 错误提示的key
     */
    public static final String unameErrMes = "unameErrMes";
    public static final String pwdErrMes = "pwdErrMes";
    public static final String codeErrMes = "codeErrMes";
    public static final String erroldPass = "erroldPass";
    public static final String successUpass = "successUpass";
    public static final String errMes = "errMes";
    /**
     * 响应码
     */
    public static final int SUCCESS_CODE = 200; //成功  ,验证通过
    public static final int FAILED_CODE = 500; //失败 ，验证不通过
    public static  final int NO_USER_CODE = 100; //用户名不存在
    public static  final int ERROR_PASSWORD_CODE = 101; //密码错误
    public static  final int ERROR_CODE = 102; //验证码错误
    public static  final int NOT_LOGINUSER_CODE = 103; //用户没有登陆
    public static  final int NO_PERMISSION_CODE = 104; //该用户没有任何权限
    public static  final int USER_EXIST_CODE = 105; //用户名已存在


    /**
     * 是否生产菜单
     */
    public static  final int YES_MENU = 1;
    public static  final int NO_MENU = 0;
    /**
     * 是否导入算法
     */
    public static  final int YES_FILE_TYPE = 1;
    public static  final int NO_FILE_TYPE = 0;
    /**
     * 文件是否被标记
     */
    public static  final int YES_FILE_SIGN = 1; //成功
    public static  final int NO_FILE_SIGN = 0;  //未标记
    public static  final int ERROR_FILE_SIGN = 3; //格式错误
    public static  final int DANGER_FILE_SIGN = 2; //失败

    /**
     * 菜单目录结构状态
     */
    public static final int ONE_MENU = 1;  //一级目录
    public static final int TWO_MENU = 2;  //二级目录
    public static final int ZERO_MENU = 0;  //不生成目录
    /**
     * 服务器ip
     */
    public static final String SERVER_IP = PropertiesUtils.getStringByKey("server.ip", "local.properties");

}
