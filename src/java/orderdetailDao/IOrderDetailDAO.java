/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package orderdetailDao;

import java.util.List;
import model.OrderDetail;

/**
 *
 * @author DELL
 */
public interface IOrderDetailDAO {
    List<OrderDetail> getOrderDetailsByOrderId(int orderId);
}
