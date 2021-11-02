package DAO.Customers;

import Helpers.DBConnection;
import Models.Customer;
import Models.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class CustomerDAO {

    public static ObservableList<Customer> getAllCustomers() throws Exception {
        String sql = "SELECT * from customers";
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        allCustomers.clear();

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

    public static void addCustomer(String name, String address, String postal,
                                   String phone, int divisionID) throws Exception {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, "+
                     "Create_Date, Created_By, Last_Update, Last_Updated_by, Division_ID)"+
                     "VALUES (?,?,?,?,CURRENT_TIMESTAMP,?,CURRENT_TIMESTAMP,?,?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);                         // Customer_Name
        ps.setString(2,address);                       // Address
        ps.setString(3,postal);                        // Postal_Code
        ps.setString(4,phone);                         // Phone
        ps.setInt(5, Data.getCurrentUser());           // Created_By
        ps.setInt(6, Data.getCurrentUser());           // Last_Updated_By
        ps.setInt(7, divisionID);                      // Division_ID

        ps.executeUpdate();

    }

    public static void updateCustomer(int id, String name, String address, String postal,
                                   String phone, int divisionID) throws Exception {

        String sql = "UPDATE customers SET " +
                     "Customer_Name = ?, Address = ?, Postal_Code = ?, " +
                     "Phone = ?, Last_Update = CURRENT_TIMESTAMP, Last_Updated_by = ?, " +
                     "Division_ID = ? " +
                     "WHERE Customer_ID = ?";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postal);
        ps.setString(4, phone);
        ps.setInt(5, Data.getCurrentUser());
        ps.setInt(6, divisionID);
        ps.setInt(7, id);

        ps.executeUpdate();

    }
}
