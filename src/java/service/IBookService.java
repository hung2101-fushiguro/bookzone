package service;

import java.sql.SQLException;
import java.util.List;
import model.Book;

public interface IBookService {
    
    void addBook(Book book) throws SQLException;
    
    Book selectBook(int id);
    
    List<Book> getAllbook();
    
    boolean deleteBook(int id) throws SQLException;
    
    boolean updateBook(Book book) throws SQLException;
    
    int getTotalBookCount();
    
    List<Book> selectBooksByPage(int offset, int limit);
    
    public List<Book> selectNewestBooks(int limit) ;
    
    public List<Book> getBooksByCategory(int categoryId) ;
    
    public List<Book> getLatestBooksByCategory(int categoryId, int limit);
    
    List<Book> searchBooks(String keyword);
    
    List<Book> searchBooks(String keyword, String sortPrice, String sortQuantity, int offset, int limit);

    List<Book> selectBooksByPageWithSort(String sortPrice, String sortQuantity, int offset, int limit);
    
    int getSearchBookCount(String keyword);
    
    public List<Book> getBooksByCategoryName(String categoryName);
    
    List<Book> getBooksByAuthor(String author);
    
    List<Book> getBooksByPriceRange(double minPrice, double maxPrice);
    
    List<Book> getBooksByKeywordInDescription(String keyword);
   
}
