package service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import model.GoogleUser;
import model.User;
import userDao.IUserDAO;
import userDao.UserDao;

public class UserService implements IUserService {

    private IUserDAO userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    @Override
    public User checkLogin(String email, String password) {
        return userDao.checkLogin(email, password);
    }

    @Override
    public void insertUser(User user) throws SQLException {
        userDao.insertUser(user);
    }

    @Override
    public User selectUser(int id) {
        return userDao.selectUser(id);
    }

    @Override
    public User selectUserByEmail(String email) {
        return userDao.selectUserByEmail(email);
    }

    @Override
    public List<User> selectAllUsers() {
        return userDao.selectAllUsers();
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        return userDao.deleteUser(id);
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        return userDao.updateUser(user);
    }

    // Optional: nếu cần dùng thêm
    public User getUserByUsername(String username) throws SQLException {
        return userDao.getByUsername(username);
    }

    public User findOrCreateByGoogleUser(GoogleUser googleUser) {
        User user = userDao.selectUserByEmail(googleUser.getEmail());
        if (user != null) {
            return user;
        }

        user = new User();
        user.setUsername(googleUser.getEmail());
        user.setEmail(googleUser.getEmail());
        user.setPassword("");  // hoặc null nếu thích
        user.setName(googleUser.getName());
        user.setAvatarUrl(googleUser.getPicture());
        user.setProvider("google");
        user.setProviderId(googleUser.getEmail());
        user.setRole("user");
        user.setCreatedAt(new Date());
        user.setAddress("");
        user.setPhone("");

        try {
            userDao.insertUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
