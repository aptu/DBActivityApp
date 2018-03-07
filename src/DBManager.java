//import com.mysql.cj.jdbc.Driver;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * DBManager connects to ActivityApp database.
 * Holds current session information: userId, activityId
 * and lists of events and activities.
 */
public class DBManager {
    private Connection connect = null;
    int currUserId = -1;
    int currActivityId = -1;
    Map<Integer, Activity> listOfActivities = new HashMap<>();
    Map<Integer, Event> listOfEvents = new HashMap<>();
    List<String> userInterests = new LinkedList<>();


    public DBManager() throws SQLException {
       // Establish a connection to the database
        connect = DriverManager.getConnection("jdbc:mysql://activityapp.c9wvxqrvbvpk.us-west-2.rds.amazonaws.com",
                "CSS475_2018", "Databases_2018");
    }

    // 1 When login button is pressed, we check if user exists and setup the userID
    public int login(String firstName, String lastName) throws SQLException {
        PreparedStatement statement = connect.prepareStatement("select * from ActivityApp.User" +
                " where FirstName = ? and LastName = ?");
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        ResultSet result= statement.executeQuery();
        String activity = "None";
        int id = -1;
        while (result.next()) {
            id = result.getInt("UserID");
            System.out.println(id);
        }
        // Authentication of user
        if (id > 0) {
            this.currUserId = id;
        }
        else {
            System.out.println("User is not registered");
        }
        return id;
    }

    // Insert new activity into the table Activity
    public void saveActivity(Activity activity) throws SQLException {
        PreparedStatement statement = connect.prepareStatement("insert into ActivityApp.Activity (Type) values (?)");
        statement.setString(1, activity.getType());
        statement.execute();
    }

    // Insert new activity into the table ActivityHistory
    public void saveActivitytoHistory(ActivityHistory activity) throws SQLException {
        PreparedStatement statement = connect.prepareStatement("insert into ActivityApp.ActivityHistory" +
                " (LoggedID, UserID, ActivityID, DateTime, CalBurned, Duration, Distance, Latitude, Longitude) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setInt(1, activity.getLoggedId());
        statement.setInt(2, activity.getUserId());
        statement.setInt(3, activity.getActivityId());
        statement.setTimestamp(4, java.sql.Timestamp.valueOf(activity.getDateTime()));
        statement.setInt(5, activity.getCalBurned());
        statement.setInt(6, activity.getDuration());
        statement.setDouble(7, activity.getDistance());
        statement.setDouble(8, activity.getLatitude());
        statement.setDouble(9, activity.getLongitude());
        statement.execute();
    }

    // Query the list of all activities and save in current session
    //TODO: should I return the list??
    public void getListOfActivities() throws SQLException {
        PreparedStatement statement = connect.prepareStatement("select * from ActivityApp.Activity");
        ResultSet result = statement.executeQuery();
        int id;
        String name;
        while (result.next()){
            id = result.getInt("ActivityID");
            name = result.getString("Type");
            Activity activity = new Activity(id, name);
            this.listOfActivities.put(id, activity);
        }
        // check if it worked
        for (int e : listOfActivities.keySet()){
            System.out.println(e + "\t " + listOfActivities.get(e).toString());
        }
    }


     // Query the list of all activities and save in current session
    //TODO: should I return the list??
    public void getListOfEvents() throws SQLException {
        PreparedStatement statement = connect.prepareStatement("select * from ActivityApp.Event");
        ResultSet result = statement.executeQuery();
        int id, aid;
        java.sql.Timestamp startTime, endTime; //TODO: format date????
        String name;
        double latitude, longitude;
        while (result.next()){
            id = result.getInt("EventID");
            aid = result.getInt("ActivityID");
            startTime = result.getTimestamp("StartTime");
            endTime = result.getTimestamp("EndTime");
            name = result.getString("EventName");
            latitude = result.getDouble("Latitude");
            longitude = result.getDouble("LOngitude");
            Event event = new Event(id,aid, startTime.toLocalDateTime(),endTime.toLocalDateTime(), name, latitude, longitude);
            this.listOfEvents.put(id, event);
        }
        // check if it worked
        for (int e : listOfEvents.keySet()){
            System.out.println(e + "\t " + listOfEvents.get(e).toString());
        }
    }
    // Select activity by ID
    // Returns the name of activity
    public String findActivity(int id) throws SQLException {
        PreparedStatement statement = connect.prepareStatement("select * from ActivityApp.Activity where ActivityId = ?");
        statement.setInt(1, id);
        ResultSet result= statement.executeQuery();
        String activity = "None";
        while(result.next()) {
            activity = result.getString("Type");
            System.out.println(activity);
        }
        if (activity.equals("None")){
            System.out.println("Activity ID does not exist");
        }
        return activity;
    }


    // Set currActivity & update it
    public boolean recordActivity(int id)  {
        try {
            saveActivity(new Activity(listOfActivities.get(id).getType()));
            currActivityId = id;
            return true;
        } catch (SQLException e) {
            System.out.println("[Record Activity]: " + e);
            return false;
        }
    }

    // Displays all interests (activities) or display my events
    public void  getUserInterests() throws SQLException{
        // the user can select any activity from the list and save it in his profile (del prev and add userInterests table)
        PreparedStatement statement = connect.prepareStatement("select Type from ActivityApp.UserInterests i, " +
                "ActivityApp.Activity a where i.ActivityID = a.ActivityID and i.UserID = ?");
        statement.setInt(1, this.currUserId); //TODO: do we need another UserID? I think not
        ResultSet result = statement.executeQuery();
        String activity;
        while (result.next()){
            activity = result.getString("Type");
            this.userInterests.add(activity);
        }
        // check if it worked
        for (String a : userInterests){
            System.out.println(a);
        }
    }

    // Insert new event into the table Event
    public void saveEvent(Event event) throws SQLException {
        PreparedStatement statement = connect.prepareStatement("insert into ActivityApp.Event" +
                " (ActivityID, StartTime, EndTime, EventName, Latitude, Longitude) values (?, ?, ?, ?, ?, ?)");
        statement.setTimestamp(2, java.sql.Timestamp.valueOf(event.getStartTime()));
        statement.setTimestamp(3, java.sql.Timestamp.valueOf(event.getEndTime()));
        statement.setString(4, event.getEventName());
        statement.setInt(1, event.getActivityId());
        statement.setDouble(5, event.getLatitude());
        statement.setDouble(6, event.getLongitude());
        statement.execute();
    }
    //

    // Methods to get statistics

    public double getMySpeed(){
        double myspeed = 0.0;
        return myspeed;
    }

    public double getTotalSpeed() {
        double speed = 0.0;
        return speed;
    }

    public double getAvgSpeed() {
        double speed = 0.0;
        return speed;

    }








    public void readDataBase() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://activityapp.c9wvxqrvbvpk.us-west-2.rds.amazonaws.com", "CSS475_2018", "Databases_2018");

        } catch (Exception e) {
            System.out.println("[Error connecting]: "  + e);
        }
    }
