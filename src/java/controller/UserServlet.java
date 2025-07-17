package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "delete":
                    deleteUser(request, response);
                    break;
                case "profile":
                    showProfile(request, response);
                    break;
                default:
                    listUsers(request, response);
                    break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "updateProfile":
                    updateProfile(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/home");
                    break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> users = userService.selectAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/user/listUser.jsp").forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        userService.deleteUser(id);
        response.sendRedirect(request.getContextPath() + "/user");
    }

    private void showProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = userService.selectUser(sessionUser.getId());
        request.setAttribute("user", user);
        request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
    }

    private void updateProfile(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {
        HttpSession session = request.getSession(false);
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");

        sessionUser.setName(name);
        sessionUser.setAddress(address);
        sessionUser.setPhone(phone);
        sessionUser.setUsername(username);

        if (password != null && !password.trim().isEmpty()) {
            sessionUser.setPassword(password);
        }

        userService.updateUser(sessionUser);

        // Cập nhật lại từ DB
        User updatedUser = userService.selectUser(sessionUser.getId());
        session.setAttribute("user", updatedUser);

        response.sendRedirect(request.getContextPath() + "/user?action=profile");
    }
}
