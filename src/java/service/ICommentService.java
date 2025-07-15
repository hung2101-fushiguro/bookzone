/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import model.Comment;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public interface ICommentService {

    void addComment(Comment comment) throws Exception;

    List<Comment> getCommentsByBookId(int bookId) throws Exception;

    Comment getCommentById(int commentId) throws Exception;

    void updateComment(Comment comment) throws Exception;

    void deleteComment(int commentId, int userId) throws Exception;

    List<Comment> getCommentsByBookIdPaged(int bookId, int offset, int limit) throws SQLException;

    int countCommentsByBookId(int bookId) throws SQLException;

}
