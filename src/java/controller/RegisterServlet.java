package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import service.UserService;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Tạo user mới
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);  // Nếu bạn mã hóa thì hãy hash ở đây
        newUser.setAvatarUrl(null);
        newUser.setProvider("local");
        newUser.setProviderId(null);
        newUser.setRole("customer");
        newUser.setCreatedAt(new Date());

        try {
            // Thêm user vào DB
            userService.insertUser(newUser);

            // Lấy lại user đầy đủ từ DB để có id
            User loggedInUser = userService.checkLogin(email, password);
            if (loggedInUser != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", loggedInUser);

                // Chuyển về trang chủ
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                // Đăng nhập thất bại (rất hiếm khi xảy ra)
                request.setAttribute("errorMessage", "Đăng ký xong nhưng không đăng nhập được.");
                request.getRequestDispatcher("user/registerUser.jsp").forward(request, response);
            }

        } catch (SQLException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "Đăng ký thất bại: " + ex.getMessage());
            request.getRequestDispatcher("user/registerUser.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("user/registerUser.jsp").forward(request, response);
    }
}


