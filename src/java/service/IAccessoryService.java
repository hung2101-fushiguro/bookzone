package service;

import java.sql.ResultSet;
import model.Accessory;
import java.sql.SQLException;
import java.util.List;
import model.AccessoryCategory;

public interface IAccessoryService {

    void addAccessory(Accessory accessory) throws SQLException;

    List<Accessory> getAllAccessories() throws SQLException;

    List<Accessory> getByCategory(int categoryId) throws SQLException;

    Accessory getById(int id) throws SQLException;

    void updateAccessory(Accessory accessory) throws SQLException;

    void deleteAccessory(int id) throws SQLException;

    List<Accessory> selectAccessoriesByPage(int offset, int limit);

    int getTotalAccessoryCount();

    AccessoryCategory getCategoryByName(String categoryName) throws SQLException;

}
