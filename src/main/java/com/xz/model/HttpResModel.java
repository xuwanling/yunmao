package com.xz.model;

/**
 * http服务给页面返回数据的统一模型
 * */
public class HttpResModel {
    /** 标识 0-处理请求成功 1-处理请求失败 */
    public int errorCode = 0;
    /** 消息说明 */
    public String errorMsg;
    /** 数据 */
    public Object data;

    public HttpResModel() {}

    public HttpResModel(int errorCode) {
        this.errorCode = errorCode;
    }

    public HttpResModel(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public HttpResModel(int errorCode, String errorMsg, Object data) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }
}
