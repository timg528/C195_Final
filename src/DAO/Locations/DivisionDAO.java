package DAO.Locations;

import Helpers.DBConnection;
import Models.Country;
import Models.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This handles CRUD actions from the underlying data source (i.e. the database).
 */
public class DivisionDAO {

    /**
     * This returns the list of all Divisions, regardless of Country
     * @return Observable list of allDivisions
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Division> getAllDivisions() throws SQLException, Exception {
        String sql = "SELECT * from first_level_divisions";
        ObservableList<Division> allDivisions = FXCollections.observableArrayList();

        PreparedStatement ps = DBConnection.startConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");

            Division d = new Division(divisionID, divisionName, countryID);
            allDivisions.add(d);
        }
        return allDivisions;
    }
}
