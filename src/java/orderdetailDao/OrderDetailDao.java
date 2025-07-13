/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderdetailDao;

/**
 *
 * @author DELL
 */
import dao.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.OrderDetail;

public class OrderDetailDao implements IOrderDetailDAO {

    private static final String SELECT_BY_ORDER_ID = """
        SELECT od.*, b.title, b.image_url 
        FROM OrderDetails od 
        JOIN Books b ON od.book_id = b.id 
        WHERE od.order_id = ?
    """;

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_ORDER_ID)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetail detail = new OrderDetail();
                detail.setId(rs.getInt("id"));
                detail.setOrderId(rs.getInt("order_id"));
                detail.setBookId(rs.getInt("book_id"));
                detail.setQuantity(rs.getInt("quantity"));
                detail.setPrice(rs.getDouble("price"));
                detail.setBookTitle(rs.getString("title"));
                detail.setBookImage(rs.getString("image_url"));
                list.add(detail);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
