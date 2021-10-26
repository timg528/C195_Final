package Models;


import DAO.Locations.CountryDAO;
import DAO.Locations.DivisionDAO;
import Helpers.DBConnection;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class essentially holds all of the database information locally. It was becoming more
 * and more difficult to write disparate code to deal with the data on each form. This acts like the
 * Inventory object in the Software 1 class.
 */
public class Data {
    private static ObservableList<User> users = FXCollections.observableArrayList();
    private static ObservableList<Country> countries = FXCollections.observableArrayList();
    private static ObservableList<Division> divisions = FXCollections.observableArrayList();


    /**
     * This method is to initialize the Data object. It calls the various generate* methods
     * to copy the database locally.
     */
    public static void generateAll() throws SQLException, Exception {

        generateCountries();
    }

    /**
     * This method calls the countries table and generates a list of country objects.
     */
    private static void generateCountries() throws SQLException, Exception {
        countries.clear();
        countries = CountryDAO.getAllCountries();
    }

    public static ObservableList<Country> getCountries() {
        return countries;
    }

    private static void generateDivisions() throws SQLException, Exception {
        divisions.clear();
        divisions = DivisionDAO.getAllDivisions();
    }

    public static ObservableList<Division> getDivisions() {
        return divisions;
    }
}
