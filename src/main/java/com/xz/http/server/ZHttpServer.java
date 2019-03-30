package com.xz.http.server;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.jwt.JWTOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

import com.xz.http.handler.IHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;

/**
 * http服务通用类
 */
public class ZHttpServer {
    /**
     * server
     */
    private HttpServer server;
    /**
     * router
     */
    private Router router;
    /**
     * 监听端口
     */
    private int port;

    public ZHttpServer(Vertx vertx, int port, IHandler[] handlers) {
        this.server = vertx.createHttpServer();
        this.router = Router.router(vertx);
        this.port = port;

        JsonObject authConfig = new JsonObject().put("KeyStore", new JsonObject()
                .put("type", "jceks")
                .put("path", "keystore.jceks")
                .put("password", "secret")
        );
        JWTAuth jwtAuth = JWTAuth.create(vertx, authConfig);


      //  this.router.route("/user/*").handler(JWTAuthHandler.create(jwtAuth));

        router.route("/user/login").handler(ctx->{
            if ("paulo".equals(ctx.request().getParam("username")) &&
                    "secret".equals(ctx.request().getParam("password"))) {
            } else {
                ctx.response().end(jwtAuth.generateToken(new JsonObject().put("sub", "paulo"), new JWTOptions()));
            }
        });


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
