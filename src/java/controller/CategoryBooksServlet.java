package controller;

import bookDao.BookDao;
import model.Book;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import model.Category;
import service.CategoryService;

@WebServlet(name = "CategoryBooksServlet", urlPatterns = {"/categorybooks"})
public class CategoryBooksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        BookDao bookDAO = new BookDao();
        List<Book> books = bookDAO.getBooksByCategory(categoryId);

        request.setAttribute("books", books);
        CategoryService categoryService = new CategoryService();
        Category category = categoryService.selectCategoryById(categoryId);

        if (category != null) {
            request.setAttribute("categoryName", category.getName());
        } else {
            request.setAttribute("categoryName", "Danh mục không tồn tại");
        }

        request.getRequestDispatcher("/book/categoryBooks.jsp").forward(request, response);
    }
}
