package commentDao;

import java.util.List;
import model.Comment;
import java.sql.SQLException;

public interface ICommentDAO {

    // Thêm bình luận cho sách hoặc phụ kiện
    void addComment(Comment comment) throws Exception;

    // Lấy danh sách bình luận theo sách (full không phân trang)
    List<Comment> getCommentsByBookId(int bookId) throws Exception;

    // Lấy danh sách bình luận theo phụ kiện (full không phân trang)
    List<Comment> selectCommentsByAccessoryId(int accessoryId) throws SQLException;

    // Lấy 1 bình luận theo ID
    Comment getCommentById(int commentId) throws Exception;

    // Cập nhật bình luận
    void updateComment(Comment comment) throws Exception;

    // Xóa bình luận (kiểm tra userId)
    void deleteComment(int commentId, int userId) throws Exception;

    // ✅ Phân trang bình luận theo sách
    List<Comment> getCommentsByBookIdPaged(int bookId, int page, int pageSize) throws SQLException;

    // ✅ Đếm tổng số bình luận theo sách
    int countCommentsByBookId(int bookId) throws SQLException;

    // ✅ Phân trang bình luận theo phụ kiện
    List<Comment> getCommentsByAccessoryIdPaged(int accessoryId, int page, int pageSize) throws SQLException;

    // ✅ Đếm tổng số bình luận theo phụ kiện
    int countCommentsByAccessoryId(int accessoryId) throws SQLException;

}
