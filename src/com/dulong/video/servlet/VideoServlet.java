package com.dulong.video.servlet;


import com.alibaba.fastjson.JSON;
import com.dulong.video.base.Constant;
import com.dulong.video.utils.JpushClientUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * http://localhost:8081/video/send?ipAddress=192.168.0.44&registrationID=160a3797c83e228f768
 */
public class VideoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String registrationID = request.getParameter("registrationID");
        String ipAddress = request.getParameter("ipAddress");
        if (registrationID == null || registrationID.isEmpty()) {
            throw new RuntimeException("缺少 registrationID 参数");
        }
        if (ipAddress == null || ipAddress.isEmpty()) {
            throw new RuntimeException("缺少 ipAddress 参数");
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("ipAddress", ipAddress);
        map.put("registrationID", registrationID);
        map.put("type", "2");
        String jsonString = JSON.toJSONString(map);

        if (JpushClientUtil.sendToRegistrationId(registrationID, "", "", jsonString, "") == 1) {
            String videoPath = Constant.VIDEO_URL(ipAddress, registrationID);
            System.out.println(videoPath);
            response.getOutputStream().write(videoPath.getBytes("UTF-8"));
        }
    }
}
