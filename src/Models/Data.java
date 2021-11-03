package Models;


import DAO.Appointments.AppointmentDAO;
import DAO.Contacts.ContactDAO;
import DAO.Customers.CustomerDAO;
import DAO.Locations.CountryDAO;
import DAO.Locations.DivisionDAO;

import DAO.User.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * This class essentially holds all of the database information locally. It was becoming more
 * and more difficult to write disparate code to deal with the data on each form. This acts like the
 * Inventory object in the Software 1 class.
 */
public class Data {
    private static int currentUser = 0;        // Set to 0 for testing
    private static ObservableList<User> users = FXCollections.observableArrayList();
    private static ObservableList<Country> countries = FXCollections.observableArrayList();
    private static ObservableList<Division> divisions = FXCollections.observableArrayList();
    private static ObservableList<Customer> customers = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private static ObservableList<Contact> contacts = FXCollections.observableArrayList();


    /**
     * This method is to initialize the Data object. It calls the various generate* methods
     * to copy the database locally.
     */
    public static void generateAll() throws Exception {

        generateCountries();
        generateDivisions();
        generateContacts();
        generateCustomers();
        generateAppointments();
        generateUsers();
    }

    public static int getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(int currentUser) {
        currentUser = currentUser;
    }

    /**
     * This method calls the countries table and generates a list of country objects.
     */
    public static void generateCountries() throws Exception {
        countries.clear();
        countries = CountryDAO.getAllCountries();
    }

    public static ObservableList<Country> getCountries() {
        return countries;
    }

    public static Country getCountry(int countryID) {
        for (Country c: countries) {
            if (c.getCountryID() == countryID) {
                return c;
            }
        }
        return null;
    }

    public static void generateDivisions() throws Exception {
        divisions.clear();
        divisions = DivisionDAO.getAllDivisions();
    }

    public static ObservableList<Division> getDivisions() {
        return divisions;
    }

    public static Division getDivision(int divisionID) {
        for (Division d : divisions) {
            if (d.getDivisionID() == divisionID) {
                return d;
            }
        }
        return null;
    }

    public static void generateCustomers() throws Exception {

        customers = CustomerDAO.getAllCustomers();
    }

    public static Customer getCustomer(int customerID) {
        for (Customer c: customers) {
            if (c.getId() == customerID) {
                return c;
            }
        }
        return null;
    }

    public static ObservableList<Customer> getCustomers() {
        return customers;
    }

    public static void generateAppointments() throws Exception {
        appointments.clear();
        appointments = AppointmentDAO.getAllAppointments();
    }

    public static ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    public static void generateContacts() throws Exception {
        contacts = ContactDAO.getAllContacts();
    }

    public static ObservableList<Contact> getContacts() {return contacts;}

    public static Contact getContact(int contactID) {
        for (Contact c: contacts) {
            if (c.getContactID() == contactID) {
                return c;
            }
        }
        return null;
    }

    public static void generateUsers() throws Exception {
        users = UserDAO.getAllUsers();
    }

    public static ObservableList<User> getUsers() {return users;}

    public static User getUser(int userID) {
        for (User u: users) {
            if (u.getId() == userID) {
                return u;
            }
        }
        return null;
    }
}
