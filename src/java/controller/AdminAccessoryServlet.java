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
import model.AccessoryCategory;

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
                    listAccessories(request, response);
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
        Accessory existingAccessory = accessoryService.getById(id);
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
    int categoryId = Integer.parseInt(request.getParameter("categoryId"));  // Lấy categoryId từ form
    String createdAt = request.getParameter("createdAt");

    // Lấy đối tượng AccessoryCategory từ categoryId
    AccessoryCategory category = categoryService.getById(categoryId);  // Lấy danh mục từ categoryId

    // Tạo đối tượng Accessory và thiết lập các giá trị
    Accessory newAccessory = new Accessory();
    newAccessory.setName(name);
    newAccessory.setDescription(description);
    newAccessory.setPrice(price);
    newAccessory.setQuantity(quantity);
    newAccessory.setImageUrl(imgUrl);
    newAccessory.setCategory(category);  // Gán đối tượng AccessoryCategory
    newAccessory.setCreatedAt(createdAt);

    // Thêm phụ kiện vào cơ sở dữ liệu
    accessoryService.addAccessory(newAccessory);
    response.sendRedirect("adminaccessory");
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

    // Lấy đối tượng AccessoryCategory dựa trên tên danh mục
    AccessoryCategory category = accessoryService.getCategoryByName(categoryName);

    // Tạo đối tượng Accessory với AccessoryCategory
    Accessory accessory = new Accessory(id, name, description, price, quantity, imgUrl, category, createdAt);

    // Cập nhật phụ kiện trong cơ sở dữ liệu
    accessoryService.updateAccessory(accessory);
    response.sendRedirect("adminaccessory");
}


    private void deleteAccessory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        accessoryService.deleteAccessory(id);
        response.sendRedirect("adminaccessory");
    }

    @Override
    public String getServletInfo() {
        return "Admin Servlet to manage accessories";
    }
}
