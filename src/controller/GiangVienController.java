package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.GiangVien;
import util.HttpUtil;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class GiangVienController {
    private static final String BASE_URL = "http://localhost:8080/MyWebApp/api/giangvien";
    private static final Gson gson = new Gson();

    public static List<GiangVien> getAll() {
        try {
            String res = HttpUtil.sendRequest(BASE_URL, "GET", null);
            Type listType = new TypeToken<List<GiangVien>>() {}.getType();
            return gson.fromJson(res, listType);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static List<GiangVien> search(String keyword) {
        try {
            String res = HttpUtil.sendRequest(BASE_URL + "?search=" + keyword, "GET", null);
            Type listType = new TypeToken<List<GiangVien>>() {}.getType();
            return gson.fromJson(res, listType);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static boolean add(GiangVien gv) {
        try {
            String json = gson.toJson(gv);
            String res = HttpUtil.sendRequest(BASE_URL, "POST", json);
            return "success".equalsIgnoreCase(res.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean update(GiangVien gv) {
        try {
            String json = gson.toJson(gv);
            String res = HttpUtil.sendRequest(BASE_URL, "PUT", json);
            return "updated".equalsIgnoreCase(res.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delete(String maGV) {
        try {
            String res = HttpUtil.sendRequest(BASE_URL + "?id=" + maGV, "DELETE", null);
            return "deleted".equalsIgnoreCase(res.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
