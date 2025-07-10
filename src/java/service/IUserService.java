
package service;

import java.sql.SQLException;
import java.util.List;
import model.User;


public interface IUserService {
    
    public User checkLogin(String email, String password); 
    
    public void insertUser(User user) throws SQLException;

    public User selectUser(int id);

    public List<User> selectAllUsers();

    public boolean deleteUser(int id) throws SQLException;
    
    User selectUserByEmail(String email);

    public boolean updateUser(User user) throws SQLException;
    
}

