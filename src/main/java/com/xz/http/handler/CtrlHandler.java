package com.xz.http.handler;

import io.vertx.ext.web.Router;

import com.xz.http.service.CtrlService;

public class CtrlHandler implements IHandler {
    @Override
    public void handle(Router router) {
        //获取家庭下房间
        router.post("/getRooms").handler(CtrlService::getRooms);
        //获取房间下设备
        router.post("/getRoomDevices").handler(CtrlService::getRoomDevices);
        //获取家庭下场景
        router.post("/getScenes").handler(CtrlService::getScenes);
        //设备控制接口
        router.post("/sendCommand").handler(CtrlService::sendCommand);
        //场景触发接口
        router.post("/triggerScene").handler(CtrlService::triggerScene);
        //获取房间下设备状态
        router.post("/roomDeviceStatus").handler(CtrlService::getDeviceStatus);
        //获取具体设备状态
        router.post("/deviceStatus").handler(CtrlService::getFamilyDeviceStatus);
        //一次性获取家庭下的房间以及房间下的设备
        router.post("/getRoomAndDevice").handler(CtrlService::getFamilyRoomAndDev);
    }
}
