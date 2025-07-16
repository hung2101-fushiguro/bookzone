
package service;

import commentDao.CommentDao;
import commentDao.ICommentDAO;
import java.util.List;
import model.Comment;
import java.sql.SQLException;


public class CommentService implements ICommentService {

    private final ICommentDAO commentDAO;

    public CommentService() {
        this.commentDAO = new CommentDao();
    }

    @Override
    public void addComment(Comment comment) throws Exception {
        commentDAO.addComment(comment);
    }

    @Override
    public List<Comment> getCommentsByBookId(int bookId) throws Exception {
        return commentDAO.getCommentsByBookId(bookId);
    }

    @Override
    public List<Comment> getCommentsByAccessoryId(int accessoryId) throws Exception {
        return commentDAO.selectCommentsByAccessoryId(accessoryId);
    }

    @Override
    public Comment getCommentById(int commentId) throws Exception {
        return commentDAO.getCommentById(commentId);
    }

    @Override
    public void updateComment(Comment comment) throws Exception {
        commentDAO.updateComment(comment);
    }

    @Override
    public void deleteComment(int commentId, int userId) throws Exception {
        commentDAO.deleteComment(commentId, userId);
    }

    @Override
    public List<Comment> getCommentsByBookIdPaged(int bookId, int page, int pageSize) throws Exception {
        return commentDAO.getCommentsByBookIdPaged(bookId, page, pageSize);
    }

    @Override
    public int countCommentsByBookId(int bookId) throws Exception {
        return commentDAO.countCommentsByBookId(bookId);
    }

    @Override
    public List<Comment> getCommentsByAccessoryIdPaged(int accessoryId, int page, int pageSize) throws Exception {
        return commentDAO.getCommentsByAccessoryIdPaged(accessoryId, page, pageSize);
    }

    @Override
    public int countCommentsByAccessoryId(int accessoryId) throws Exception {
        return commentDAO.countCommentsByAccessoryId(accessoryId);
    }

}
