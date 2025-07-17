package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import model.CartItem;
import model.Order;
import model.User;

import service.IMailService;
import service.MailService;
import service.OrderService;
import service.UserService;

@WebServlet(name = "AddressPhoneServlet", urlPatterns = {"/update-contact"})
public class AddressPhoneServlet extends HttpServlet {

    private UserService userService;
    private IMailService mailService = new MailService();

    @Override
    public void init() throws ServletException {
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
        String paymentMethod = request.getParameter("paymentMethod");

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

                // Tạo đơn hàng
                Order order = new Order();
                order.setUserId(user.getId());
                order.setTotalPrice(total.doubleValue());
                order.setStatus("đang xử lý");
                new OrderService().createOrderWithDetails(order, processingItems);

                session.setAttribute("lastOrder", order);
                session.setAttribute("lastCart", processingItems);
                session.setAttribute("orderCompleted", true); // dùng để clear cart ở CartServlet nếu cần

                // Gửi mail xác nhận đơn hàng
                mailService.sendOrderConfirmation(user, order.getId());

                // Điều hướng theo phương thức thanh toán
                if ("COD".equalsIgnoreCase(paymentMethod)) {
                    session.setAttribute("lastPaymentMethod", "COD");
                    response.sendRedirect("cart/thankyou.jsp");
                } else if ("VNPAY".equalsIgnoreCase(paymentMethod)) {
                    session.setAttribute("qrImage", "image/vnpay_qr.png");
                    session.setAttribute("payMethod", "VNPAY");
                    session.setAttribute("lastPaymentMethod", "VNPAY");
                    response.sendRedirect("cart/payment.jsp");
                } else if ("MOMO".equalsIgnoreCase(paymentMethod)) {
                    String momoLink = "https://me.momo.vn/thanhtoanBookZone"
                            + "?amount=" + total.intValue()
                            + "&comment=Thanh%20toan%20don%20hang%20BookZone";

                    session.setAttribute("qrImage", momoLink);
                    session.setAttribute("payMethod", "MOMO");
                    session.setAttribute("lastPaymentMethod", "MOMO");
                    response.sendRedirect("cart/payment.jsp");
                } else {
                    response.sendRedirect("error.jsp");
                }

                return;
            }

            response.sendRedirect("error.jsp"); // fallback

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
