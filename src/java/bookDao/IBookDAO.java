package bookDao;

import java.sql.SQLException;
import java.util.List;
import model.Book;

public interface IBookDAO {

    void insertBook(Book user) throws SQLException;

    Book selectBook(int id);

    List<Book> selectAllBook();
    
    public List<Book> selectNewestBooks(int limit);

    boolean deleteBook(int id) throws SQLException;

    boolean updateBook(Book book) throws SQLException;

    List<Book> selectBooksByPage(int offset, int limit);

    int getTotalBookCount();
    
    public List<Book> getBooksByCategory(int categoryId);
    
    public List<Book> getLatestBooksByCategory(int categoryId, int limit);

    List<Book> searchBooks(String keyword);

    List<Book> searchBooks(String keyword, String sortPrice, String sortQuantity, int offset, int limit);

    List<Book> selectBooksByPageWithSort(String sortPrice, String sortQuantity, int offset, int limit);
    
    int getSearchBookCount(String keyword);
    
    
    List<Book> getBooksByAuthor(String author);
    
    List<Book> getBooksByPriceRange(double minPrice, double maxPrice);
    
    List<Book> getBooksByKeywordInDescription(String keyword);
    
    List<Book> getBooksByCategoryName(String categoryName)throws SQLException;
}
