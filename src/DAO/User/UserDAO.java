package DAO.User;

import Helpers.DBConnection;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This handles CRUD actions from the underlying data source (i.e. the database).
 */
public class UserDAO {
    /**
     * This returns an ObservableList of all the Users in the database.
     * @return allUsers
     * @throws Exception in case of SQL or other issues.
     */
    public static ObservableList<User> getAllUsers() throws Exception {
        String sql = "SELECT User_ID, User_Name from users";
        ObservableList<User> allUsers = FXCollections.observableArrayList();

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");

            User u = new User(userId, userName);

            allUsers.add(u);
        }
        return allUsers;
    }
}
