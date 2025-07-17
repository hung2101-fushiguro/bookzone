package userDao;

import java.sql.SQLException;
import java.util.List;
import model.User;

public interface IUserDAO {

    // Kiểm tra đăng nhập bằng email và password
    User checkLogin(String email, String password);

    // Thêm người dùng mới
    void insertUser(User user) throws SQLException;

    // Lấy người dùng theo ID
    User selectUser(int id);

    // Lấy toàn bộ người dùng
    List<User> selectAllUsers();

    // Xóa người dùng theo ID
    boolean deleteUser(int id) throws SQLException;

    // Cập nhật thông tin người dùng
    boolean updateUser(User user) throws SQLException;

    // Lấy người dùng theo username
    User getByUsername(String username) throws SQLException;

    // Lấy người dùng theo email
    User selectUserByEmail(String email);
    
    boolean updatePasswordByEmail(String email, String newPassword) throws SQLException;

}
