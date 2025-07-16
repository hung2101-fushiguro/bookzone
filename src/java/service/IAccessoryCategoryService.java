
package service;


import model.AccessoryCategory;
import java.sql.SQLException;
import java.util.List;

public interface IAccessoryCategoryService {
    List<AccessoryCategory> getAllCategories() throws SQLException;
    AccessoryCategory getById(int id) throws SQLException;
}
