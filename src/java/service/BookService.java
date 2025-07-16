package service;

import bookDao.IBookDAO;
import bookDao.BookDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Book;

/**
 *
 * @author DELL
 */
public class BookService implements IBookService {

    private IBookDAO bookDao;
    private Connection connection;  // Khai báo đối tượng Connection

    // Constructor nhận Connection từ DBConnection
    public BookService() {
        this.bookDao = new BookDao(connection);
    }

    @Override
    public void addBook(Book book) throws SQLException {
        bookDao.insertBook(book);
    }

    @Override
    public Book selectBook(int id) {
        return bookDao.selectBook(id);
    }

    @Override
    public List<Book> getAllbook() {
        return bookDao.selectAllBook();
    }

    @Override
    public boolean deleteBook(int id) throws SQLException {
        return bookDao.deleteBook(id);
    }

    @Override
    public boolean updateBook(Book book) throws SQLException {
        return bookDao.updateBook(book);
    }

    @Override
    public int getTotalBookCount() {
        return bookDao.getTotalBookCount();
    }

    @Override
    public List<Book> selectBooksByPage(int offset, int limit) {
        return bookDao.selectBooksByPage(offset, limit);
    }

    @Override
    public List<Book> searchBooks(String keyword) {
        return bookDao.searchBooks(keyword);
    }

    @Override
    public List<Book> selectBooksByPageWithSort(String sortPrice, String sortQuantity, int offset, int limit) {
        return bookDao.selectBooksByPageWithSort(sortPrice, sortQuantity, offset, limit);
    }

    @Override
    public List<Book> searchBooks(String keyword, String sortPrice, String sortQuantity, int offset, int limit) {
        return bookDao.searchBooks(keyword, sortPrice, sortQuantity, offset, limit);
    }

    @Override
    public int getSearchBookCount(String keyword) {
        return bookDao.getSearchBookCount(keyword);
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return bookDao.getBooksByAuthor(author);
    }

    @Override
    public List<Book> getBooksByPriceRange(double minPrice, double maxPrice) {
        return bookDao.getBooksByPriceRange(minPrice, maxPrice);
    }

    @Override
    public List<Book> getBooksByKeywordInDescription(String keyword) {
        return bookDao.getBooksByKeywordInDescription(keyword);
    }

    @Override
    public List<Book> getBooksByCategoryName(String categoryName) {
        try {
            return bookDao.getBooksByCategoryName(categoryName);
        } catch (SQLException ex) {
            Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Book> selectNewestBooks(int limit) {
        return bookDao.selectNewestBooks(limit);
    }

    @Override
    public List<Book> getBooksByCategory(int categoryId) {
        return bookDao.getBooksByCategory(categoryId);
    }

    @Override
    public List<Book> getLatestBooksByCategory(int categoryId, int limit) {
        return bookDao.getLatestBooksByCategory(categoryId, limit);
    }

    @Override
    public List<Book> getRelatedBooks(int categoryId, int excludeBookId, int limit) {
        return bookDao.getRelatedBooks(categoryId, excludeBookId, limit);
    }

}
