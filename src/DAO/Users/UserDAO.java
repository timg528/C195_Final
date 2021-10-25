package DAO.Users;

import Models.User;
import javafx.collections.ObservableList;

/**
 * Data Access Object interface for Users
 */
public class UserDAO {

    /**
     * Gets User specified by ID
     * @param userID
     * @return User object
     */
    public User getUserbyID(int userID) {
        String sql = "SELECT User_ID, User_Name from users WHERE User_ID = '"+userID+"'";

        try{

        }


    }

    /**
     * Gets user specified by username
     * @param username
     * @return
     */
    public User getUserbyName(String username);

}
