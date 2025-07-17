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
import java.util.List;
import model.CartItem;
import model.Order;
import model.User;
import service.BookService;
import service.IBookService;
import service.IOrderService;
import service.OrderService;

/**
 *
 * @author DELL
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

    private IOrderService orderService = new OrderService();
    private IBookService bookService = new BookService();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CheckoutServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckoutServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/cart/confirm.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String paymentMethod = request.getParameter("paymentMethod");

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        // Trường hợp thiếu thông tin
        if (phone == null || phone.trim().isEmpty() || address == null || address.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ địa chỉ và số điện thoại.");
            request.setAttribute("cart", cart); // Gán lại giỏ hàng
            request.getRequestDispatcher("/cart/confirm.jsp").forward(request, response);
            return;
        }

        // Cập nhật thông tin người dùng
        user.setPhone(phone);
        user.setAddress(address);
        session.setAttribute("user", user);

        if ("update".equals(action)) {
            request.setAttribute("error", "Thông tin đã được cập nhật.");
            request.setAttribute("cart", cart); // Gán lại giỏ hàng
            request.getRequestDispatcher("/cart/confirm.jsp").forward(request, response);
            return;
        }

        if ("confirm".equals(action)) {
            if (cart == null || cart.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart/cart.jsp");
                return;
            }

            double total = cart.stream()
                    .mapToDouble(item -> item.getBook().getPrice() * item.getQuantity())
                    .sum();

            for (CartItem item : cart) {
                item.setStatus("đang xử lý");
            }

            Order order = new Order();
            order.setUserId(user.getId());
            order.setTotalPrice(total);
            order.setStatus("Đang xử lý");

            orderService.createOrderWithDetails(order, cart);

            session.setAttribute("lastOrder", order);
            session.setAttribute("lastCart", cart);
            session.setAttribute("orderCompleted", true);
            session.setAttribute("lastPaymentMethod", paymentMethod);

            if ("VNPAY".equalsIgnoreCase(paymentMethod)) {
                response.sendRedirect("cart/payment_vnpay.jsp");
            } else if ("MOMO".equalsIgnoreCase(paymentMethod)) {
                response.sendRedirect("cart/payment_momo.jsp");
            } else {
                response.sendRedirect("cart/payment_cod.jsp");
            }
            return;
        }

        response.sendRedirect(request.getContextPath() + "/cart/confirm.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý xác nhận đơn hàng và thanh toán";
    }
}
