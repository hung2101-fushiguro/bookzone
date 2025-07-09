
package service;

import userDao.IUserDAO;
import userDao.UserDao;
import java.sql.SQLException;
import java.util.List;
import model.User;

/**
 *
 * @author DELL
 */
public class UserService implements IUserService {
    private IUserDAO userDao;
    
    public UserService(){
        this.userDao = new UserDao();
    }
    
    public void addUser(User user) throws SQLException{
        userDao.insertUser(user);
    }
    
    public User selectUser(int id){
        return userDao.selectUser(id);
    }

    public List<User> selectAllUsers(){
        return userDao.selectAllUsers();
    }

    public boolean deleteUser(int id) throws SQLException{
        return userDao.deleteUser(id);
    }

    public boolean updateUser(User user) throws SQLException{
        return userDao.updateUser(user);
    }

    @Override
    public User checkLogin(String email, String password) {
        return userDao.checkLogin(email, password);
    }

    
}

