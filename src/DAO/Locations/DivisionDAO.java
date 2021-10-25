package DAO.Locations;

import Helpers.DBConnection;
import Models.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionDAO {

    public ObservableList<Country> getAllCountries() throws SQLException, Exception {
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
