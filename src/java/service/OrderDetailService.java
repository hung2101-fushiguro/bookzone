/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import orderdetailDao.IOrderDetailDAO;

import java.util.List;
import model.OrderDetail;
import orderdetailDao.OrderDetailDao;

public class OrderDetailService implements IOrderDetailService {

    private final IOrderDetailDAO orderDetailDAO;

    public OrderDetailService() {
        this.orderDetailDAO = new OrderDetailDao();
    }

    @Override
    public List<OrderDetail> getDetailsByOrderId(int orderId) {
        return orderDetailDAO.getOrderDetailsByOrderId(orderId);
    }
}
