package com.xz.http.handler;

import io.vertx.ext.web.Router;

public interface IHandler {
    void handle(Router router);
}
