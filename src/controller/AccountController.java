package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.TaiKhoan;
import util.HttpUtil;

import java.lang.reflect.Type;
import java.util.List;

public class AccountController {
    private static final String BASE_URL = "http://localhost:8080/MyWebApp/api/account";

    public static List<TaiKhoan> getAll() {
    try {
        String json = HttpUtil.sendRequest(BASE_URL, "GET", null);
        Type type = new TypeToken<List<TaiKhoan>>() {}.getType();
        return new Gson().fromJson(json, type);
    } catch (Exception e) {
        e.printStackTrace();
        return java.util.Collections.emptyList();
    }
}


    public static TaiKhoan getByUsername(String tenDN) throws Exception {
        String json = HttpUtil.sendRequest(BASE_URL + "?id=" + tenDN, "GET", null);
        return new Gson().fromJson(json, TaiKhoan.class);
    }

    public static boolean insert(TaiKhoan tk) {
        try {
            String body = new Gson().toJson(tk);
            String res = HttpUtil.sendRequest(BASE_URL, "POST", body);
            return res.contains("true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean update(TaiKhoan tk) {
        try {
            String body = new Gson().toJson(tk);
            String res = HttpUtil.sendRequest(BASE_URL, "PUT", body);
            return res.contains("true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delete(String tenDN) {
        try {
            String res = HttpUtil.sendRequest(BASE_URL + "?tenDN=" + tenDN, "DELETE", null);
            return res.contains("true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<TaiKhoan> search(String keyword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
