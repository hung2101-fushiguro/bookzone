package userDao;

import dao.DBConnection;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IUserDAO {

    private static final String LOGIN = "SELECT * FROM Users WHERE email = ? AND password = ?";
    private static final String INSERT_USER = "INSERT INTO Users (username, email, password, name, avatar_url, provider, provider_id, role, created_at, address, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE Users SET username = ?, email = ?, password = ?, name = ?, avatar_url = ?, provider = ?, provider_id = ?, role = ?, created_at = ?, address = ?, phone = ? WHERE id = ?";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM Users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM Users";
    private static final String DELETE_USER_BY_ID = "DELETE FROM Users WHERE id = ?";
    private static final String SELECT_BY_USERNAME = "SELECT * FROM Users WHERE username = ?";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM Users WHERE email = ?";
    private static final String UPDATE_PASSWORD_BY_EMAIL = "UPDATE Users SET password = ? WHERE email = ?";

    @Override
    public User checkLogin(String email, String password) {
        User user = null;
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(LOGIN)) {

            pst.setString(1, email);
            pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    user = extractUserFromResultSet(rs);
                }
            }
        } catch (Exception e) {
            System.out.println("Error in checkLogin:");
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void insertUser(User user) throws SQLException {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_USER)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getName());
            ps.setString(5, user.getAvatarUrl());
            ps.setString(6, user.getProvider());
            ps.setString(7, user.getProviderId());
            ps.setString(8, user.getRole());
            ps.setTimestamp(9, new Timestamp(user.getCreatedAt().getTime()));
            ps.setString(10, user.getAddress());
            ps.setString(11, user.getPhone());

            int rowsInserted = ps.executeUpdate();
            System.out.println(rowsInserted > 0 ? "User inserted successfully!" : "Insert failed.");
        }
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(UPDATE_USER)) {

            pst.setString(1, user.getUsername());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getName());
            pst.setString(5, user.getAvatarUrl());
            pst.setString(6, user.getProvider());
            pst.setString(7, user.getProviderId());
            pst.setString(8, user.getRole());
            pst.setTimestamp(9, new Timestamp(user.getCreatedAt().getTime()));
            pst.setString(10, user.getAddress());
            pst.setString(11, user.getPhone());
            pst.setInt(12, user.getId());

            int rowsUpdated = pst.executeUpdate();

            System.out.println(rowsUpdated > 0 ? "User updated successfully!" : "Update failed.");
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Error in updateUser:");
            printSQLException(e);
            return false;
        }
    }

    @Override
    public User selectUser(int id) {
        User user = null;
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(SELECT_USER_BY_ID)) {

            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    user = extractUserFromResultSet(rs);
                }
            }
        } catch (Exception e) {
            System.out.println("Error in selectUser:");
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> selectAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(SELECT_ALL_USERS); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                userList.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error in selectAllUsers:");
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(DELETE_USER_BY_ID)) {

            pst.setInt(1, id);
            int rows = pst.executeUpdate();

            System.out.println(rows > 0 ? "Delete successful!" : "No user found.");
            return rows > 0;
        } catch (Exception e) {
            System.out.println("Error in deleteUser:");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getByUsername(String username) throws SQLException {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_USERNAME)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public User selectUserByEmail(String email) {
        User user = null;
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(SELECT_BY_EMAIL)) {

            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    user = extractUserFromResultSet(rs);
                }
            }
        } catch (Exception e) {
            System.out.println("Error in selectUserByEmail:");
            e.printStackTrace();
        }
        return user;
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("avatar_url"),
                rs.getString("provider"),
                rs.getString("provider_id"),
                rs.getString("role"),
                rs.getTimestamp("created_at"),
                rs.getString("address"),
                rs.getString("phone")
        );
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    @Override
    public boolean updatePasswordByEmail(String email, String newPassword) throws SQLException {
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(UPDATE_PASSWORD_BY_EMAIL)) {

            pst.setString(1, newPassword);
            pst.setString(2, email);

            int rowsUpdated = pst.executeUpdate();

            System.out.println(rowsUpdated > 0 ? "Password updated successfully!" : "Password update failed.");
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error in updatePasswordByEmail:");
            printSQLException(e);
            return false;
        }
    }
}
