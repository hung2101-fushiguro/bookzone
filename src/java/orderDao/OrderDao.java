
package orderDao;

import dao.DBConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Order;

public class OrderDao implements IOrderDAO{
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM Orders WHERE id = ?";
    private static final String INSERT_ORDER = "INSERT INTO Orders (user_id, total_price, status) VALUES (?, ?, ?)";
    private static final String INSERT_ORDER_DETAILS = "INSERT INTO OrderDetails (order_id, book_id, quantity, price) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_ORDER = "UPDATE Orders SET user_id = ?, total_price = ?, status = ? WHERE id = ?";
    private static final String SELECT_ALL_ORDERS = "SELECT * FROM Orders";
    private static final String DELETE_ORDER = "DELETE FROM Orders WHERE id = ?";

    @Override
    public void insertOrder(Order orderObj) throws SQLException {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_ORDER)) {
            ps.setInt(1, orderObj.getUserId());
            ps.setDouble(2, orderObj.getTotalPrice());
            ps.setString(3, orderObj.getStatus());
            ps.executeUpdate();
        }
    }

    @Override
    public Order getOrderById(int id) {
        Order order = null;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ORDER_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                order = Order.fromResultSet(rs);
            } else {
                System.out.println("User not found!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return order;
    }

    @Override
    public List<Order> selectAllOrder() {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_ORDERS); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                orders.add(Order.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public boolean deleteOrder(int id) throws SQLException {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_ORDER)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateOrder(Order order) throws SQLException {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_ORDER)) {
            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalPrice());
            ps.setString(3, order.getStatus());
            ps.setInt(4, order.getId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public int createOrder(Order order) throws SQLException {
        int orderId = -1;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalPrice());
            ps.setString(3, order.getStatus());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    orderId = rs.getInt(1);
                }
            }
        }
        return orderId;
    }

    @Override
    public void addOrderDetail(int orderId, int bookId, int quantity, Double price) throws SQLException {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_ORDER_DETAILS)) {
            ps.setInt(1, orderId);
            ps.setInt(2, bookId);
            ps.setInt(3, quantity);
            ps.setDouble(4, price);
            ps.executeUpdate();
        }
    }

}