package controller;

import service.AccessoryService;
import service.IAccessoryService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Accessory;
import model.Cart;
import model.Comment;
import service.CartService;
import service.CommentService;
import service.ICartService;
import service.ICommentService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AccessoryDetailServlet", urlPatterns = {"/accessorydetail"})
public class AccessoryDetailServlet extends HttpServlet {

    private IAccessoryService accessoryService;
    private ICommentService commentService;
    private ICartService cartService;

    @Override
    public void init() throws ServletException {
        accessoryService = new AccessoryService();
        commentService = new CommentService();
        cartService = new CartService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            showAccessoryDetail(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void showAccessoryDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/accessorylist");
            return;
        }

        int accessoryId;
        try {
            accessoryId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/accessorylist");
            return;
        }

        // Save lastAccessoryId to session
        HttpSession session = request.getSession();
        session.setAttribute("lastAccessoryId", idParam);

        // Get Accessory
        Accessory accessory = accessoryService.getById(accessoryId);
        if (accessory == null) {
            response.sendRedirect(request.getContextPath() + "/accessorylist");
            return;
        }
        request.setAttribute("accessory", accessory);

        // Phân trang bình luận
        int page = 1;
        String pageParam = request.getParameter("commentPage");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException ignored) {
            }
        }
        int pageSize = 5;

        try {
            int totalComments = commentService.countCommentsByAccessoryId(accessoryId);
            int totalPages = (int) Math.ceil((double) totalComments / pageSize);

            if (page > totalPages && totalPages > 0) {
                page = totalPages;
            }

            List<Comment> comments = commentService.getCommentsByAccessoryIdPaged(accessoryId, page, pageSize);

            request.setAttribute("comments", comments);
            request.setAttribute("commentPage", page);
            request.setAttribute("commentTotalPages", totalPages);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("comments", null);
        }

        // Cart if logged in
        Object userIdObj = session.getAttribute("userId");
        if (userIdObj instanceof Integer) {
            try {
                int userId = (Integer) userIdObj;
                Cart cart = cartService.getCartByUserId(userId);
                request.setAttribute("cart", cart);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("cart", null);
            }
        }

        request.getRequestDispatcher("/accessory/accessoryDetail.jsp").forward(request, response);
    }
}
