package controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;
import service.UserService;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService(); // khởi tạo DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Đọc cookie "rememberMe" nếu có
        Cookie[] cookies = request.getCookies();
        String rememberedEmail = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("rememberMe".equals(cookie.getName())) {
                    rememberedEmail = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    break;
                }
            }
        }

        request.setAttribute("rememberedEmail", rememberedEmail);
        request.getRequestDispatcher("user/loginUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        User user = userService.checkLogin(email, password);

        if (user == null) {
            request.setAttribute("errorMessage", "Sai email hoặc mật khẩu");
            request.getRequestDispatcher("user/loginUser.jsp").forward(request, response);
            return;
        }

        // Xử lý "Remember Me"
        Cookie cookie;
        if ("on".equals(rememberMe)) {
            cookie = new Cookie("rememberMe", URLEncoder.encode(email, "UTF-8"));
            cookie.setMaxAge(60 * 60 * 24 * 7); // 7 ngày
        } else {
            cookie = new Cookie("rememberMe", null);
            cookie.setMaxAge(0); // xóa cookie
        }
        response.addCookie(cookie);

        // Lưu user vào session
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        // Phân quyền và điều hướng
        String role = user.getRole();
        if ("admin".equalsIgnoreCase(role)) {
            response.sendRedirect("books");
            // ví dụ admin trang quản lý users
            return;
        } else {
            response.sendRedirect("home.jsp"); // trang chính của người dùng
            return;
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý đăng nhập người dùng có Remember Me và phân quyền";
    }
}
