package service;

import model.Order;

import java.sql.SQLException;
import java.util.List;
import model.CartItem;

public interface IOrderService {

    void insertOrder(Order order) throws SQLException;

    Order getOrderById(int id);

    List<Order> getAllOrders();

    boolean deleteOrder(int id) throws SQLException;

    boolean updateOrder(Order order) throws SQLException;

    int createOrder(Order order) throws SQLException;

    void addOrderDetail(int orderId, int bookId, int quantity, Double price) throws SQLException;

    public void createOrderWithDetails(Order order, List<CartItem> cart);

    List<Order> getOrdersByUserId(int userId);

}
