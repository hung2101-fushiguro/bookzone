
package userDao;

import java.sql.SQLException;
import java.util.List;
import model.User;


public interface  IUserDAO {

    public User checkLogin(String email, String password); 
    
    public void insertUser(User user) throws SQLException;

    public User selectUser(int id);

    public List<User> selectAllUsers();

    public boolean deleteUser(int id) throws SQLException;

    public boolean updateUser(User user) throws SQLException;
}


