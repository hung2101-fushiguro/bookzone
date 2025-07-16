package commentDao;

import dao.DBConnection;
import model.Comment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDao implements ICommentDAO {

    private static final String INSERT_BOOK_COMMENT_SQL =
        "INSERT INTO Comments (book_id, user_id, content, rating, created_at) VALUES (?, ?, ?, ?, ?)";

    private static final String INSERT_ACCESSORY_COMMENT_SQL =
        "INSERT INTO Comments (accessory_id, user_id, content, rating, created_at) VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_COMMENTS_BY_BOOK_ID_SQL =
        "SELECT c.*, u.name AS user_name " +
        "FROM Comments c " +
        "JOIN Users u ON c.user_id = u.id " +
        "WHERE c.book_id = ? " +
        "ORDER BY c.created_at DESC";

    private static final String SELECT_COMMENTS_BY_ACCESSORY_ID_SQL =
        "SELECT c.*, u.name AS user_name " +
        "FROM Comments c " +
        "JOIN Users u ON c.user_id = u.id " +
        "WHERE c.accessory_id = ? " +
        "ORDER BY c.created_at DESC";

    private static final String SELECT_COMMENT_BY_ID_SQL =
        "SELECT * FROM Comments WHERE id = ?";

    private static final String UPDATE_COMMENT_SQL =
        "UPDATE Comments SET content = ?, rating = ? WHERE id = ? AND user_id = ?";

    private static final String DELETE_COMMENT_SQL =
        "DELETE FROM Comments WHERE id = ? AND user_id = ?";

    private static final String SELECT_COMMENTS_BY_BOOK_PAGED =
        "SELECT c.*, u.name AS user_name " +
        "FROM Comments c " +
        "JOIN Users u ON c.user_id = u.id " +
        "WHERE c.book_id = ? " +
        "ORDER BY c.created_at DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    private static final String COUNT_COMMENTS_BY_BOOK =
        "SELECT COUNT(*) FROM Comments WHERE book_id = ?";

    private static final String SELECT_COMMENTS_BY_ACCESSORY_PAGED =
        "SELECT c.*, u.name AS user_name " +
        "FROM Comments c " +
        "JOIN Users u ON c.user_id = u.id " +
        "WHERE c.accessory_id = ? " +
        "ORDER BY c.created_at DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    private static final String COUNT_COMMENTS_BY_ACCESSORY =
        "SELECT COUNT(*) FROM Comments WHERE accessory_id = ?";

    @Override
    public void addComment(Comment comment) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps;

            if (comment.getBookId() > 0) {
                ps = conn.prepareStatement(INSERT_BOOK_COMMENT_SQL);
                ps.setInt(1, comment.getBookId());
            } else if (comment.getAccessoryId() > 0) {
                ps = conn.prepareStatement(INSERT_ACCESSORY_COMMENT_SQL);
                ps.setInt(1, comment.getAccessoryId());
            } else {
                throw new IllegalArgumentException("Comment must have either bookId or accessoryId.");
            }

            ps.setInt(2, comment.getUserId());
            ps.setString(3, comment.getContent());
            ps.setInt(4, comment.getRating());
            ps.setTimestamp(5, comment.getCreatedAt());
            ps.executeUpdate();
        }
    }

    @Override
    public List<Comment> getCommentsByBookId(int bookId) throws Exception {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_COMMENTS_BY_BOOK_ID_SQL)) {

            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                comments.add(extractComment(rs));
            }
        }
        return comments;
    }

    @Override
    public List<Comment> selectCommentsByAccessoryId(int accessoryId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_COMMENTS_BY_ACCESSORY_ID_SQL)) {

            ps.setInt(1, accessoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                comments.add(extractComment(rs));
            }
        }
        return comments;
    }

    @Override
    public Comment getCommentById(int commentId) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_COMMENT_BY_ID_SQL)) {

            ps.setInt(1, commentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractComment(rs);
            }
        }
        return null;
    }

    @Override
    public void updateComment(Comment comment) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_COMMENT_SQL)) {

            ps.setString(1, comment.getContent());
            ps.setInt(2, comment.getRating());
            ps.setInt(3, comment.getCommentId());
            ps.setInt(4, comment.getUserId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteComment(int commentId, int userId) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_COMMENT_SQL)) {

            ps.setInt(1, commentId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Comment> getCommentsByBookIdPaged(int bookId, int page, int pageSize) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_COMMENTS_BY_BOOK_PAGED)) {

            int offset = (page - 1) * pageSize;
            ps.setInt(1, bookId);
            ps.setInt(2, offset);
            ps.setInt(3, pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                comments.add(extractComment(rs));
            }
        }
        return comments;
    }

    @Override
    public int countCommentsByBookId(int bookId) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(COUNT_COMMENTS_BY_BOOK)) {

            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    @Override
    public List<Comment> getCommentsByAccessoryIdPaged(int accessoryId, int page, int pageSize) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_COMMENTS_BY_ACCESSORY_PAGED)) {

            int offset = (page - 1) * pageSize;
            ps.setInt(1, accessoryId);
            ps.setInt(2, offset);
            ps.setInt(3, pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                comments.add(extractComment(rs));
            }
        }
        return comments;
    }

    @Override
    public int countCommentsByAccessoryId(int accessoryId) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(COUNT_COMMENTS_BY_ACCESSORY)) {

            ps.setInt(1, accessoryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    private Comment extractComment(ResultSet rs) throws SQLException {
        Comment comment = new Comment();
        comment.setCommentId(rs.getInt("id"));
        comment.setBookId(rs.getInt("book_id"));
        comment.setAccessoryId(rs.getInt("accessory_id"));
        comment.setUserId(rs.getInt("user_id"));
        comment.setUserName(rs.getString("user_name"));
        comment.setContent(rs.getString("content"));
        comment.setRating(rs.getInt("rating"));
        comment.setCreatedAt(rs.getTimestamp("created_at"));
        return comment;
    }
}
