package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Order;
import model.User;
import model.OrderDetail;
import service.OrderService;
import service.OrderDetailService;

import java.io.IOException;
import java.util.*;

@WebServlet(name = "OrderInfoServlet", urlPatterns = {"/cartinformation"})
public class OrderInfoServlet extends HttpServlet {

    private OrderService orderService;
    private OrderDetailService orderDetailService;

    @Override
    public void init() throws ServletException {
        orderService = new OrderService();
        orderDetailService = new OrderDetailService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("user/loginUser.jsp");
            return;
        }

        // Lấy danh sách đơn hàng của người dùng
        List<Order> orders = orderService.getOrdersByUserId(user.getId());

        // Tạo map: orderId -> List<OrderDetail>
        Map<Integer, List<OrderDetail>> orderDetailsMap = new HashMap<>();
        for (Order order : orders) {
            List<OrderDetail> details = orderDetailService.getDetailsByOrderId(order.getId());
            orderDetailsMap.put(order.getId(), details);
        }

        // Gửi sang JSP
        request.setAttribute("orders", orders);
        request.setAttribute("orderDetailsMap", orderDetailsMap);
        request.getRequestDispatcher("cart/cartinformation.jsp").forward(request, response);
    }
}
