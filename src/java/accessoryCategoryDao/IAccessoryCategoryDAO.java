/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accessoryCategoryDao;

/**
 *
 * @author ADMIN
 */
import java.sql.SQLException;
import java.util.List;
import model.AccessoryCategory;

public interface IAccessoryCategoryDAO {
    List<AccessoryCategory> getAllCategories() throws SQLException;
    AccessoryCategory getById(int id) throws SQLException;
}
