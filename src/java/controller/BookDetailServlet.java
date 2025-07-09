package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import model.Book;
import service.BookService;

@WebServlet(name = "BookDetailServlet", urlPatterns = {"/bookdetail"})
public class BookDetailServlet extends HttpServlet {

    private BookService bookService;

    @Override
    public void init() throws ServletException {
        bookService = new BookService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect("home.jsp");
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("home.jsp");
            return;
        }

        Book book = bookService.selectBook(bookId);
        if (book == null) {
            response.sendRedirect("home.jsp");
            return;
        }

        request.setAttribute("book", book);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/book/bookDetail.jsp");
        dispatcher.forward(request, response);
    }
}
