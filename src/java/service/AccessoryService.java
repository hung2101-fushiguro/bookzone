package service;

import accessoryDao.AccessoryDao;
import accessoryDao.IAccessoryDAO;
import dao.DBConnection;
import model.Accessory;

import java.sql.SQLException;
import java.util.List;

public class AccessoryService implements IAccessoryService {

    private final IAccessoryDAO accessoryDAO;

    public AccessoryService() {
        this.accessoryDAO = new AccessoryDao();
    }

    @Override
    public void addAccessory(Accessory accessory) throws SQLException {
        accessoryDAO.addAccessory(accessory);
    }

    @Override
    public List<Accessory> getAllAccessories() throws SQLException {
        return accessoryDAO.getAllAccessories();
    }

    @Override
    public List<Accessory> getByCategory(int categoryId) throws SQLException {
        return accessoryDAO.getByCategory(categoryId);
    }

    @Override
    public Accessory getById(int id) throws SQLException {
        return accessoryDAO.getById(id);
    }
}
