package com.xz.http.service;

import com.xz.model.HttpResModel;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xz.Container.HTTP_URL_CENTER;
import static com.xz.util.Util.*;

public class CtrlService {

    public static void getRooms(RoutingContext context) {
        HttpResModel sdata = new HttpResModel();
        try {
            JsonObject bdata = checkAndGetBdata(context, new String[]{"homeId"});
            JsonObject param = new JsonObject().put("homeId", bdata.getString("homeId"));
            buildSdataFromCenter(HTTP_URL_CENTER + "/yunmao/getRooms", param, sdata);
        } catch (Exception e) {
            sdata.errorCode = 1;
            sdata.errorMsg = e.getMessage();
        }
        doResponse(context, sdata);
    }


    public static void getRoomDevices(RoutingContext context) {
        HttpResModel sdata = new HttpResModel();
        try {
            JsonObject bdata = checkAndGetBdata(context, new String[]{"homeId"});
            JsonObject param = new JsonObject().put("homeId", bdata.getString("homeId"));
            String roomName = bdata.getString("roomName");
            if (roomName != null) param.put("roomName", roomName);
            buildSdataFromCenter(HTTP_URL_CENTER + "/yunmao/getRoomDevices", param, sdata);
        } catch (Exception e) {
            sdata.errorCode = 1;
            sdata.errorMsg = e.getMessage();
        }
        doResponse(context, sdata);
    }

    public static void getScenes(RoutingContext context) {
        HttpResModel sdata = new HttpResModel();
        try {
            JsonObject bdata = checkAndGetBdata(context, new String[]{"homeId"});
            JsonObject param = new JsonObject().put("homeId", bdata.getString("homeId"));
            buildSdataFromCenter(HTTP_URL_CENTER + "/yunmao/getScenes", param, sdata);
        } catch (Exception e) {
            sdata.errorCode = 1;
            sdata.errorMsg = e.getMessage();
        }
        doResponse(context, sdata);
    }

    public static void sendCommand(RoutingContext context) {
        HttpResModel sdata = new HttpResModel();
        try {
            JsonObject bdata = checkAndGetBdata(context, new String[]{"homeId", "phone", "cmds"});
            List<Map<String, Object>> cmdList = new ArrayList<>();
            for (Object obj : bdata.getJsonArray("cmds")) {
                try {
                    JsonObject item = (JsonObject) obj;
                    Map<String, Object> cmd = new HashMap<>();
                    cmd.put("cmdName", item.getString("cmdName"));
                    Object cmdDetails = item.getJsonObject("cmdDetails");
                    if (cmdDetails != null) cmd.put("cmdDetails", cmdDetails);
                    Map<String, Object> sendCmd = new HashMap<>();
                    sendCmd.put("path", item.getString("path"));
                    sendCmd.put("cmd", cmd);
                    cmdList.add(sendCmd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            JsonObject param = new JsonObject().put("cmd", cmdList);
            buildSdataFromCenter(HTTP_URL_CENTER + "/deviceCtrl/batchComand", param, sdata);
        } catch (Exception e) {
            sdata.errorCode = 1;
            sdata.errorMsg = e.getMessage();
        }
        doResponse(context, sdata);
    }

    public static void triggerScene(RoutingContext context) {
        HttpResModel sdata = new HttpResModel();
        try {
            JsonObject bdata = checkAndGetBdata(context, new String[]{"homeId", "phone", "path"});
            JsonObject param = new JsonObject().put("path", bdata.getString("path"));
            buildSdataFromCenter(HTTP_URL_CENTER + "/deviceCtrl/command", param, sdata);
        } catch (Exception e) {
            sdata.errorCode = 1;
            sdata.errorMsg = e.getMessage();
        }
        doResponse(context, sdata);
    }


    public static void getDeviceStatus(RoutingContext context) {
        HttpResModel sdata = new HttpResModel();
        try {
            JsonObject bdata = checkAndGetBdata(context, new String[]{"homeId","roomNo"});
            String hotelId = bdata.getString("homeId");
            String roomNo = bdata.getString("roomNo");
            JsonObject param = new JsonObject().put("hotelId", hotelId).put("roomNo", roomNo);
            buildSdataFromCenter(HTTP_URL_CENTER + "/room/getRoomDeviceStatus", param, sdata);
        } catch (Exception e) {
            sdata.errorCode = 1;
            sdata.errorMsg = e.getMessage();
        }
        doResponse(context, sdata);
    }

    public static void getFamilyDeviceStatus(RoutingContext context) {
        HttpResModel sdata = new HttpResModel();
        try {
            JsonObject bdata = checkAndGetBdata(context, new String[]{"path"});
            String path = bdata.getString("path");
            JsonObject param = new JsonObject().put("path", path);
            buildSdataFromCenter(HTTP_URL_CENTER + "/room/getFamilyDeviceStatus", param, sdata);
        } catch (Exception e) {
            sdata.errorCode = 1;
            sdata.errorMsg = e.getMessage();
        }
        doResponse(context, sdata);
    }


    public static void getFamilyRoomAndDev(RoutingContext context){
        HttpResModel sdata = new HttpResModel();
        try {
            JsonObject bdata = checkAndGetBdata(context, new String[]{"homeId"});
            String homeId = bdata.getString("homeId");
            JsonObject param = new JsonObject().put("homeId", homeId);
            buildSdataFromCenter(HTTP_URL_CENTER + "/room/getRoomAndDev", param, sdata);
        } catch (Exception e) {
            sdata.errorCode = 1;
            sdata.errorMsg = e.getMessage();
        }
        doResponse(context, sdata);
    }

}
