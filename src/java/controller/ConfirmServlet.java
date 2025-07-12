package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.util.List;
import model.CartItem;
import model.User;

@WebServlet(name = "ConfirmServlet", urlPatterns = {"/confirm"})
public class ConfirmServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("user/loginUser.jsp");
            return;
        }

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart/cart.jsp");
            return;
        }

        request.getRequestDispatcher("cart/confirm.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Hiển thị trang xác nhận đơn hàng nếu đã đăng nhập và có giỏ hàng.";
    }
}
