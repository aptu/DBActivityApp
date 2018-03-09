package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * DBManager connects to ActivityApp database.
 * Holds current session information: userId, activityId
 * and lists of events and activities.
 */
public class DBManager {

    public static DBManager db;
    public int currUserId = -1;

    private Connection connect = null;
    int currActivityId = -1;
    Map<Integer, Activity> listOfActivities = new HashMap<Integer, Activity>();
    Map<String, Integer> activityIDMap = new HashMap<String, Integer>();
    Map<Integer, Event> listOfEvents = new HashMap<Integer, Event>();
    List<String> userInterests = new ArrayList<String>();
    List<String> allInterests = new ArrayList<String>();


    public DBManager() {

    }

    public void connect(String password) throws SQLException {
        // Establish a connection to the database
        connect = DriverManager.getConnection("jdbc:mysql://activityapp.c9wvxqrvbvpk.us-west-2.rds.amazonaws.com",
                "CSS475_2018", password);
        if(allInterests.size() == 0) {
            PreparedStatement statement = connect.prepareStatement("select ActivityID, Type from ActivityApp.Activity");
            String name;
            int id;
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                name = result.getString("Type");
                id = result.getInt("ActivityID");
                allInterests.add(name);
                activityIDMap.put(name, id);
            }
        }
    }

    public void logout()throws SQLException{
        connect.close();
        currUserId = -1;
        userInterests.clear();
    }

    public List<String> getAllActivities(){
        return allInterests;
    }

    public List<String> getListofAllActivities() throws  SQLException {

        List<String> activities = new ArrayList<String>();
        PreparedStatement statement = connect.prepareStatement("select LocName from ActivityApp.LocatableActivity");
        String name;
        ResultSet result= statement.executeQuery();
        while (result.next()) {
            name = result.getString("LocName");
            activities.add(name);
        }

        return activities;
    }

    // 1 When login button is pressed, we check if user exists and setup the userID
    public Boolean login(String userName) throws SQLException {
        PreparedStatement statement = connect.prepareStatement("select * from ActivityApp.User" +
                " where UserName = ?");
        statement.setString(1, userName);
        ResultSet result= statement.executeQuery();
        String activity = "None";
        int id = -1;
        while (result.next()) {
            id = result.getInt("UserID");
        }
        // Authentication of user
        if (id > 0) {
            this.currUserId = id;
            System.out.println("USER ID: " + this.currUserId);
            return  true;
        }
        else {
            System.out.println("User is not registered");
            return  false;
        }
    }

    public String getUserName() throws SQLException {
        String fname = null;
        String lname = null;
        if (this.currUserId > 0){
            PreparedStatement statement = connect.prepareStatement("select * from ActivityApp.User" +
                    " where UserID = ?");
            statement.setInt(1, this.currUserId);
            ResultSet result= statement.executeQuery();
            while (result.next()){
                fname = result.getString("FirstName");
                lname = result.getString("LastName");
            }
        }
        return fname + " " + lname;
    }

    public String getDOB() throws SQLException {
        String dob = null;
        PreparedStatement statement = connect.prepareStatement("select DOB from ActivityApp.User where UserID = ?");
        statement.setInt(1, this.currUserId);
        ResultSet result = statement.executeQuery();
        while (result.next()){
            dob = result.getDate("DOB").toString();
        }
        System.out.println(dob);
        return  dob;
    }

    public String getGender() throws SQLException {
        return "";
    }

    public List<String> getUserHistory() throws SQLException {
        List<String> history = new ArrayList<String>();

        PreparedStatement statement = connect.prepareStatement("select a.Type from ActivityApp.ActivityHistory ah, ActivityApp.Activity a" +
                " where ah.UserID = ? AND ah.ActivityID = a.ActivityID");
        statement.setInt(1, this.currUserId);
        ResultSet result = statement.executeQuery();
        //int id;
        String name;

        while (result.next()){
            //id = result.getInt("ActivityID");
            name = result.getString("Type");
            //Activity activity = new Activity(id, name);
            history.add(name);
        }

        return history;
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
    public List<String> findActivity(String activityType) throws SQLException {

        List<String> activities = new ArrayList<String>();
        PreparedStatement statement = connect.prepareStatement("select LocName from ActivityApp.LocatableActivity where ActivityId = ?");

        statement.setInt(1, activityIDMap.get(activityType));
        String name;
        ResultSet result= statement.executeQuery();
        while (result.next()) {
            name = result.getString("LocName");
            activities.add(name);
        }

        return activities;
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


    // TODO: select the list of booleans
    // Displays all interests (activities) or display my events
    public boolean[] getUserInterests() throws SQLException{
        userInterests.clear();

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
        boolean[] boolInterests = new boolean[10];

        System.out.println(userInterests);
        System.out.println(allInterests);
        // check if it worked

        for(int i = 0; i < boolInterests.length; i++){
            boolInterests[i] = userInterests.contains(allInterests.get(i));
        }

        //System.out.println(boolInterests);
        return boolInterests;
    }

    public void clearUserInterest() throws SQLException {
        PreparedStatement statement = connect.prepareStatement("delete from ActivityApp.UserInterests" +
                " where UserID = ?");
        statement.setInt(1, this.currUserId);
        statement.execute();
    }

    public void setUserInterest(int activityID) throws SQLException {
        PreparedStatement statement = connect.prepareStatement("insert into ActivityApp.UserInterests" +
                " (UserID, ActivityID) values (?, ?)");
        statement.setInt(1, this.currUserId);
        statement.setInt(2, activityID);
        statement.execute();
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

    // STATISTICS done last night DIDN"T TEST THEM TODO: TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public double getLastMonthDistance() throws SQLException {
        double distance = 0.0;
        PreparedStatement statement = connect.prepareStatement("select sum(Distance) as dist " +
                "from ActivityApp.ActivityHistory where UserID = ? and DateTime <= Date_sub(now(), interval 1 month");
        statement.setInt(1, this.currUserId);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            distance = result.getDouble("dist");
        }
        System.out.println("Distance per month: "  + distance);
        return distance;
    }

    public double getLastMonthCaloriesBurned() throws SQLException {
        double calburned = 0.0;
        PreparedStatement statement = connect.prepareStatement("select sum(CalBurned) as calBurned " +
                "from ActivityApp.ActivityHistory where UserID = ? and DateTime <= Date_sub(now(), interval 1 month");
        statement.setInt(1, this.currUserId);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            calburned = result.getDouble("calBurned");
        }
        System.out.println("Distance per month: "  + calburned);
        return calburned;
    }

    public Map<String, Double> getAvgCaloriesBurned() throws SQLException {
        Map<String, Double> avgCalBurned = new HashMap<>();
        double avgCal = 0.0;
        String type = "";
        PreparedStatement statement = connect.prepareStatement("select Activity.Type, avg(ActivityHistory.CalBurned) as avgCalBurned " +
                "from ActivityApp.ActivityHistory, ActivityApp.Activity where ActivityHistory.UserID = ? group by ActivityHistory.ActivityID");
        statement.setInt(1, this.currUserId);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            type = result.getString("Type");
            avgCal = result.getDouble("avgCalBurned");
            avgCalBurned.put(type, avgCal);
        }
        System.out.println(avgCalBurned);
        return avgCalBurned;
    }



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
        //dbm.findActivity(101);
        //dbm.findActivity(109);
        //dbm.findActivity(10);

        // Test login method
        dbm.login("jsmith");
        //dbm.login("jwhUUe");
        dbm.getDOB();
        dbm.getUserInterests();

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

        // get stats
        dbm.getLastMonthDistance();

        // create new Event
        //dbm.saveEvent(new Event(LocalDateTime.now(), "Rattlesnake Ledge", 103 ));





    }



}
