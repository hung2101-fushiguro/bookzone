package controller;

import model.CartItem;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Book;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import service.BookService;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    private BookService bookService;

    @Override
    public void init() throws ServletException {
        bookService = new BookService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Boolean orderCompleted = (Boolean) session.getAttribute("orderCompleted");
        if (orderCompleted != null && orderCompleted) {
            session.removeAttribute("orderCompleted");
            session.removeAttribute("cart");
        }

        String action = request.getParameter("action");
        String bookIdStr = request.getParameter("bookId");

        if (action != null && bookIdStr != null) {
            try {
                int bookId = Integer.parseInt(bookIdStr);
                List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

                if (cart != null) {
                    if ("remove".equals(action)) {
                        cart.removeIf(item -> item.getBook().getId() == bookId && "chưa thanh toán".equals(item.getStatus()));
                    } else if ("cancel".equals(action)) {
                        cart.removeIf(item -> item.getBook().getId() == bookId && "đang xử lý".equals(item.getStatus()));
                    }
                    session.setAttribute("cart", cart);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid book ID");
                return;
            }
        }

        response.sendRedirect("cart/cart.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Boolean orderCompleted = (Boolean) session.getAttribute("orderCompleted");
        if (orderCompleted != null && orderCompleted) {
            session.removeAttribute("orderCompleted");
            session.removeAttribute("cart");
        }

        try {
            String action = request.getParameter("action");
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            int quantity = 1;
            if (request.getParameter("quantity") != null) {
                quantity = Integer.parseInt(request.getParameter("quantity"));
            }

            Book book = bookService.selectBook(bookId);
            if (book == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Book not found");
                return;
            }

            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart == null) {
                cart = new ArrayList<>();
            }

            boolean found = false;
            Iterator<CartItem> iterator = cart.iterator();
            while (iterator.hasNext()) {
                CartItem item = iterator.next();
                if (item.getBook().getId() == bookId && "chưa thanh toán".equals(item.getStatus())) {
                    found = true;

                    if ("increase".equals(action)) {
                        item.setQuantity(item.getQuantity() + 1);
                    } else if ("decrease".equals(action)) {
                        if (item.getQuantity() > 1) {
                            item.setQuantity(item.getQuantity() - 1);
                        } else {
                            iterator.remove();
                        }
                    } else {
                        item.setQuantity(item.getQuantity() + quantity);
                    }
                    break;
                }
            }

            if (!found && !"decrease".equals(action)) {
                cart.add(new CartItem(book, quantity, "chưa thanh toán"));
            }

            session.setAttribute("cart", cart);
            response.sendRedirect(request.getContextPath() + "/cart");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input");
        }
    }
}
