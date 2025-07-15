/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author ADMIN
 */
import accessoryCategoryDao.AccessoryCategoryDao;
import accessoryCategoryDao.IAccessoryCategoryDAO;
import dao.DBConnection;
import model.AccessoryCategory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AccessoryCategoryService implements IAccessoryCategoryService {

    private final IAccessoryCategoryDAO categoryDAO;

    public AccessoryCategoryService() {
        this.categoryDAO = new AccessoryCategoryDao();
    }

    @Override
    public List<AccessoryCategory> getAllCategories() throws SQLException {
        return categoryDAO.getAllCategories();
    }

    @Override
    public AccessoryCategory getById(int id) throws SQLException {
        return categoryDAO.getById(id);
    }
}
