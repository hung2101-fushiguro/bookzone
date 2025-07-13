package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Order {

    private int id;
    private int userId;
    private double totalPrice;
    private String status;
    private Date createdAt; // ✅ Ngày đặt hàng

    public Order() {
    }

    public Order(int id, int userId, double totalPrice, String status, Date createdAt) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Order{" +
               "id=" + id +
               ", userId=" + userId +
               ", totalPrice=" + totalPrice +
               ", status='" + status + '\'' +
               ", createdAt=" + createdAt +
               '}';
    }

    
    public static Order fromResultSet(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setUserId(rs.getInt("user_id"));
        order.setTotalPrice(rs.getDouble("total_price"));
        order.setStatus(rs.getString("status"));
        order.setCreatedAt(rs.getTimestamp("order_date")); // lấy ngày tạo
        return order;
    }
}
