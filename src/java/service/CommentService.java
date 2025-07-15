/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import commentDao.CommentDao;
import commentDao.ICommentDAO;
import java.util.List;
import model.Comment;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class CommentService implements ICommentService {

    private ICommentDAO commentDao;

    public CommentService() {
        this.commentDao = new CommentDao();
    }

    @Override
    public void addComment(Comment comment) throws Exception {
        commentDao.addComment(comment);
    }

    @Override
    public List<Comment> getCommentsByBookId(int bookId) throws Exception {
        return commentDao.getCommentsByBookId(bookId);
    }

    @Override
    public Comment getCommentById(int commentId) throws Exception {
        return commentDao.getCommentById(commentId);
    }

    @Override
    public void updateComment(Comment comment) throws Exception {
        commentDao.updateComment(comment);
    }

    @Override
    public void deleteComment(int commentId, int userId) throws Exception {
        commentDao.deleteComment(commentId, userId);
    }

    public List<Comment> getCommentsByBookIdPaged(int bookId, int page, int pageSize) throws SQLException {
        return commentDao.getCommentsByBookIdPaged(bookId, page, pageSize);
    }

    public int countCommentsByBookId(int bookId) throws SQLException {
        return commentDao.countCommentsByBookId(bookId);
    }

}
