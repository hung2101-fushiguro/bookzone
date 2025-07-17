/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.UserService;

/**
 *
 * @author ADMIN
 */
@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");
        HttpSession session = req.getSession(false);
        String email = (String) session.getAttribute("emailForReset");

        if (!newPassword.equals(confirmPassword)) {
            req.setAttribute("errorMessage", "Mật khẩu nhập lại không trùng khớp.");
            req.getRequestDispatcher("user/resetPassword.jsp").forward(req, resp);
            return;
        }

        if (email != null) {
            try {
                userService.updatePasswordByEmail(email, newPassword);
                session.removeAttribute("emailForReset"); // Optional: xóa luôn để tránh dùng lại
                session.setAttribute("successMessage", "Đổi mật khẩu thành công. Mời bạn đăng nhập lại.");
                resp.sendRedirect("login");
            } catch (Exception e) {
                req.setAttribute("errorMessage", "Không thể cập nhật mật khẩu.");
                req.getRequestDispatcher("user/resetPassword.jsp").forward(req, resp);
            }
        } else {
            resp.sendRedirect("login");
        }
    }

}
