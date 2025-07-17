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
import model.User;
import service.UserService;
import util.MailUtil;

/**
 *
 * @author DELL
 */
@WebServlet(name = "ForgotPasswordServlet", urlPatterns = {"/forgot-password"})
public class ForgotPasswordServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        User user = userService.selectUserByEmail(email);

        if (user == null) {
            req.setAttribute("errorMessage", "Email không tồn tại.");
            req.getRequestDispatcher("user/forgotPassword.jsp").forward(req, resp);
            return;
        }

        String otp = String.valueOf((int) (Math.random() * 900000 + 100000)); // mã 6 chữ số
        HttpSession session = req.getSession();
        session.setAttribute("otp", otp);
        session.setAttribute("emailForReset", email);
        session.setMaxInactiveInterval(300); // 5 phút

        try {
            new MailUtil().sendMail(email, "Mã OTP đặt lại mật khẩu", "Mã OTP của bạn là: " + otp);
            resp.sendRedirect("user/verifyOtp.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Không thể gửi email. Vui lòng thử lại.");
            req.getRequestDispatcher("user/forgotPassword.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("user/forgotPassword.jsp").forward(req, resp);
    }
}
