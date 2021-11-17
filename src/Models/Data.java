package Models;


import DAO.Appointments.AppointmentDAO;
import DAO.Contacts.ContactDAO;
import DAO.Customers.CustomerDAO;
import DAO.Locations.CountryDAO;
import DAO.Locations.DivisionDAO;

import DAO.User.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * This class essentially holds all of the database information locally. It was becoming more
 * and more difficult to write disparate code to deal with the data on each form. This acts like the
 * Inventory object in the Software 1 class.
 */
public class Data {
    private static ResourceBundle rb;
    private static ZoneId tz;
    private static int currentUser;        // Set to 0 for testing
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


    /**
     * This returns the currently logged-in user
     * @return currentUser
     */
    public static int getCurrentUser() {
        return currentUser;
    }

    /**
     * This sets the current user as part of the Login screen's login function
     * @param user ID of the user who just logged in
     */
    public static void setCurrentUser(int user) {
        currentUser = user;
    }

    /**
     * This method calls the countries table and generates a list of country objects.
     */
    public static void generateCountries() throws Exception {
        countries.clear();
        countries = CountryDAO.getAllCountries();
    }

    /**
     * Returns an ObservableList of country objects
     * @return countries list
     */
    public static ObservableList<Country> getCountries() {
        return countries;
    }

    /**
     * Returns a country object specified by the country ID
     * @param countryID the ID of the country object to be returned
     * @return country specified by ID
     */
    public static Country getCountry(int countryID) {
        for (Country c: countries) {
            if (c.getCountryID() == countryID) {
                return c;
            }
        }
        return null;
    }

    /**
     * Generates an observable list of Divisions
     * @throws Exception In case of SQL or other exception
     */
    public static void generateDivisions() throws Exception {
        divisions.clear();
        divisions = DivisionDAO.getAllDivisions();
    }

    /**
     * Returns an ObservableList of Divisions
     * @return divisions
     */
    public static ObservableList<Division> getDivisions() {
        return divisions;
    }

    /**
     * Returns a Division object specified by ID
     * @param divisionID The ID of the division object to be returned
     * @return division
     */
    public static Division getDivision(int divisionID) {
        for (Division d : divisions) {
            if (d.getDivisionID() == divisionID) {
                return d;
            }
        }
        return null;
    }

    /**
     * Creates an observable list of customers from the database
     * @throws Exception In case of a SQL or other Exception
     */
    public static void generateCustomers() throws Exception {

        customers = CustomerDAO.getAllCustomers();
    }

    /**
     * Returns a customer specified by ID
     * @param customerID of the Customer object to be returned
     * @return customer object
     */
    public static Customer getCustomer(int customerID) {
        for (Customer c: customers) {
            if (c.getId() == customerID) {
                return c;
            }
        }
        return null;
    }

    /**
     * Returns an Observable list of all customers in the database
     * @return customers
     */
    public static ObservableList<Customer> getCustomers() {
        return customers;
    }

    /**
     * Creates an ObservableList of all the appointments in the database
     * @throws Exception in case of SQL or other exceptions
     */
    public static void generateAppointments() throws Exception {
        appointments.clear();
        appointments = AppointmentDAO.getAllAppointments();
    }

    /**
     * Returns a list of all appointments in the database
     * @return appointments
     */
    public static ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Creates an Observable list of Contacts in the database
     * @throws Exception in case of SQL or other exception
     */
    public static void generateContacts() throws Exception {
        contacts = ContactDAO.getAllContacts();
    }

    /**
     * Returns an ObservableList of all contacts in the database
     * @return contacts
     */
    public static ObservableList<Contact> getContacts() {return contacts;}

    /**
     * Return a single contact object specified by contactID
     * @param contactID of the Contact to be returned
     * @return contact or null if the contactID is invalid
     */
    public static Contact getContact(int contactID) {
        for (Contact c: contacts) {
            if (c.getContactID() == contactID) {
                return c;
            }
        }
        return null;
    }

    /**
     * Creates an ObservableList of all users in the database
     * @throws Exception in case of SQL or other Exception
     */
    public static void generateUsers() throws Exception {
        users = UserDAO.getAllUsers();
    }

    /**
     * Returns an Observable list of all users in the database
     * @return users
     */
    public static ObservableList<User> getUsers() {return users;}

    /**
     * Returns a single user object specified by userID
     * @param userID of the User object to be returned
     * @return user or null if no user for userID exists
     */
    public static User getUser(int userID) {
        for (User u: users) {
            if (u.getId() == userID) {
                return u;
            }
        }
        return null;
    }

    /**
     * Creates a timezone object and selects resource bundle based on the application user's computer settings
     */
    public static void generateLocalData() {
        tz = ZoneId.systemDefault();
        rb = ResourceBundle.getBundle("Main/login", Locale.getDefault());
    }

    /**
     * Returns the local timezone of the user
     * @return tz
     */
    public static ZoneId getLocalTimezone() {
        return tz;
    }

    /**
     * Returns the resource bundle for localization
     * @return rb
     */
    public static ResourceBundle getRB() {
        return rb;
    }
}
