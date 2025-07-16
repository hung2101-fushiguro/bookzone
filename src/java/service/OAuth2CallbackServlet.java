//package controller;
//
//import java.io.IOException;
//import java.util.Collections;
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
//import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.json.JsonFactory;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.security.GeneralSecurityException;
//
//@WebServlet("/oauth2callback")
//public class OAuth2CallbackServlet extends HttpServlet {
//
//    private static final String CLIENT_ID = "19340180186-sm87uhblq9epkqme3jhgvt4fef4sb45k.apps.googleusercontent.com";
//    private static final String CLIENT_SECRET = "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";
//    private static final String REDIRECT_URI = "http://localhost:9999/bookzone/oauth2callback";
//    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String code = request.getParameter("code");
//        System.out.println("[OAuth2Callback] Received code: " + code);
//
//        if (code == null || code.isEmpty()) {
//            System.out.println("[OAuth2Callback] No code received!");
//            response.getWriter().println("No authorization code received!");
//            return;
//        }
//
//        try {
//            GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
//                    new NetHttpTransport(),
//                    JSON_FACTORY,
//                    "https://oauth2.googleapis.com/token",
//                    CLIENT_ID,
//                    CLIENT_SECRET,
//                    code,
//                    REDIRECT_URI
//            ).execute();
//
//            System.out.println("[OAuth2Callback] Token response OK");
//
//            String idTokenString = tokenResponse.getIdToken();
//            System.out.println("[OAuth2Callback] ID Token String: " + idTokenString);
//
//            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
//                    new NetHttpTransport(),
//                    JSON_FACTORY
//            ).setAudience(Collections.singletonList(CLIENT_ID)).build();
//
//            GoogleIdToken idToken = verifier.verify(idTokenString);
//
//            if (idToken != null) {
//                System.out.println("[OAuth2Callback] ID Token verified!");
//
//                GoogleIdToken.Payload payload = idToken.getPayload();
//                String email = payload.getEmail();
//                String name = (String) payload.get("name");
//
//                System.out.println("[OAuth2Callback] User info: " + name + " | " + email);
//
//                // Lưu user vào session
//                HttpSession session = request.getSession();
//                session.setAttribute("user", name);
//                session.setAttribute("email", email);
//
//                System.out.println("[OAuth2Callback] Redirecting to /home...");
//                response.sendRedirect(request.getContextPath() + "/home");
//                return;
//
//            } else {
//                System.out.println("[OAuth2Callback] ID Token verification failed!");
//                response.getWriter().println("ID Token is invalid!");
//                return;
//            }
//
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//            System.out.println("[OAuth2Callback] Security error: " + e.getMessage());
//            response.getWriter().println("Security verification error: " + e.getMessage());
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("[OAuth2Callback] IO error: " + e.getMessage());
//            response.getWriter().println("IO error: " + e.getMessage());
//        }
//    }
//}
