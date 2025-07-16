
package service;

import model.User;


public interface IMailService {
    void sendOrderConfirmation(User user, int orderId);
}
