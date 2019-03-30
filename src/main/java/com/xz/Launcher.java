package com.xz;

import com.xz.http.handler.CtrlHandler;
import com.xz.http.handler.IHandler;
import com.xz.http.server.ZHttpServer;

public class Launcher {
    public static void main(String[] args) {
        try {
            Container.init();
            ZHttpServer httpServer = new ZHttpServer(Container.vertx, 8099, new IHandler[] {
                    new CtrlHandler()
            });
            httpServer.listen();
            System.out.println("server is started now.");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
