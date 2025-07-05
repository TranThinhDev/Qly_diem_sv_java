package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Lop;
import util.HttpUtil;

import java.lang.reflect.Type;
import java.util.List;

public class LopController {

    private static final String BASE_URL = "http://localhost:8080/MyWebApp/api/lop";

    public static List<Lop> getAll() {
        try {
            String json = HttpUtil.sendRequest(BASE_URL, "GET", null);
            Type type = new TypeToken<List<Lop>>() {}.getType();
            return new Gson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Lop getById(String maLop) {
        try {
            String json = HttpUtil.sendRequest(BASE_URL + "?id=" + maLop, "GET", null);
            return new Gson().fromJson(json, Lop.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Lop> search(String keyword) {
        try {
            String json = HttpUtil.sendRequest(BASE_URL + "?search=" + keyword, "GET", null);
            Type type = new TypeToken<List<Lop>>() {}.getType();
            return new Gson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean insert(Lop lop) {
        try {
            String body = new Gson().toJson(lop);
            String res = HttpUtil.sendRequest(BASE_URL, "POST", body);
            return "success".equalsIgnoreCase(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean update(Lop lop) {
        try {
            String body = new Gson().toJson(lop);
            String res = HttpUtil.sendRequest(BASE_URL, "PUT", body);
            return "updated".equalsIgnoreCase(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(String maLop) {
        try {
            String res = HttpUtil.sendRequest(BASE_URL + "?id=" + maLop, "DELETE", null);
            return "deleted".equalsIgnoreCase(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
