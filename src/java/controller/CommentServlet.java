package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Comment;
import model.User;
import service.CommentService;
import service.ICommentService;

import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(name = "CommentServlet", urlPatterns = {"/comment"})
public class CommentServlet extends HttpServlet {

    private ICommentService commentService;

    @Override
    public void init() throws ServletException {
        commentService = new CommentService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Không hỗ trợ GET
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        // Debug log
        System.out.println("[CommentServlet] Received action: " + action);

        try {
            if ("add".equals(action)) {
                addComment(request, response);
            } else if ("update".equals(action)) {
                updateComment(request, response);
            } else if ("delete".equals(action)) {
                deleteComment(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private void addComment(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();
        User user = getLoggedUser(session, response);
        if (user == null) return;

        int bookId = Integer.parseInt(request.getParameter("bookId"));
        String content = request.getParameter("content");
        int rating = Integer.parseInt(request.getParameter("rating"));

        if (content == null || content.trim().isEmpty()) {
            response.sendRedirect("bookdetail?id=" + bookId + "&error=emptyContent");
            return;
        }

        Timestamp createdAt = new Timestamp(System.currentTimeMillis());

        Comment comment = new Comment();
        comment.setBookId(bookId);
        comment.setUserId(user.getId());
        comment.setContent(content);
        comment.setRating(rating);
        comment.setCreatedAt(createdAt);

        System.out.println("[CommentServlet] Adding comment by userId=" + user.getId());

        commentService.addComment(comment);

        response.sendRedirect("bookdetail?id=" + bookId);
    }

    private void updateComment(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();
        User user = getLoggedUser(session, response);
        if (user == null) return;

        int commentId = Integer.parseInt(request.getParameter("commentId"));
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        String content = request.getParameter("content");
        int rating = Integer.parseInt(request.getParameter("rating"));

        Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

        Comment comment = new Comment();
        comment.setCommentId(commentId);
        comment.setUserId(user.getId());
        comment.setContent(content);
        comment.setRating(rating);
        comment.setCreatedAt(updatedAt);

        System.out.println("[CommentServlet] Updating commentId=" + commentId + " by userId=" + user.getId());

        commentService.updateComment(comment);

        response.sendRedirect("bookdetail?id=" + bookId);
    }

    private void deleteComment(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();
        User user = getLoggedUser(session, response);
        if (user == null) return;

        int commentId = Integer.parseInt(request.getParameter("commentId"));
        int bookId = Integer.parseInt(request.getParameter("bookId"));

        System.out.println("[CommentServlet] Deleting commentId=" + commentId + " by userId=" + user.getId());

        commentService.deleteComment(commentId, user.getId());

        response.sendRedirect("bookdetail?id=" + bookId);
    }

    private User getLoggedUser(HttpSession session, HttpServletResponse response) throws IOException {
        Object userObj = session.getAttribute("user");
        if (userObj == null || !(userObj instanceof User)) {
            System.out.println("[CommentServlet] User not logged in or invalid session object!");
            response.sendRedirect("login");
            return null;
        }
        return (User) userObj;
    }
}
