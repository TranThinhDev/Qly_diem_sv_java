package controller;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import model.User;
import util.HttpUtil;

import java.util.List;

public class UserController {
    private static final String BASE_URL = "http://localhost:8080/MyWebApp/api/users";
    private static final Gson gson = new Gson();

    public static List<User> getAllUsers() throws Exception {
        String json = HttpUtil.sendRequest(BASE_URL, "GET", null);
        return gson.fromJson(json, new TypeToken<List<User>>(){}.getType());
    }

    public static User getUserById(int id) throws Exception {
        String json = HttpUtil.sendRequest(BASE_URL + "?id=" + id, "GET", null);
        return gson.fromJson(json, User.class);
    }

    public static boolean insertUser(User u) throws Exception {
        String json = gson.toJson(u);
        String response = HttpUtil.sendRequest(BASE_URL, "POST", json);
        return response.contains("\"success\":true");
    }

    public static boolean updateUser(User u) throws Exception {
        String json = gson.toJson(u);
        String response = HttpUtil.sendRequest(BASE_URL, "PUT", json);
        return response.contains("\"success\":true");
    }

    public static boolean deleteUser(int id) throws Exception {
        String response = HttpUtil.sendRequest(BASE_URL + "?id=" + id, "DELETE", null);
        return response.contains("\"success\":true");
    }
}
