package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login-google")
public class LoginGoogleServlet extends HttpServlet {

    private static final String CLIENT_ID = "YOUR_CLIENT_ID";
    private static final String REDIRECT_URI = "http://localhost:9999/bookzone3/oauth2callback";
    private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email";
    private static final String AUTH_URL = "https://accounts.google.com/o/oauth2/auth";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oauthURL = AUTH_URL
                + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=code"
                + "&scope=" + SCOPE
                + "&access_type=offline";
        resp.sendRedirect(oauthURL);
    }
}
