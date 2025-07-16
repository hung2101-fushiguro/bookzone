package service;

import model.Accessory;
import java.sql.SQLException;
import java.util.List;

public interface IAccessoryService {

    void addAccessory(Accessory accessory) throws SQLException;

    List<Accessory> getAllAccessories() throws SQLException;

    List<Accessory> getByCategory(int categoryId) throws SQLException;

    Accessory getById(int id) throws SQLException;
}
