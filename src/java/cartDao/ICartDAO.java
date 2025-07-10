
package cartDao;

import model.Cart;
import java.sql.SQLException;

public interface ICartDAO {

    Cart getCartByUserId(int userId) throws SQLException;

    void createCart(int userId) throws SQLException;

    void deleteCart(int userId) throws SQLException;
}

