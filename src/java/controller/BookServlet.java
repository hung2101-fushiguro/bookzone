package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Book;
import service.BookService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale.Category;
import service.CategoryService;

@WebServlet(name = "BookServlet", urlPatterns = {"/books"})
public class BookServlet extends HttpServlet {

    private BookService bookService;
    private CategoryService categoryService;

    @Override

    public void init() {
        bookService = new BookService();
        categoryService = new CategoryService(); // ← thêm dòng này
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create":
                    showCreateForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteBook(request, response);
                    break;
                default:
                    String keyword = request.getParameter("keyword");
                    if (keyword != null && !keyword.trim().isEmpty()) {
                        searchBooks(request, response);
                    } else {
                        listBooks(request, response);
                    }
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create":
                    insertBook(request, response);
                    break;
                case "edit":
                    updateBook(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        // Lấy tham số tìm kiếm và sắp xếp
        String keyword = request.getParameter("keyword");
        String sortPrice = request.getParameter("sortPrice");
        String sortQuantity = request.getParameter("sortQuantity");

        // Phân trang
        int page = 1;
        int recordsPerPage = 10;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        int offset = (page - 1) * recordsPerPage;

        List<Book> books;
        int totalBooks;

        // Nếu có keyword tìm kiếm
        if (keyword != null && !keyword.trim().isEmpty()) {
            books = bookService.searchBooks(keyword.trim(), sortPrice, sortQuantity, offset, recordsPerPage);
            totalBooks = bookService.getSearchBookCount(keyword.trim());
        } else {
            // Không có keyword => truyền "" vào để áp dụng sắp xếp + phân trang
            books = bookService.searchBooks("", sortPrice, sortQuantity, offset, recordsPerPage);
            totalBooks = bookService.getTotalBookCount();
        }

        int totalPages = (int) Math.ceil(totalBooks * 1.0 / recordsPerPage);

        // Gửi dữ liệu tới JSP
        request.setAttribute("books", books);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", keyword);
        request.setAttribute("sortPrice", sortPrice);
        request.setAttribute("sortQuantity", sortQuantity);

        RequestDispatcher dispatcher = request.getRequestDispatcher("book/bookList.jsp");
        dispatcher.forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<model.Category> categories = categoryService.selectAllCategories();
        request.setAttribute("categories", categories);

        RequestDispatcher rd = request.getRequestDispatcher("book/createBook.jsp");
        rd.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book existingBook = bookService.selectBook(id);
        List<model.Category> categories = categoryService.selectAllCategories();

        request.setAttribute("book", existingBook);
        request.setAttribute("categories", categories);

        RequestDispatcher rd = request.getRequestDispatcher("book/editBook.jsp");
        rd.forward(request, response);
    }

    private void insertBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String img_url = request.getParameter("img_url");
        int cateId = Integer.parseInt(request.getParameter("cateId"));
        String importDateStr = request.getParameter("dateadd");
        java.sql.Date dateadd = java.sql.Date.valueOf(importDateStr);
        int discount = Integer.parseInt(request.getParameter("discount"));

        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthor(author);
        newBook.setDescription(description);
        newBook.setPrice(price);
        newBook.setQuantity(quantity);
        newBook.setImageURL(img_url);
        newBook.setCategoryID(cateId);
        newBook.setCreated_at(dateadd);
        newBook.setDiscount(discount);

        bookService.addBook(newBook);
        response.sendRedirect("books");
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        bookService.deleteBook(id);
        response.sendRedirect("books");
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String img_url = request.getParameter("img_url");
        int cateId = Integer.parseInt(request.getParameter("cateId"));
        String importDateStr = request.getParameter("dateadd");
        java.sql.Date dateadd = java.sql.Date.valueOf(importDateStr);
        int discount = Integer.parseInt(request.getParameter("discount"));

        Book book = new Book(id, title, author, description, price, quantity, img_url, cateId, dateadd, discount);
        bookService.updateBook(book);
        response.sendRedirect("books");
    }

    private void searchBooks(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String keyword = request.getParameter("keyword");
        if (keyword == null || keyword.trim().isEmpty()) {
            response.sendRedirect("books");
            return;
        }

        List<Book> books = bookService.searchBooks(keyword.trim());

        request.setAttribute("books", books);
        request.setAttribute("keyword", keyword); // giữ lại từ khóa tìm kiếm
        request.setAttribute("currentPage", 1);
        request.setAttribute("totalPages", 1); // không phân trang

        RequestDispatcher dispatcher = request.getRequestDispatcher("book/bookList.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý thao tác sách";
    }

}
