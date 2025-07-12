package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Book;
import model.Category;
import service.BookService;
import service.CategoryService;
import service.IBookService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy danh mục sách
        CategoryService categoryService = new CategoryService();
        List<Category> categories = categoryService.selectAllCategories();
        request.setAttribute("categories", categories);

        // Lấy sách mới nhất toàn trang
        IBookService bookService = new BookService();
        List<Book> newBooks = bookService.selectNewestBooks(10);
        request.setAttribute("newBooks", newBooks);

        // Lấy 4 cuốn mới nhất cho từng danh mục
        for (Category category : categories) {
            List<Book> books = bookService.getLatestBooksByCategory(category.getId(), 5);
            request.setAttribute("categoryBooks_" + category.getId(), books);
        }

        // Forward đến home.jsp
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "HomeServlet - hiển thị trang chủ với sách mới và sách theo từng danh mục";
    }
}
