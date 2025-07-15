/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author ADMIN
 */
import model.AccessoryCategory;
import java.sql.SQLException;
import java.util.List;

public interface IAccessoryCategoryService {
    List<AccessoryCategory> getAllCategories() throws SQLException;
    AccessoryCategory getById(int id) throws SQLException;
}
