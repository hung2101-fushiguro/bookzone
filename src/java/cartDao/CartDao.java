package cartDao;

import model.Cart;
import dao.DBConnection;
import java.sql.*;

public class CartDao implements ICartDAO {

    @Override
    public Cart getCartByUserId(int userId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM Cart WHERE user_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return Cart.fromResultSet(rs);
        }

        return null;
    }

    @Override
    public void createCart(int userId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "INSERT INTO Cart (user_id) VALUES (?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.executeUpdate();
    }

    @Override
    public void deleteCart(int userId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "DELETE FROM Cart WHERE user_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.executeUpdate();
    }
}