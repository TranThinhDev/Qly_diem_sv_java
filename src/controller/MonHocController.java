package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.MonHoc;
import util.HttpUtil;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MonHocController {
    private static final String BASE_URL = "http://localhost:8080/MyWebApp/api/monhoc";
    private static final Gson gson = new Gson();

    public static List<MonHoc> getAll() {
        try {
            String json = HttpUtil.sendRequest(BASE_URL, "GET", null);
            Type type = new TypeToken<List<MonHoc>>() {}.getType();
            return gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static MonHoc getById(String maMon) {
        try {
            String encodedId = URLEncoder.encode(maMon, StandardCharsets.UTF_8.toString());
            String json = HttpUtil.sendRequest(BASE_URL + "?id=" + encodedId, "GET", null);
            return gson.fromJson(json, MonHoc.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<MonHoc> search(String keyword) {
        try {
            String encodedKey = URLEncoder.encode(keyword, StandardCharsets.UTF_8.toString());
            String json = HttpUtil.sendRequest(BASE_URL + "?search=" + encodedKey, "GET", null);
            Type type = new TypeToken<List<MonHoc>>() {}.getType();
            return gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean insert(MonHoc mh) {
        try {
            String body = gson.toJson(mh);
            String res = HttpUtil.sendRequest(BASE_URL, "POST", body);
            return "success".equalsIgnoreCase(res.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean update(MonHoc mh) {
        try {
            String body = gson.toJson(mh);
            String res = HttpUtil.sendRequest(BASE_URL, "PUT", body);
            return "updated".equalsIgnoreCase(res.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delete(String maMon) {
        try {
            String encodedId = URLEncoder.encode(maMon, StandardCharsets.UTF_8.toString());
            String res = HttpUtil.sendRequest(BASE_URL + "?id=" + encodedId, "DELETE", null);
            return "deleted".equalsIgnoreCase(res.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
