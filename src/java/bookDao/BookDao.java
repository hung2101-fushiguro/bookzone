package bookDao;

import dao.DBConnection;
import model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao implements IBookDAO {

    private Connection connection;  // Khai báo đối tượng Connection

    // Constructor nhận Connection từ DBConnection
    public BookDao(Connection connection) {
        this.connection = connection;
    }

    public BookDao() {

    }

    private static final String INSERT_BOOK_SQL = "INSERT INTO Books (title, author, description, price, quantity, image_url, category_id, created_at, discount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BOOK_BY_ID
            = "SELECT b.*, c.name AS category_name "
            + "FROM Books b "
            + "LEFT JOIN Categories c ON b.category_id = c.id "
            + "WHERE b.id = ?";

    private static final String SELECT_ALL_BOOK = "SELECT * FROM Books";
    private static final String DELETE_BOOK_SQL = "DELETE FROM Books WHERE id = ?";
    private static final String UPDATE_BOOK_SQL = "UPDATE Books SET title=?, author=?, description=?, price=?, quantity=?, image_url=?, category_id=?, created_at=?, discount=? WHERE id=?";
    private static final String SELECT_BOOKS_BY_PAGE = "SELECT b.*, c.name AS category_name "
            + "FROM Books b "
            + "LEFT JOIN Categories c ON b.category_id = c.id "
            + "ORDER BY b.id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String COUNT_ALL_BOOKS = "SELECT COUNT(*) FROM Books";
    private static final String SEARCH_BOOK_SQL
            = "SELECT b.*, c.name AS category_name "
            + "FROM Books b "
            + "LEFT JOIN Categories c ON b.category_id = c.id "
            + "WHERE LOWER(b.title) LIKE ? OR LOWER(b.author) LIKE ? OR LOWER(c.name) LIKE ? "
            + "ORDER BY b.id";
    private static final String SELECT_NEWEST_BOOKS
            = "SELECT b.*, c.name AS category_name "
            + "FROM Books b "
            + "LEFT JOIN Categories c ON b.category_id = c.id "
            + "ORDER BY b.created_at DESC "
            + "OFFSET 0 ROWS FETCH NEXT ? ROWS ONLY";

    private static final String SELECT_BOOKS_BY_CATEGORY
            = "SELECT b.*, c.name AS category_name "
            + "FROM Books b "
            + "LEFT JOIN Categories c ON b.category_id = c.id "
            + "WHERE b.category_id = ?";

    private static final String SELECT_LATEST_BOOKS_BY_CATEGORY
            = "SELECT b.*, c.name AS category_name "
            + "FROM Books b "
            + "LEFT JOIN Categories c ON b.category_id = c.id "
            + "WHERE b.category_id = ? "
            + "ORDER BY b.created_at DESC "
            + "OFFSET 0 ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SELECT_BOOKS_ADVANCED
            = "SELECT b.*, c.name AS category_name "
            + "FROM Books b "
            + "LEFT JOIN Categories c ON b.category_id = c.id "
            + "WHERE LOWER(b.title) LIKE ? OR LOWER(b.author) LIKE ? OR LOWER(c.name) LIKE ? "
            + "ORDER BY %s OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    private static final String SELECT_BOOKS_BY_AUTHOR
            = "SELECT b.*, c.name AS category_name "
            + "FROM Books b "
            + "LEFT JOIN Categories c ON b.category_id = c.id "
            + "WHERE LOWER(b.author) LIKE ?";

    private static final String SELECT_BOOKS_BY_PRICE_RANGE
            = "SELECT b.*, c.name AS category_name "
            + "FROM Books b "
            + "LEFT JOIN Categories c ON b.category_id = c.id "
            + "WHERE b.price BETWEEN ? AND ?";

    private static final String SELECT_BOOKS_BY_KEYWORD_IN_DESCRIPTION
            = "SELECT b.*, c.name AS category_name "
            + "FROM Books b "
            + "LEFT JOIN Categories c ON b.category_id = c.id "
            + "WHERE LOWER(b.description) LIKE ?";

    private static final String SELECT_RELATED_BOOKS
            = "SELECT b.*, c.name AS category_name "
            + "FROM Books b "
            + "LEFT JOIN Categories c ON b.category_id = c.id "
            + "WHERE b.category_id = ? AND b.id != ? "
            + "ORDER BY b.created_at DESC "
            + "OFFSET 0 ROWS FETCH NEXT ? ROWS ONLY";

    @Override
    public void insertBook(Book book) throws SQLException {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOK_SQL)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getDescription());
            preparedStatement.setDouble(4, book.getPrice());
            preparedStatement.setInt(5, book.getQuantity());
            preparedStatement.setString(6, book.getImageURL());
            preparedStatement.setInt(7, book.getCategoryID());
            preparedStatement.setDate(8, new java.sql.Date(book.getCreated_at().getTime()));
            preparedStatement.setInt(9, book.getDiscount());

            preparedStatement.executeUpdate();
        }
    }

    @Override

    public Book selectBook(int id) {
        Book book = null;
        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                book = extractBookFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public List<Book> selectAllBook() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BOOK)) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Book book = extractBookFromResultSet(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public boolean deleteBook(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_BOOK_SQL)) {

            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateBook(Book book) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK_SQL)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getDescription());
            statement.setDouble(4, book.getPrice());
            statement.setInt(5, book.getQuantity());
            statement.setString(6, book.getImageURL());
            statement.setInt(7, book.getCategoryID());
            statement.setDate(8, new java.sql.Date(book.getCreated_at().getTime()));
            statement.setInt(9, book.getDiscount());
            statement.setInt(10, book.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    private Book extractBookFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setDescription(rs.getString("description"));
        book.setPrice(rs.getDouble("price"));
        book.setQuantity(rs.getInt("quantity"));
        book.setImageURL(rs.getString("image_url"));
        book.setCategoryID(rs.getInt("category_id"));
        book.setCreated_at(rs.getDate("created_at"));
        book.setCategoryName(rs.getString("category_name"));
        book.setDiscount(rs.getInt("discount"));
        return book;
    }

    @Override
    public List<Book> selectBooksByPage(int offset, int limit) {
        List<Book> books = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOKS_BY_PAGE)) {

            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, limit);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Book book = extractBookFromResultSet(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public int getTotalBookCount() {
        int count = 0;

        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(COUNT_ALL_BOOKS)) {

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    @Override
    public List<Book> searchBooks(String keyword) {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(SEARCH_BOOK_SQL)) {

            String searchPattern = "%" + keyword.toLowerCase() + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = extractBookFromResultSet(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public int getSearchBookCount(String keyword) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM Books b "
                + "LEFT JOIN Categories c ON b.category_id = c.id "
                + "WHERE LOWER(b.title) LIKE ? OR LOWER(b.author) LIKE ? OR LOWER(c.name) LIKE ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            String pattern = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    @Override

    public List<Book> searchBooks(String keyword, String sortPrice, String sortQuantity, int offset, int limit) {
        List<Book> books = new ArrayList<>();
        StringBuilder sortClause = new StringBuilder();

        if (sortPrice != null && !sortPrice.isEmpty()) {
            sortClause.append("b.price ").append(sortPrice);
        }

        if (sortQuantity != null && !sortQuantity.isEmpty()) {
            if (sortClause.length() > 0) {
                sortClause.append(", ");
            }
            sortClause.append("b.quantity ").append(sortQuantity);
        }

        if (sortClause.length() == 0) {
            sortClause.append("b.id");
        }

        String sql = String.format(SELECT_BOOKS_ADVANCED, sortClause.toString());

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            String keywordPattern = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, keywordPattern);
            ps.setString(2, keywordPattern);
            ps.setString(3, keywordPattern);
            ps.setInt(4, offset);
            ps.setInt(5, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

     @Override
    public List<Book> selectBooksByPageWithSort(String sortPrice, String sortQuantity, int offset, int limit) {
        List<Book> books = new ArrayList<>();
        StringBuilder sortClause = new StringBuilder();

        if (sortPrice != null && !sortPrice.isEmpty()) {
            sortClause.append("b.price ").append(sortPrice);
        }

        if (sortQuantity != null && !sortQuantity.isEmpty()) {
            if (sortClause.length() > 0) {
                sortClause.append(", ");
            }
            sortClause.append("b.quantity ").append(sortQuantity);
        }

        if (sortClause.length() == 0) {
            sortClause.append("b.id");
        }

        String sql = "SELECT b.*, c.name AS category_name "
                + "FROM Books b "
                + "LEFT JOIN Categories c ON b.category_id = c.id "
                + "ORDER BY " + sortClause + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, offset);
            ps.setInt(2, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_BOOKS_BY_AUTHOR)) {
            ps.setString(1, "%" + author.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> getBooksByPriceRange(double minPrice, double maxPrice) {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_BOOKS_BY_PRICE_RANGE)) {
            ps.setDouble(1, minPrice);
            ps.setDouble(2, maxPrice);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> getBooksByKeywordInDescription(String keyword) {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_BOOKS_BY_KEYWORD_IN_DESCRIPTION)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> getBooksByCategoryName(String categoryName) throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_BOOKS_BY_CATEGORY)) {
            ps.setString(1, "%" + categoryName + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setDescription(rs.getString("description"));
                book.setPrice(rs.getDouble("price"));
                book.setQuantity(rs.getInt("quantity"));
                book.setImageURL(rs.getString("image_url"));
                book.setCategoryID(rs.getInt("category_id"));
                book.setCategoryName(rs.getString("category_name"));
                book.setCreated_at(rs.getDate("created_at"));
                books.add(book);
            }
        }
        return books;
    }

    @Override
    public List<Book> selectNewestBooks(int limit) {
        List<Book> books = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NEWEST_BOOKS)) {

            preparedStatement.setInt(1, limit);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Book book = extractBookFromResultSet(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public List<Book> getBooksByCategory(int categoryId) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOKS_BY_CATEGORY)) {

            preparedStatement.setInt(1, categoryId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Book book = extractBookFromResultSet(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> getLatestBooksByCategory(int categoryId, int limit) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection(); PreparedStatement ps = connection.prepareStatement(SELECT_LATEST_BOOKS_BY_CATEGORY)) {

            ps.setInt(1, categoryId);
            ps.setInt(2, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = extractBookFromResultSet(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> getRelatedBooks(int categoryId, int excludeBookId, int limit) {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_RELATED_BOOKS)) {

            ps.setInt(1, categoryId);
            ps.setInt(2, excludeBookId);
            ps.setInt(3, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

}
