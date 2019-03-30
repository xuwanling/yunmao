package com.xz.http.server;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

import com.xz.http.handler.IHandler;

/**
 * http服务通用类
 * */
public class ZHttpServer {
    /** server */
    private HttpServer server;
    /** router */
    private Router router;
    /** 监听端口 */
    private int port;

    public ZHttpServer(Vertx vertx, int port, IHandler[] handlers) {
        this.server = vertx.createHttpServer();
        this.router = Router.router(vertx);
        this.port = port;
        //允许跨域
        this.router.route().handler(CorsHandler.create("*")
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedHeader("X-PINGARUNER")
                .allowedHeader("Content-Type"));
        //此步实现了getBodyAsJson
        this.router.route().handler(BodyHandler.create().setBodyLimit(-1));
        //处理router
        for (IHandler handler : handlers) {
            handler.handle(this.router);
        }
    }

    /**
     * 启动方法
     */
    public void listen() {
        this.server.requestHandler(router::accept).listen(port);
    }
}
