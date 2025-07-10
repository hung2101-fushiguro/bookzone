package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Hủy session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Xóa cookie RememberMe nếu cần
        Cookie cookie = new Cookie("rememberMe", null);
        cookie.setMaxAge(0); // xóa ngay
        response.addCookie(cookie);

        // Chuyển hướng về trang login hoặc home
        response.sendRedirect("home");
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý logout user";
    }
}
