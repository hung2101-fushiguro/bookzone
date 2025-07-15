/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author ADMIN
 */
import model.Accessory;
import java.sql.SQLException;
import java.util.List;

public interface IAccessoryService {

    void addAccessory(Accessory accessory) throws SQLException;

    List<Accessory> getAllAccessories() throws SQLException;

    List<Accessory> getByCategory(int categoryId) throws SQLException;

    Accessory getById(int id) throws SQLException;
}
