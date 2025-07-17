package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "ConfirmPaymentServlet", urlPatterns = {"/confirm-payment"})
public class ConfirmPaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Sau khi thanh toán xong, chuyển đến trang cảm ơn
        response.sendRedirect("cart/thankyou.jsp");
    }
}
