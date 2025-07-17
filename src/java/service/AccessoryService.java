package service;

import accessoryDao.AccessoryDao;
import accessoryDao.IAccessoryDAO;
import dao.DBConnection;
import model.Accessory;

import java.sql.SQLException;
import java.util.List;
import model.AccessoryCategory;

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

    @Override
    public void updateAccessory(Accessory accessory) throws SQLException {
        accessoryDAO.updateAccessory(accessory);
    }

    @Override
    public void deleteAccessory(int id) throws SQLException {
        accessoryDAO.deleteAccessory(id);
    }

    @Override
    public List<Accessory> selectAccessoriesByPage(int page, int limit) {
        int offset = (page - 1) * limit;  // Tính toán offset từ trang hiện tại
        return accessoryDAO.selectAccessoriesByPage(offset, limit);
    }

    @Override
    public int getTotalAccessoryCount() {
        return accessoryDAO.getTotalAccessoryCount();
    }

    @Override
    public AccessoryCategory getCategoryByName(String categoryName) throws SQLException {
        return accessoryDAO.getCategoryByName(categoryName);
    }
}
