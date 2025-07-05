package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.SinhVien;
import util.HttpUtil;

import java.lang.reflect.Type;
import java.util.List;

public class SinhVienController {

    private static final String BASE_URL = "http://localhost:8080/MyWebApp/api/sinhvien";

    public static List<SinhVien> getAll() {
        try {
            String json = HttpUtil.sendRequest(BASE_URL, "GET", null);
            Type type = new TypeToken<List<SinhVien>>() {}.getType();
            return new Gson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SinhVien getById(String maSV) {
        try {
            String json = HttpUtil.sendRequest(BASE_URL + "?id=" + maSV, "GET", null);
            return new Gson().fromJson(json, SinhVien.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<SinhVien> search(String keyword) {
        try {
            String json = HttpUtil.sendRequest(BASE_URL + "?search=" + keyword, "GET", null);
            Type type = new TypeToken<List<SinhVien>>() {}.getType();
            return new Gson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean insert(SinhVien sv) {
        try {
            sv.setGioiTinh(convertGenderToCode(sv.getGioiTinh()));
            String body = new Gson().toJson(sv);
            String res = HttpUtil.sendRequest(BASE_URL, "POST", body);
            return "success".equalsIgnoreCase(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean update(SinhVien sv) {
        try {
            sv.setGioiTinh(convertGenderToCode(sv.getGioiTinh()));
            String body = new Gson().toJson(sv);
            String res = HttpUtil.sendRequest(BASE_URL, "PUT", body);
            return "updated".equalsIgnoreCase(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    private static String convertGenderToCode(String gender) {
        if (gender == null) return "";
        gender = gender.trim().toLowerCase();
        if (gender.equals("nam")) return "1";
        if (gender.equals("nữ") || gender.equals("nu")) return "0";
        return gender; // giữ nguyên nếu đã là "1"/"0"
    }


    public static boolean delete(String maSV) {
        try {
            String res = HttpUtil.sendRequest(BASE_URL + "?id=" + maSV, "DELETE", null);
            return "deleted".equalsIgnoreCase(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
