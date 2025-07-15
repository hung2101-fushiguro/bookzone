/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import service.AccessoryService;
import service.IAccessoryService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Accessory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "AccessoryDetailServlet", urlPatterns = {"/accessorydetail"})
public class AccessoryDetailServlet extends HttpServlet {

    private IAccessoryService accessoryService;

    @Override
    public void init() throws ServletException {
        accessoryService = new AccessoryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            showAccessoryDetail(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AccessoryDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
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
