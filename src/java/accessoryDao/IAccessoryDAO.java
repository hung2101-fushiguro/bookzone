/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accessoryDao;

import model.Accessory;
import java.sql.*;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface IAccessoryDAO {

    void addAccessory(Accessory accessory) throws SQLException;

    List<Accessory> getAllAccessories() throws SQLException;

    List<Accessory> getByCategory(int categoryId) throws SQLException;

    Accessory getById(int id) throws SQLException;
}
