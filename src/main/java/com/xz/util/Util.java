package com.xz.util;

import com.xz.model.HttpResModel;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.xz.util.HttpClient.sendPOST;

public class Util {

    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    /**
     * 检查并取得页面上传过来的数据
     * */
    public static JsonObject checkAndGetBdata(RoutingContext context, String[] keys) {
        JsonObject bdata = context.getBodyAsJson();
        logger.info("接收http请求: "+context.normalisedPath()+", 参数: "+bdata.toString());
        for (String key : keys) {
            if (!bdata.containsKey(key)) throw new RuntimeException("客户端JSON数据缺少字段" + key);
        }
        return bdata;
    }

    /**
     * 回复页面请求
     * */
    public static void doResponse(RoutingContext context, HttpResModel sdata) {
        context.response().putHeader("content-type", "application/json;charset=UTF-8")
                .end(JsonObject.mapFrom(sdata).toString(), "UTF-8");
    }

    public static void buildSdataFromCenter(String url, JsonObject param, HttpResModel sdata) {
        JsonObject data = new JsonObject(sendPOST(url, param));
        sdata.errorCode = data.getInteger("code");
        sdata.errorMsg = data.getString("errorMsg");
        sdata.data = data.getValue("data");
    }
}
