/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accessoryDao;

/**
 *
 * @author ADMIN
 */
import dao.DBConnection;
import accessoryCategoryDao.AccessoryCategoryDao;
import accessoryCategoryDao.IAccessoryCategoryDAO;
import model.Accessory;
import model.AccessoryCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccessoryDao implements IAccessoryDAO {

    private static final String SELECT_ALL_ACCESSORIES_SQL
            = "SELECT * FROM Accessory";

    private static final String SELECT_ACCESSORIES_BY_CATEGORY_SQL
            = "SELECT * FROM Accessory WHERE category_id = ?";

    private static final String SELECT_ACCESSORY_BY_ID_SQL
            = "SELECT * FROM Accessory WHERE id = ?";

    private static final String INSERT_ACCESSORY_SQL
            = "INSERT INTO Accessory (name, description, price, quantity, image_url, category_id, created_at) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_ACCESSORY_SQL
            = "UPDATE Accessory SET name = ?, description = ?, price = ?, quantity = ?, image_url = ?, category_id = ?, created_at = ? WHERE id = ?";

    private static final String DELETE_ACCESSORY_SQL
            = "DELETE FROM Accessory WHERE id = ?";

    private static final String SELECT_ACCESSORIES_BY_PAGE
            = "SELECT a.*, c.name AS category_name "
            + "FROM Accessory a " // Đổi từ "Accessories" thành "Accessory"
            + "LEFT JOIN AccessoryCategories c ON a.category_id = c.id "
            + "ORDER BY a.id "
            + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    private static final String SELECT_CATEGORY_BY_NAME_SQL = "SELECT * FROM AccessoryCategories WHERE name = ?";

    private static final String COUNT_ALL_ACCESSORIES = "SELECT COUNT(*) FROM Accessory"; // Đổi từ "Accessories" thành "Accessory"

    private final IAccessoryCategoryDAO categoryDAO;

    public AccessoryDao() {
        this.categoryDAO = new AccessoryCategoryDao();
    }

    @Override
    public void addAccessory(Accessory accessory) throws SQLException {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_ACCESSORY_SQL)) {

            ps.setString(1, accessory.getName());
            ps.setString(2, accessory.getDescription());
            ps.setDouble(3, accessory.getPrice());
            ps.setInt(4, accessory.getQuantity());
            ps.setString(5, accessory.getImageUrl());
            ps.setInt(6, accessory.getCategory().getId());
            ps.setString(7, accessory.getCreatedAt());

            ps.executeUpdate();
        }
    }

    @Override
    public List<Accessory> getAllAccessories() throws SQLException {
        List<Accessory> accessories = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_ACCESSORIES_SQL)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                accessories.add(extractAccessory(rs));
            }
        }
        return accessories;
    }

    @Override
    public List<Accessory> getByCategory(int categoryId) throws SQLException {
        List<Accessory> accessories = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ACCESSORIES_BY_CATEGORY_SQL)) {

            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                accessories.add(extractAccessory(rs));
            }
        }
        return accessories;
    }

    @Override
    public Accessory getById(int id) throws SQLException {
        Accessory accessory = null;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ACCESSORY_BY_ID_SQL)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                accessory = extractAccessory(rs);
            }
        }
        return accessory;
    }

    private Accessory extractAccessory(ResultSet rs) throws SQLException {
        Accessory accessory = new Accessory();
        accessory.setId(rs.getInt("id"));
        accessory.setName(rs.getString("name"));
        accessory.setDescription(rs.getString("description"));
        accessory.setPrice(rs.getDouble("price"));
        accessory.setQuantity(rs.getInt("quantity"));
        accessory.setImageUrl(rs.getString("image_url"));
        accessory.setCreatedAt(rs.getString("created_at"));

        int categoryId = rs.getInt("category_id");
        AccessoryCategory category = categoryDAO.getById(categoryId);
        accessory.setCategory(category);

        return accessory;
    }

    @Override
    public void updateAccessory(Accessory accessory) throws SQLException {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_ACCESSORY_SQL)) {

            ps.setString(1, accessory.getName());
            ps.setString(2, accessory.getDescription());
            ps.setDouble(3, accessory.getPrice());
            ps.setInt(4, accessory.getQuantity());
            ps.setString(5, accessory.getImageUrl());
            ps.setInt(6, accessory.getCategory().getId());
            ps.setString(7, accessory.getCreatedAt());
            ps.setInt(8, accessory.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void deleteAccessory(int id) throws SQLException {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_ACCESSORY_SQL)) {

            ps.setInt(1, id);

            ps.executeUpdate();
        }
    }

    @Override
    public List<Accessory> selectAccessoriesByPage(int offset, int limit) {
        List<Accessory> accessories = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCESSORIES_BY_PAGE)) {

            preparedStatement.setInt(1, offset); // Set offset đúng
            preparedStatement.setInt(2, limit);  // Set limit đúng

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Accessory accessory = extractAccessory(rs);
                accessories.add(accessory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accessories;
    }

    @Override
    public int getTotalAccessoryCount() {
        int count = 0;

        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(COUNT_ALL_ACCESSORIES)) {

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1); // Đảm bảo trả về đúng số lượng phụ kiện
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    @Override
    public AccessoryCategory getCategoryByName(String categoryName) throws SQLException {
        AccessoryCategory category = null;

        // Kết nối cơ sở dữ liệu
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_CATEGORY_BY_NAME_SQL)) {

            // Đặt tham số vào câu lệnh SQL
            ps.setString(1, categoryName);

            // Thực thi truy vấn và lấy kết quả
            ResultSet rs = ps.executeQuery();

            // Nếu có kết quả, tạo đối tượng AccessoryCategory từ dữ liệu truy vấn
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                // Tạo đối tượng AccessoryCategory
                category = new AccessoryCategory(id, name);
            }
        }

        // Trả về đối tượng AccessoryCategory hoặc null nếu không tìm thấy
        return category;
    }

}
