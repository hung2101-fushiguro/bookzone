/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import service.AccessoryCategoryService;
import service.IAccessoryCategoryService;
import service.AccessoryService;
import service.IAccessoryService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Accessory;
import model.AccessoryCategory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "AccessoryListServlet", urlPatterns = {"/accessorylist"})
public class AccessoryListServlet extends HttpServlet {

    private IAccessoryService accessoryService;
    private IAccessoryCategoryService categoryService;

    @Override
    public void init() throws ServletException {
        accessoryService = new AccessoryService();
        categoryService = new AccessoryCategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "detail":
                    showAccessoryDetail(request, response);
                    break;
                default:
                    listAccessories(request, response);
                    break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccessoryListServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
    }

    private void listAccessories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String categoryParam = request.getParameter("category");
        List<Accessory> accessories;

        if (categoryParam != null && !categoryParam.isEmpty()) {
            int categoryId = Integer.parseInt(categoryParam);
            accessories = accessoryService.getByCategory(categoryId);
        } else {
            accessories = accessoryService.getAllAccessories();
        }

        List<AccessoryCategory> categories = categoryService.getAllCategories();

        request.setAttribute("accessories", accessories);
        request.setAttribute("categories", categories);

        request.getRequestDispatcher("/accessory/accessoryList.jsp").forward(request, response);
    }

    private void showAccessoryDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/accessorylist");
            return;
        }

        int id = Integer.parseInt(idParam);
        Accessory accessory = accessoryService.getById(id);

        if (accessory == null) {
            response.sendRedirect(request.getContextPath() + "/accessorylist");
            return;
        }

        request.setAttribute("accessory", accessory);
        request.getRequestDispatcher("/accessory/accessoryDetail.jsp").forward(request, response);
    }
}
