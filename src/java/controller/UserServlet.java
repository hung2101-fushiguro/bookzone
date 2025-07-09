
package controller;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import model.User;
import service.UserService;


@WebServlet(name = "UserServlet", urlPatterns = {"/user"})
public class UserServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    showAddform(request, response);
                    break;
                case "update":
                    showUpdateform(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                default:
                    listUser(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    addUser(request, response);
                    break;
                case "update":
                    updateUser(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);

        }
    }



    private void addUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setAvatarUrl(null); // hoặc URL ảnh mặc định
        newUser.setProvider("local");
        newUser.setProviderId(null);
        newUser.setRole("customer");
        newUser.setCreatedAt(new java.util.Date());

        userService.addUser(newUser);
        response.sendRedirect("home.jsp");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String avatarUrl = request.getParameter("avatar_url"); // có thể null nếu không thay ảnh
        String provider = request.getParameter("provider");     // thường là "local"
        String providerId = request.getParameter("provider_id");
        String role = request.getParameter("role");             // ví dụ: admin, customer

        // Nếu form không có created_at, ta dùng ngày hiện tại
        java.util.Date createdAt = new java.util.Date();

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setAvatarUrl(avatarUrl);
        user.setProvider(provider);
        user.setProviderId(providerId);
        user.setRole(role);
        user.setCreatedAt(createdAt);

        userService.updateUser(user);  // hoặc gọi DAO trực tiếp nếu không dùng service
        response.sendRedirect("user?action=list");  // chuyển về danh sách user
    }

    private void showAddform(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/registerUser.jsp"); // Trang JSP để nhập user
        dispatcher.forward(request, response);
    }

    private void showUpdateform(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userService.selectUser(id);
        request.setAttribute("user", existingUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp"); // Dùng lại form
        dispatcher.forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userService.deleteUser(id);
        response.sendRedirect("user?action=list"); // Quay lại danh sách
    }

    private void listUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<User> userList = userService.selectAllUsers();
        request.setAttribute("userList", userList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
        dispatcher.forward(request, response);
    }

}