/*
    private void writeMetaData(ResultSet resultSet) throws SQLException {
        //  Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
        }
    }


    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

*/


    public static void main (String[]args) throws Exception {
            DBManager dbm = new DBManager();

            // DONE: Load Activity Table data
            //dbm.saveActivity(new Activity(101, "Biking"));
            //dbm.saveActivity(new Activity(102, "Running"));
            //dbm.saveActivity(new Activity(103, "Hiking"));
            //dbm.saveActivity(new Activity(104, "Snowboarding"));
            //dbm.saveActivity(new Activity(105, "Skiing"));
            //dbm.saveActivity(new Activity(106, "Fishing"));
            //dbm.saveActivity(new Activity(107, "MountBiking"));
            //dbm.saveActivity(new Activity(108, "Sky Diving"));
            //dbm.saveActivity(new Activity(109, "Climbing"));
            //dbm.saveActivity(new Activity("Rafting"));

            // Test findActivity method:
            dbm.findActivity(101);
            dbm.findActivity(109);
            dbm.findActivity(10);

            // Test login method
            dbm.login("John", "Smith");
            dbm.login("Asia", "Tsai");

            // Test getListof Activities and getListOfEvents
            dbm.getListOfActivities();
            dbm.getListOfEvents();

            // Save activity to history
            /*dbm.saveActivitytoHistory(new ActivityHistory(1039, 2, 101,
                    LocalDateTime.now(),200, 50, 5,17364, 4949 ));

            dbm.saveActivitytoHistory(new ActivityHistory(1021, 2, 103,
                LocalDateTime.now(),1000, 500, 15, 10210, 674937 ));

            dbm.saveActivitytoHistory(new ActivityHistory(1032, 3, 105,
                LocalDateTime.now(),200, 100, 5, 10520, 46382 )); */



        // See user Interests
            dbm.getUserInterests();

            // create new Event
            //dbm.saveEvent(new Event(LocalDateTime.now(), "Rattlesnake Ledge", 103 ));




        }

    }

