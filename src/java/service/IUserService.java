package service;

import model.User;
import java.sql.SQLException;
import java.util.List;

public interface IUserService {

    User checkLogin(String email, String password);

    void insertUser(User user) throws SQLException;

    User selectUser(int id);

    User selectUserByEmail(String email);

    List<User> selectAllUsers();

    boolean deleteUser(int id) throws SQLException;

    boolean updateUser(User user) throws SQLException;
}
