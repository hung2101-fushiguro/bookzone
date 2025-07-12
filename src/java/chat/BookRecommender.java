package chat;

import bookDao.BookDao;
import model.Book;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class BookRecommender {

    private BookDao bookDao;

    public BookRecommender(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    // Gợi ý sách theo thể loại
    public List<String> recommendByGenre(String genreKeyword) throws SQLException {
        List<Book> books = bookDao.getBooksByCategoryName(genreKeyword);
        List<String> bookTitles = new ArrayList<>();
        for (Book book : books) {
            bookTitles.add(book.getTitle());
        }
        return bookTitles;
    }

    // Gợi ý sách theo từ khóa (có thể là từ khóa trong tiêu đề, mô tả, hoặc tác giả)
    public List<String> recommendByKeyword(String keyword) throws SQLException {
        List<Book> books = bookDao.searchBooks(keyword);
        List<String> bookTitles = new ArrayList<>();
        for (Book book : books) {
            bookTitles.add(book.getTitle());
        }
        return bookTitles;
    }

    // Gợi ý sách theo tác giả
    public List<Book> recommendByAuthor(String author) throws SQLException {
        List<Book> books = bookDao.getBooksByAuthor(author);
        return books;
    }

    // Gợi ý sách theo khoảng giá
    public List<Book> recommendByPriceRange(double minPrice, double maxPrice) throws SQLException {
        List<Book> books = bookDao.getBooksByPriceRange(minPrice, maxPrice);
        return books;
    }

    // Tìm kiếm sách theo tên (title) và trả về mô tả sách
    public String recommendByTitle(String title) throws SQLException {
    List<Book> books = bookDao.searchBooks(title); // Tìm sách bằng tên
    if (books.isEmpty()) {
        return "Không tìm thấy sách với tên '" + title + "' trong kho của chúng tôi.";
    }
    Book book = books.get(0);  // Giả sử chỉ lấy cuốn sách đầu tiên nếu có nhiều kết quả
    return "Thông tin sách '" + book.getTitle() + "':<br>" +
           "Tác giả: " + book.getAuthor() + "<br>" +
           "Giá: " + book.getPrice() + " VND<br>" +
           "Mô tả: " + book.getDescription();
}


    // Tìm kiếm sách theo từ khóa trong mô tả
    public List<Book> recommendByKeywordInDescription(String keyword) throws SQLException {
        return bookDao.getBooksByKeywordInDescription(keyword);
    }

    // Tìm kiếm sách theo câu của người dùng và trả về thông tin sách nếu có
    public List<Book> searchBooksByUserInput(String userMessage) throws SQLException {
        // Lọc từ khóa trong câu người dùng
        String bookTitle = extractBookTitleFromMessage(userMessage);  // Lọc tên sách từ câu
        if (bookTitle != null) {
            return bookDao.searchBooks(bookTitle);  // Truy vấn sách theo tên
        }
        return new ArrayList<>();  // Nếu không tìm thấy tên sách, trả về danh sách trống
    }

    // Phương thức tìm tên sách trong câu người dùng
    private String extractBookTitleFromMessage(String msg) {
        List<String> allBookTitles = getAllBookTitles();  // Lấy tất cả tên sách từ DB
        for (String title : allBookTitles) {
            if (msg.toLowerCase().contains(title.toLowerCase())) {
                return title;  // Trả về tên sách nếu tìm thấy
            }
        }
        return null;  // Nếu không tìm thấy tên sách
    }

    // Lấy tất cả tên sách từ cơ sở dữ liệu
    private List<String> getAllBookTitles() {
        List<Book> books = bookDao.selectAllBook();  // Lấy tất cả sách từ cơ sở dữ liệu
        List<String> titles = new ArrayList<>();
        for (Book book : books) {
            titles.add(book.getTitle());
        }
        return titles;
    }

    // Tìm kiếm sách theo từ khóa (tên sách, tác giả, mô tả)
    public List<Book> searchBooks(String keyword) throws SQLException {
        return bookDao.searchBooks(keyword);
    }
}
