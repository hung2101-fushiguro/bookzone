package controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.GoogleUser;
import model.User;
import service.UserService;

@WebServlet("/oauth2callback")

public class GoogleOAuth2CallbackServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String CLIENT_ID = "";
    private static final String CLIENT_SECRET = "";
    private static final String REDIRECT_URI = "http://localhost:9999/bookzone/oauth2callback";

    private static final String TOKEN_ENDPOINT = "https://oauth2.googleapis.com/token";
    private static final String USERINFO_ENDPOINT = "https://www.googleapis.com/oauth2/v3/userinfo";

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            response.sendRedirect("login?error=missing_code");
            return;
        }

        try {
            // 1️⃣ Đổi code lấy access token
            String accessToken = exchangeCodeForAccessToken(code);

            // 2️⃣ Gọi Google lấy thông tin người dùng
            GoogleUser googleUser = getUserInfo(accessToken);

            // 3️⃣ Tìm hoặc tạo user local (✔️ gọi đúng hàm Service mới)
            User user = userService.findOrCreateByGoogleUser(googleUser);

            // 4️⃣ Tạo session đăng nhập
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // 5️⃣ Chuyển về home
            response.sendRedirect("home");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login?error=oauth_error");
        }
    }

    private String exchangeCodeForAccessToken(String code) throws Exception {
        URL url = new URL(TOKEN_ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        String params = "code=" + URLEncoder.encode(code, "UTF-8")
                + "&client_id=" + URLEncoder.encode(CLIENT_ID, "UTF-8")
                + "&client_secret=" + URLEncoder.encode(CLIENT_SECRET, "UTF-8")
                + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8")
                + "&grant_type=authorization_code";

        try (OutputStream os = conn.getOutputStream()) {
            os.write(params.getBytes());
        }

        InputStream is = conn.getInputStream();
        JsonObject json = JsonParser.parseReader(new InputStreamReader(is)).getAsJsonObject();
        return json.get("access_token").getAsString();
    }

    private GoogleUser getUserInfo(String accessToken) throws Exception {
        URL url = new URL(USERINFO_ENDPOINT + "?access_token=" + URLEncoder.encode(accessToken, "UTF-8"));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        InputStream is = conn.getInputStream();
        JsonObject json = JsonParser.parseReader(new InputStreamReader(is)).getAsJsonObject();

        GoogleUser user = new GoogleUser();
        user.setEmail(json.get("email").getAsString());
        user.setName(json.get("name").getAsString());
        user.setPicture(json.get("picture").getAsString());
        return user;
    }
}
