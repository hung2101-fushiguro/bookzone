
package service;

import cartDao.CartDao;
import model.Cart;

import java.sql.SQLException;

public class CartService implements ICartService {

    private CartDao cartDAO;

    public CartService() {
        this.cartDAO = new CartDao();
    }

    @Override
    public Cart getCartByUserId(int userId) throws SQLException {
        return cartDAO.getCartByUserId(userId);
    }

    @Override
    public void createCart(int userId) throws SQLException {
        cartDAO.createCart(userId);
    }

    @Override
    public void deleteCart(int userId) throws SQLException {
        cartDAO.deleteCart(userId);
    }
}
