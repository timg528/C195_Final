package DAO.Customers;

import Helpers.DBConnection;
import Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    public static ObservableList<Customer> getAllCustomers() throws SQLException, Exception {
        String sql = "SELECT * from customers";
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        PreparedStatement ps = DBConnection.startConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String customerPostal = rs.getString("Postal_Code");
            String customerPhone = rs.getString("Phone");
            int customerDivision = rs.getInt("Division_ID");

            Customer c = new Customer(customerID, customerName, customerAddress, customerPostal,
                                    customerDivision, customerPhone);
            allCustomers.add(c);
        }
        return allCustomers;
    }
}
