
package service;

import model.Cart;
import java.sql.SQLException;

public interface ICartService {

    Cart getCartByUserId(int userId) throws SQLException;

    void createCart(int userId) throws SQLException;

    void deleteCart(int userId) throws SQLException;
}
