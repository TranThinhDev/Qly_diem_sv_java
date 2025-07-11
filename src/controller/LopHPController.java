package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.LopHP;
import util.HttpUtil;

import java.lang.reflect.Type;
import java.util.List;

public class LopHPController {
    private static final String BASE_URL = "http://localhost:8080/MyWebApp/api/lophp";

    public static List<LopHP> getAll() {
        try {
            String json = HttpUtil.sendRequest(BASE_URL, "GET", null);
            Type type = new TypeToken<List<LopHP>>() {}.getType();
            return new Gson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LopHP getById(String maHP) {
        try {
            String json = HttpUtil.sendRequest(BASE_URL + "?id=" + maHP, "GET", null);
            return new Gson().fromJson(json, LopHP.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<LopHP> search(String keyword) {
        try {
            String json = HttpUtil.sendRequest(BASE_URL + "?search=" + keyword, "GET", null);
            Type type = new TypeToken<List<LopHP>>() {}.getType();
            return new Gson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean insert(LopHP lhp) {
        try {
            String body = new Gson().toJson(lhp);
            String res = HttpUtil.sendRequest(BASE_URL, "POST", body);
            return "success".equalsIgnoreCase(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean update(LopHP lhp) {
        try {
            String body = new Gson().toJson(lhp);
            String res = HttpUtil.sendRequest(BASE_URL, "PUT", body);
            return "updated".equalsIgnoreCase(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(String maHP) {
        try {
            String res = HttpUtil.sendRequest(BASE_URL + "?id=" + maHP, "DELETE", null);
            return "deleted".equalsIgnoreCase(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static List<LopHP> getByGiangVien(String maGV) {
    try {
        String url = BASE_URL + "?maGV=" + maGV;
        String json = HttpUtil.sendRequest(url, "GET", null);
        Type type = new TypeToken<List<LopHP>>() {}.getType();
        return new Gson().fromJson(json, type);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

    public static List<LopHP> searchByGiangVien(String maGV, String keyword) {
        try {
            String url = BASE_URL + "?maGV=" + maGV + "&search=" + keyword;
            String json = HttpUtil.sendRequest(url, "GET", null);
            Type type = new TypeToken<List<LopHP>>() {}.getType();
            return new Gson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
