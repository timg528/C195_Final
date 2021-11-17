package DAO.Contacts;

import Helpers.DBConnection;
import Models.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This handles CRUD actions from the underlying data source (i.e. the database).
 */
public class ContactDAO {
    /**
     * This handles reading the database and returning all the contacts within.
     * @return
     * @throws Exception
     */
    public static ObservableList<Contact> getAllContacts() throws Exception {
        String sql = "SELECT * from contacts";
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");

            Contact c = new Contact(contactID, contactName, contactEmail);
            allContacts.add(c);
        }

        return allContacts;
    }
}
