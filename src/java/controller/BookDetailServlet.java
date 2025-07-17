package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.List;
import model.Book;
import service.BookService;
import service.CommentService;
import model.Comment;

@WebServlet(name = "BookDetailServlet", urlPatterns = {"/bookdetail"})
public class BookDetailServlet extends HttpServlet {

    private BookService bookService;
    private CommentService commentService;

    @Override
    public void init() throws ServletException {
        bookService = new BookService();
        commentService = new CommentService();
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

        // Gợi ý sách cùng loại
        List<Book> relatedBooks = bookService.getRelatedBooks(
                book.getCategoryID(),
                book.getId(),
                5
        );
        request.setAttribute("relatedBooks", relatedBooks);

        // Phân trang bình luận
        int page = 1;
        String pageParam = request.getParameter("commentPage");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException ignored) {
            }
        }
        int pageSize = 5;

        try {
            List<Comment> comments = commentService.getCommentsByBookIdPaged(bookId, page, pageSize);
            int totalComments = commentService.countCommentsByBookId(bookId);
            int totalPages = (int) Math.ceil(totalComments / (double) pageSize);

            request.setAttribute("comments", comments);
            request.setAttribute("commentPage", page);
            request.setAttribute("commentTotalPages", totalPages);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("comments", null);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/book/bookDetail.jsp");
        dispatcher.forward(request, response);
    }

}
