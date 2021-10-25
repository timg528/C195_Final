package Models;


import Helpers.DBConnection;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class essentially holds all of the database information locally. It was becoming more
 * and more difficult to write disparate code to deal with the data on each form.
 */
public class Data {
    private static ObservableList<User> users = FXCollections.observableArrayList();
    private static ObservableList<Country> countries = FXCollections.observableArrayList();


    /**
     * This method is to initialize the Data object. It calls the various generate* methods
     * to copy the database locally.
     */
    public static void generateAll() {
        generateUsers();
    }

    /**
     * This method calls the users table and pulls all of the user accounts and puts them
     * in an ObservableList named creatively enough "users".
     */
    private static void generateUsers(){
        try {

            String sql = "Select User_ID, User_Name, Password FROM users;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                // Bad practice, will clean up if I have time
                String password = rs.getString("Password");

                User user = new User(userID, username, password);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {e.printStackTrace();}
    }

    /**
     * This method calls the countries table and generates a list of country objects.
     */
    private static void generateCountries(){
        try {
            String sql = "Select Country_ID, Country FROM countries;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Country country = new Country(countryID, country);
                countries.add(country);
            }
        } catch (SQLException e) {e.printStackTrace();
        } catch (Exception e) {e.printStackTrace();}


    }
}
