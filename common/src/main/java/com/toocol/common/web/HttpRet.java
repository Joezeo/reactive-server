package com.toocol.common.web;

import com.alibaba.fastjson.JSONObject;

/**
 * @author ZhaoZhe
 * @email joezane.cn@gmail.com
 * @date 2021/2/3 10:34
 */
public class HttpRet {
    public static String error(String msg) {
        JSONObject ret = new JSONObject();
        ret.put("code", 400);
        ret.put("msg", msg);
        return ret.toJSONString();
    }

    public static String error(JSONObject result) {
        JSONObject ret = new JSONObject();
        ret.put("code", 400);
        ret.put("msg", result.toJSONString());
        return ret.toJSONString();
    }

    public static String success(String msg) {
        JSONObject ret = new JSONObject();
        ret.put("code", 200);
        ret.put("msg", msg);
        return ret.toJSONString();
    }

    public static String success(JSONObject result) {
        JSONObject ret = new JSONObject();
        ret.put("code", 200);
        ret.put("msg", result.toJSONString());
        return ret.toJSONString();
    }
}
