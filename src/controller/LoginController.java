package controller;

import com.google.gson.Gson;
import model.TaiKhoan;
import model.LoginRequest;
import util.HttpUtil;

public class LoginController {
    public static TaiKhoan login(String user, String pass) {
        try {
            LoginRequest req = new LoginRequest(user, pass);
            String json = new Gson().toJson(req);

            String res = HttpUtil.sendRequest(
                "http://localhost:8080/MyWebApp/api/login",
                "POST",
                json
            );

            return new Gson().fromJson(res, TaiKhoan.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
