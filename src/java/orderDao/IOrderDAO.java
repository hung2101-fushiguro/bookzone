package orderDao;

import java.sql.SQLException;
import java.util.List;
import model.Order;

public interface IOrderDAO {

    public void insertOrder(Order orderObj) throws SQLException;

    public Order getOrderById(int id);

    public List<Order> selectAllOrder();

    public boolean deleteOrder(int id) throws SQLException;

    public boolean updateOrder(Order OrderObj) throws SQLException;

    int createOrder(Order order) throws SQLException;

    void addOrderDetail(int orderId, int bookId, int quantity, Double price) throws SQLException;

    List<Order> getOrdersByUserId(int userId);

}
