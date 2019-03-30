package com.xz;

import io.vertx.core.Vertx;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;

/**
 * 本系统的容器，随着项目的启动初始化，加载需要用到的数据
 * */
public class Container {
    /** vertx */
    static final Vertx vertx = Vertx.vertx();
    /** 配置数据 */
    private static PropertiesConfiguration properties;
    /** center地址 */
    public static String HTTP_URL_CENTER;

    static void init() throws Exception {
        //加载application.properties文件
        properties = new Configurations().properties("conf.properties");
        HTTP_URL_CENTER = properties.getString("http.url.center");
    }
}
