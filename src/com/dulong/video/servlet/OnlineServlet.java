package com.dulong.video.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dulong.video.base.Constant;
import com.dulong.video.utils.JpushClientUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * http://localhost:8081/video/online
 */
public class OnlineServlet extends HttpServlet {


    private static List<Object> onlineList = new ArrayList<>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) throw new RuntimeException("缺少 type 参数 !!");
        int typeInt = Integer.parseInt(type);
        switch (typeInt) {
            case 1:
                String ipAddress = request.getParameter("ipAddress");
                if (ipAddress == null || ipAddress.isEmpty())
                    throw new RuntimeException("缺少 ipAddress 参数");

                HashMap<String, String> map = new HashMap<>();
                map.put("type", "1");
                map.put("ipAddress", ipAddress);
                String json = JSON.toJSONString(map);
                if (JpushClientUtil.sendToAll("", "", json, "") == 1) {
                    onlineList.clear();
                    String url = Constant.BASE_URL(ipAddress) + "online?type=3";
                    response.getOutputStream().write(url.getBytes("UTF-8"));
                }
                break;
            case 2:
                String userMsg = request.getParameter("userMsg");
                JSONObject jsonObject = JSON.parseObject(userMsg);

                onlineList.add(jsonObject);
                break;
            case 3:


                String jsonString = JSON.toJSONString(onlineList);
                response.getOutputStream().write(jsonString.getBytes("UTF-8"));
                break;
        }


    }
}
