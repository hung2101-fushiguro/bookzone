package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Accessory;
import service.AccessoryService;
import service.AccessoryCategoryService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AdminAccessoryServlet", urlPatterns = {"/adminaccessory"})
public class AdminAccessoryServlet extends HttpServlet {

    private AccessoryService accessoryService;
    private AccessoryCategoryService categoryService;

    @Override
    public void init() {
        accessoryService = new AccessoryService();
        categoryService = new AccessoryCategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create":
                    showCreateForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteAccessory(request, response);
                    break;
                default:
                int page = 1;
                int limit = 10; // Số lượng phụ kiện mỗi trang
                if (request.getParameter("page") != null) {
                    try {
                        page = Integer.parseInt(request.getParameter("page"));
                    } catch (NumberFormatException e) {
                        page = 1;
                    }
                }

                List<Accessory> accessories = accessoryService.selectAccessoriesByPage(page, limit);
                int totalAccessories = accessoryService.getTotalAccessoryCount();
                int totalPages = (int) Math.ceil(totalAccessories * 1.0 / limit);

                request.setAttribute("accessories", accessories);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                RequestDispatcher dispatcher = request.getRequestDispatcher("admin/accessoryList.jsp");
                dispatcher.forward(request, response);
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create":
                    insertAccessory(request, response);
                    break;
                case "edit":
                    updateAccessory(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listAccessories(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Accessory> accessories = accessoryService.getAllAccessories();
        request.setAttribute("accessories", accessories);
        RequestDispatcher dispatcher = request.getRequestDispatcher("accessory/listAdminAccessory.jsp");
        dispatcher.forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<model.AccessoryCategory> categories = categoryService.getAllCategories();
        request.setAttribute("categories", categories);
        RequestDispatcher dispatcher = request.getRequestDispatcher("accessory/createAccessory.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Accessory existingAccessory = (Accessory) accessoryService.getByCategory(id);
        List<model.AccessoryCategory> categories = categoryService.getAllCategories();

        request.setAttribute("accessory", existingAccessory);
        request.setAttribute("categories", categories);
        RequestDispatcher dispatcher = request.getRequestDispatcher("accessory/editAccessory.jsp");
        dispatcher.forward(request, response);
    }

    private void insertAccessory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String imgUrl = request.getParameter("imgUrl");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String createdAt = request.getParameter("createdAt");

        Accessory newAccessory = new Accessory();
        newAccessory.setName(name);
        newAccessory.setDescription(description);
        newAccessory.setPrice(price);
        newAccessory.setQuantity(quantity);
        newAccessory.setImageUrl(imgUrl);
        newAccessory.setCategoryId(categoryId);
        newAccessory.setCreatedAt(createdAt);

        accessoryService.addAccessory(newAccessory);
        response.sendRedirect("/adminaccessory");
    }

    private void updateAccessory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String imgUrl = request.getParameter("imgUrl");
        String categoryName = request.getParameter("categoryName");
        String createdAt = request.getParameter("createdAt");

        Accessory accessory = new Accessory(id, name, description, price, quantity, imgUrl, categoryName, createdAt);
        accessoryService.updateAccessory(accessory);
        response.sendRedirect("/adminaccessory");
    }

    private void deleteAccessory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        accessoryService.deleteAccessory(id);
        response.sendRedirect("/adminaccessory");
    }

    @Override
    public String getServletInfo() {
        return "Admin Servlet to manage accessories";
    }
}
