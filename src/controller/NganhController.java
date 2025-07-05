package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Nganh;
import util.HttpUtil;

import java.lang.reflect.Type;
import java.util.List;

public class NganhController {
    private static final String BASE_URL = "http://localhost:8080/MyWebApp/api/nganh";

    public static List<Nganh> getAll() {
        try {
            String json = HttpUtil.sendRequest(BASE_URL, "GET", null);
            Type type = new TypeToken<List<Nganh>>() {}.getType();
            return new Gson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Nganh getById(String maNganh) {
        try {
            String json = HttpUtil.sendRequest(BASE_URL + "?id=" + maNganh, "GET", null);
            return new Gson().fromJson(json, Nganh.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Nganh> search(String keyword) {
        try {
            String json = HttpUtil.sendRequest(BASE_URL + "?search=" + keyword, "GET", null);
            Type type = new TypeToken<List<Nganh>>() {}.getType();
            return new Gson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean insert(Nganh ng) {
        try {
            String body = new Gson().toJson(ng);
            String res = HttpUtil.sendRequest(BASE_URL, "POST", body);
            return "success".equalsIgnoreCase(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean update(Nganh ng) {
        try {
            String body = new Gson().toJson(ng);
            String res = HttpUtil.sendRequest(BASE_URL, "PUT", body);
            return "updated".equalsIgnoreCase(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(String maNganh) {
        try {
            String res = HttpUtil.sendRequest(BASE_URL + "?id=" + maNganh, "DELETE", null);
            return "deleted".equalsIgnoreCase(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
