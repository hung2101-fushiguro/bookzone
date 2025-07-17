package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.User;
import model.Order;
import model.CartItem;
import service.IMailService;
import service.MailService;
import service.OrderService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/confirm-payment")
public class ConfirmPaymentServlet extends HttpServlet {

    private IMailService mailService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        mailService = new MailService();
        orderService = new OrderService();
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

        try {
            // Tạo đơn hàng
            Order order = new Order();
            order.setUserId(user.getId());
            order.setTotalPrice(total.doubleValue());
            order.setStatus("đang xử lý");

            new OrderService().createOrderWithDetails(order, processingItems);

            // Gửi mail xác nhận
            new MailService().sendOrderConfirmation(user, order.getId());

            // Lưu vào session
            session.setAttribute("lastOrder", order);
            session.setAttribute("lastCart", processingItems);

            // 💡 Lưu phương thức thanh toán nếu có trong request
            String paymentMethod = request.getParameter("paymentMethod"); // eg. "COD", "MOMO", "VNPAY"
            session.setAttribute("lastPaymentMethod", paymentMethod);

            // Nếu cần, bạn có thể xóa cart
            // session.removeAttribute("cart");
            response.sendRedirect("cart/thankyou.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

}
