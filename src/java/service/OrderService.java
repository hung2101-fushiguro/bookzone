/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import orderDao.IOrderDAO;
import orderDao.OrderDao;
import model.Order;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CartItem;

public class OrderService implements IOrderService {

    private final IOrderDAO orderDAO;

    public OrderService() {
        this.orderDAO = new OrderDao();
    }

    @Override
    public void insertOrder(Order order) throws SQLException {
        orderDAO.insertOrder(order);
    }

    @Override
    public Order getOrderById(int id) {
        return orderDAO.getOrderById(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDAO.selectAllOrder();
    }

    @Override
    public boolean deleteOrder(int id) throws SQLException {
        return orderDAO.deleteOrder(id);
    }

    @Override
    public boolean updateOrder(Order order) throws SQLException {
        return orderDAO.updateOrder(order);
    }

    @Override
    public int createOrder(Order order) throws SQLException {
        return orderDAO.createOrder(order);
    }

    @Override
    public void addOrderDetail(int orderId, int bookId, int quantity, Double price) throws SQLException {
        orderDAO.addOrderDetail(orderId, bookId, quantity, price);
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        return orderDAO.getOrdersByUserId(userId);
    }

    @Override
    public void updateOrderStatus(int orderId, String status) {
        try {
            orderDAO.updateOrderStatus(orderId, status);
        } catch (SQLException e) {
            Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void createOrderWithDetails(Order order, List<CartItem> cartItems) {
        int orderId = 0;
        try {
            orderId = orderDAO.createOrder(order);
        } catch (SQLException ex) {
            Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (CartItem item : cartItems) {
            try {
                orderDAO.addOrderDetail(orderId, item.getBook().getId(),
                        item.getQuantity(), item.getBook().getPrice());
            } catch (SQLException ex) {
                Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
