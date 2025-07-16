package service;

import java.util.List;
import model.Comment;

public interface ICommentService {

    // Thêm bình luận (sách hoặc phụ kiện)
    void addComment(Comment comment) throws Exception;

    // Lấy danh sách bình luận theo sách
    List<Comment> getCommentsByBookId(int bookId) throws Exception;

    // Lấy danh sách bình luận theo phụ kiện
    List<Comment> getCommentsByAccessoryId(int accessoryId) throws Exception;

    // Lấy 1 bình luận theo ID
    Comment getCommentById(int commentId) throws Exception;

    // Cập nhật nội dung và đánh giá của bình luận
    void updateComment(Comment comment) throws Exception;

    // Xóa bình luận
    void deleteComment(int commentId, int userId) throws Exception;

    // ✅ Phân trang bình luận theo sách
    List<Comment> getCommentsByBookIdPaged(int bookId, int page, int pageSize) throws Exception;

    // ✅ Đếm tổng số bình luận sách
    int countCommentsByBookId(int bookId) throws Exception;

    // ✅ Phân trang bình luận theo phụ kiện
    List<Comment> getCommentsByAccessoryIdPaged(int accessoryId, int page, int pageSize) throws Exception;

    // ✅ Đếm tổng số bình luận phụ kiện
    int countCommentsByAccessoryId(int accessoryId) throws Exception;

}
