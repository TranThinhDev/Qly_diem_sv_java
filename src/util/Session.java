package util;

import model.TaiKhoan;

public class Session {
    private static TaiKhoan loggedInUser;

    public static TaiKhoan getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(TaiKhoan user) {
        loggedInUser = user;
    }

    public static void clear() {
        loggedInUser = null;
    }
}
