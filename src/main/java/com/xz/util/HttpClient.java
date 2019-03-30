package com.xz.util;

import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author wz
 * http客户端通用类
 **/
public class HttpClient {
    /** 日志工具 */
    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);

    /** 发送GET请求 */
    public static String sendGET(String url, Map<String, Object> params) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            //将params添加到URL
            StringBuilder wholeUrl = new StringBuilder(url);
            if (params.size() > 0) {
                wholeUrl.append("?");
                for (Map.Entry item : params.entrySet()) {
                    wholeUrl.append(item.getKey()).append("=").append(item.getValue()).append("&");
                }
                wholeUrl.deleteCharAt(wholeUrl.lastIndexOf("&"));
            }
            //创建URL对象
            URL connURL = new URL(wholeUrl.toString());
            //打开URL连接
            HttpURLConnection httpConn = (HttpURLConnection)connURL.openConnection();
            //设置GET方式
            httpConn.setRequestMethod("GET");
            //必须
            httpConn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            //建立实际的连接
            httpConn.connect();
            //定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), StandardCharsets.UTF_8));
            // 读取返回的内容
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("failed to send get, url: "+url+", data: "+params+", result: " + result.toString()
                    + ", error: " + e.getMessage());
            return result.toString();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ignored) {}
        }
        logger.info("success to send get, url: "+url+", data: "+params+", result: " + result.toString());
        return result.toString();
    }

    /** 发送POST请求 */
    public static String sendPOST(String url, JsonObject bdata) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        OutputStream out = null;
        try {
            //创建URL对象
            URL connURL = new URL(url);
            //打开URL连接
            HttpURLConnection httpConn = (HttpURLConnection)connURL.openConnection();
            //设置POST方式
            httpConn.setRequestMethod("POST");
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            //必须
            httpConn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            //获取HttpURLConnection对象对应的输出流
            out = httpConn.getOutputStream();
            //发送请求参数
            out.write(bdata.toString().getBytes(StandardCharsets.UTF_8));
            out.flush();
            //定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), StandardCharsets.UTF_8));
            // 读取返回的内容
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("failed to send post, url: "+url+", data: "+bdata.toString()+", result: " + result.toString()
                    + ", error: " + e.getMessage());
            return result.toString();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception ignored) {}
        }
        logger.info("success to send post, url: "+url+", data: "+bdata.toString()+", result: " + result.toString());
        return result.toString();
    }

}
