package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.util.ArrayList;
import java.util.List;
import model.CartItem;
import model.Order;
import model.User;
import java.math.BigDecimal;
import service.OrderService;
import service.UserService;

@WebServlet(name = "AddressPhoneServlet", urlPatterns = {"/update-contact"})
public class AddressPhoneServlet extends HttpServlet {

    private UserService userService ;
    
    @Override
    public void init() throws ServletException{
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("user/loginUser.jsp");
            return;
        }

        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String action = request.getParameter("action");

        user.setAddress(address);
        user.setPhone(phone);

        try {
            // Cập nhật DB
            userService.updateUser(user);
            session.setAttribute("user", user);

            if ("update".equals(action)) {
                response.sendRedirect("cart/confirm.jsp");
                return;
            }

            if ("confirm".equals(action)) {
                // Lấy giỏ hàng hiện tại
                List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
                if (cart == null) {
                    cart = new ArrayList<>();
                }

                List<CartItem> processingItems = new ArrayList<>();
                BigDecimal total = BigDecimal.ZERO;

                for (CartItem item : cart) {
                    if ("chưa thanh toán".equals(item.getStatus())) {
                        item.setStatus("đang xử lý");
                        processingItems.add(item);
                        BigDecimal itemTotal = BigDecimal.valueOf(item.getBook().getPrice())
                                .multiply(BigDecimal.valueOf(item.getQuantity()));
                        total = total.add(itemTotal);
                    }
                }

                // Tạo đơn hàng giả lập để hiển thị
                Order order = new Order();
                order.setUserId(user.getId());
                order.setTotalPrice(total.doubleValue());
                order.setStatus("đang xử lý");
                new OrderService().createOrderWithDetails(order, processingItems);

                session.setAttribute("lastOrder", order);
                session.setAttribute("lastCart", processingItems);
                session.setAttribute("orderCompleted", true); // dùng để clear cart ở CartServlet nếu cần

                response.sendRedirect("cart/thankyou.jsp");
            } else {
                response.sendRedirect("error.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
