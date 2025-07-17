package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Comment;
import model.User;
import service.CommentService;
import service.ICommentService;

import java.io.IOException;
import java.net.URLEncoder;
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
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

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
        if (user == null) {
            return;
        }

        int bookId = parseIntParam(request, "bookId");
        int accessoryId = parseIntParam(request, "accessoryId");

        String content = request.getParameter("content");
        int rating = Integer.parseInt(request.getParameter("rating"));
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());

        Comment comment = new Comment();
        comment.setUserId(user.getId());
        comment.setContent(content);
        comment.setRating(rating);
        comment.setCreatedAt(createdAt);

        if (bookId > 0) {
            comment.setBookId(bookId);
            commentService.addComment(comment);
            response.sendRedirect("bookdetail?id=" + bookId + "&commentPage=1");
        } else if (accessoryId > 0) {
            comment.setAccessoryId(accessoryId);
            commentService.addComment(comment);
            response.sendRedirect("accessorydetail?id=" + accessoryId + "&commentPage=1");
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu bookId hoặc accessoryId.");
        }
    }

    private void updateComment(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();
        User user = getLoggedUser(session, response);
        if (user == null) {
            return;
        }

        int bookId = parseIntParam(request, "bookId");
        int accessoryId = parseIntParam(request, "accessoryId");

        int commentId = Integer.parseInt(request.getParameter("commentId"));
        String content = request.getParameter("content");
        int rating = Integer.parseInt(request.getParameter("rating"));
        Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

        Comment comment = new Comment();
        comment.setCommentId(commentId);
        comment.setUserId(user.getId());
        comment.setContent(content);
        comment.setRating(rating);
        comment.setCreatedAt(updatedAt);

        commentService.updateComment(comment);

        if (bookId > 0) {
            response.sendRedirect("bookdetail?id=" + bookId + "&commentPage=1");
        } else if (accessoryId > 0) {
            response.sendRedirect("accessorydetail?id=" + accessoryId + "&commentPage=1");
        } else {
            response.sendRedirect("home");
        }
    }

    private void deleteComment(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();
        User user = getLoggedUser(session, response);
        if (user == null) {
            return;
        }

        int bookId = parseIntParam(request, "bookId");
        int accessoryId = parseIntParam(request, "accessoryId");

        int commentId = Integer.parseInt(request.getParameter("commentId"));

        commentService.deleteComment(commentId, user.getId());

        if (bookId > 0) {
            response.sendRedirect("bookdetail?id=" + bookId + "&commentPage=1");
        } else if (accessoryId > 0) {
            response.sendRedirect("accessorydetail?id=" + accessoryId + "&commentPage=1");
        } else {
            response.sendRedirect("home");
        }
    }

    private User getLoggedUser(HttpSession session, HttpServletResponse response) throws IOException {
        Object userObj = session.getAttribute("user");
        if (userObj == null || !(userObj instanceof User)) {
            String currentURL = getCurrentURL(session);
            response.sendRedirect("login?redirect=" + URLEncoder.encode(currentURL, "UTF-8"));
            return null;
        }
        return (User) userObj;
    }

    private String getCurrentURL(HttpSession session) {
        String lastBookId = (String) session.getAttribute("lastBookId");
        String lastAccessoryId = (String) session.getAttribute("lastAccessoryId");

        if (lastBookId != null) {
            return "bookdetail?id=" + lastBookId + "&commentPage=1";
        } else if (lastAccessoryId != null) {
            return "accessorydetail?id=" + lastAccessoryId + "&commentPage=1";
        } else {
            return "home";
        }
    }

    private int parseIntParam(HttpServletRequest request, String param) {
        try {
            String val = request.getParameter(param);
            return (val != null && !val.isEmpty()) ? Integer.parseInt(val) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
