/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author ADMIN
 */
public class Comment {

    private int commentId;
    private int bookId;
    private int userId;
    private String userName; 
    private String content;
    private int rating;
    private Timestamp createdAt;
    private Integer parentCommentId;

    public Comment() {
    }

    public Comment(int commentId, int bookId, int userId, String userName, String content, int rating, Timestamp createdAt, Integer parentCommentId) {
        this.commentId = commentId;
        this.bookId = bookId;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.rating = rating;
        this.createdAt = createdAt;
        this.parentCommentId = parentCommentId;
    }
    
    

    public Comment(int commentId, int bookId, int userId, String content, Timestamp createdAt) {
        this.commentId = commentId;
        this.bookId = bookId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Comment(int commentId, int bookId, int userId, String content, int rating, Timestamp createdAt) {
        this.commentId = commentId;
        this.bookId = bookId;
        this.userId = userId;
        this.content = content;
        this.rating = rating;
        this.createdAt = createdAt;
    }

    public Comment(int commentId, int bookId, int userId, String content, int rating, Timestamp createdAt, Integer parentCommentId) {
        this.commentId = commentId;
        this.bookId = bookId;
        this.userId = userId;
        this.content = content;
        this.rating = rating;
        this.createdAt = createdAt;
        this.parentCommentId = parentCommentId;
    }
    
    
    
    

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Comment{" + "commentId=" + commentId + ", bookId=" + bookId + ", userId=" + userId + ", userName=" + userName + ", content=" + content + ", rating=" + rating + ", createdAt=" + createdAt + ", parentCommentId=" + parentCommentId + '}';
    }
    
    
    
    
    
}
