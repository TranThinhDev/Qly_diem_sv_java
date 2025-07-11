package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.KetQua;
import util.HttpUtil;

import java.lang.reflect.Type;
import java.util.List;

public class KetQuaController {

    private static final String BASE_URL = "http://localhost:8080/MyWebApp/api/ketqua";
    private static final Gson gson = new Gson();

    public static List<KetQua> getAll() {
        try {
            String json = HttpUtil.sendRequest(BASE_URL, "GET", null);
            Type type = new TypeToken<List<KetQua>>() {}.getType();
            return gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<KetQua> getByMaHP(String maHP) {
        try {
            String json = HttpUtil.sendRequest(BASE_URL + "?search=" + maHP, "GET", null);
            Type type = new TypeToken<List<KetQua>>() {}.getType();
            return gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static KetQua getByMaSV_MaHP(String maSV, String maHP) {
        try {
            String url = BASE_URL + "?maSV=" + maSV + "&maHP=" + maHP;
            String json = HttpUtil.sendRequest(url, "GET", null);
            return gson.fromJson(json, KetQua.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean insert(KetQua kq) {
        try {
            String body = gson.toJson(kq);
            String response = HttpUtil.sendRequest(BASE_URL, "POST", body);
            return "success".equalsIgnoreCase(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean update(KetQua kq) {
        try {
            String body = gson.toJson(kq);
            String response = HttpUtil.sendRequest(BASE_URL, "PUT", body);
            return "updated".equalsIgnoreCase(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(String maSV, String maHP) {
        try {
            String url = BASE_URL + "?maSV=" + maSV + "&maHP=" + maHP;
            String response = HttpUtil.sendRequest(url, "DELETE", null);
            return "deleted".equalsIgnoreCase(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
