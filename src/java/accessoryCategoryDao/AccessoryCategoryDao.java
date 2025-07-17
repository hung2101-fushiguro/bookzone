/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accessoryCategoryDao;

/**
 *
 * @author ADMIN
 */
import model.AccessoryCategory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import dao.DBConnection;

public class AccessoryCategoryDao implements IAccessoryCategoryDAO {

    private static final String SELECT_ALL_CATEGORIES_SQL
            = "SELECT * FROM AccessoryCategories";

    private static final String SELECT_CATEGORY_BY_ID_SQL
            = "SELECT * FROM AccessoryCategories WHERE id = ?";

    @Override
    public List<AccessoryCategory> getAllCategories() throws SQLException {
        List<AccessoryCategory> categories = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_CATEGORIES_SQL)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categories.add(extractCategory(rs));
            }
        }
        return categories;
    }

    @Override
    public AccessoryCategory getById(int id) throws SQLException {
        AccessoryCategory category = null;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_CATEGORY_BY_ID_SQL)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                category = extractCategory(rs);
            }
        }
        return category;
    }

    private AccessoryCategory extractCategory(ResultSet rs) throws SQLException {
        AccessoryCategory category = new AccessoryCategory();
        category.setId(rs.getInt("id"));
        category.setName(rs.getString("name"));
        return category;
    }
}
