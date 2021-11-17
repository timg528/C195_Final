package DAO.Locations;

import Helpers.DBConnection;
import Models.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This handles CRUD actions from the underlying data source (i.e. the database).
 */
public class CountryDAO {

    /**
     * This is the only class in the CountryDAO since we are not able to modify the table,
     * nor do we have need to return a single country.
     * @return Observable list of all countries
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Country> getAllCountries() throws SQLException, Exception {
        String sql = "SELECT * from countries";
        ObservableList<Country> allCountries = FXCollections.observableArrayList();

        PreparedStatement ps = DBConnection.startConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Country c = new Country(countryID, countryName);
            allCountries.add(c);
        }
        return allCountries;
    }
}
