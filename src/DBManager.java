//import com.mysql.cj.jdbc.Driver;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.ArrayList;

/**
 * Created by pandabaka on 2/13/18.
 */
public class DBManager {
    private Connection connect = null;


    public DBManager() throws SQLException {

        // This will load the MySQL driver, each DB has its own driver
        //Class.forName("com.mysql.cj.jdbc.Driver");
        // Setup the connection with the DB
        connect = DriverManager.getConnection("jdbc:mysql://activityapp.c9wvxqrvbvpk.us-west-2.rds.amazonaws.com", "CSS475_2018", "Databases_2018");


    }


    public void saveActivty(Activity activity) throws SQLException {
        PreparedStatement statement = connect.prepareStatement("insert into ActivityApp.Activity (Type) values (?)");
        statement.setString(1, activity.getType());
        statement.execute();
    }

    public Activity findActivity(int id) throws SQLException {
        Activity activity = null;
        return activity;
    }


    public void readDataBase() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://activityapp.c9wvxqrvbvpk.us-west-2.rds.amazonaws.com", "CSS475_2018", "Databases_2018");



        /*    // PreparedStatements can use variables and are more efficient
            preparedStatement = connect
                    .prepareStatement("insert into  feedback.comments values (default, ?, ?, ?, ? , ?, ?)");
            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            // Parameters start with 1
            preparedStatement.setString(1, "Test");
            preparedStatement.setString(2, "TestEmail");
            preparedStatement.setString(3, "TestWebpage");
            preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
            preparedStatement.setString(5, "TestSummary");
            preparedStatement.setString(6, "TestComment");
            preparedStatement.executeUpdate();

            preparedStatement = connect
                    .prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            resultSet = preparedStatement.executeQuery();
            writeResultSet(resultSet);

            // Remove again the insert comment
            preparedStatement = connect
                    .prepareStatement("delete from feedback.comments where myuser= ? ; ");
            preparedStatement.setString(1, "Test");
            preparedStatement.executeUpdate();

            resultSet = statement
                    .executeQuery("select * from feedback.comments");
            writeMetaData(resultSet);
*/
        } catch (Exception e) {
            System.out.println("Caught exception: "  + e);
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

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String user = resultSet.getString("myuser");
            String website = resultSet.getString("webpage");
            String summary = resultSet.getString("summary");
            Date date = resultSet.getDate("datum");
            String comment = resultSet.getString("comments");
            System.out.println("User: " + user);
            System.out.println("Website: " + website);
            System.out.println("summary: " + summary);
            System.out.println("Date: " + date);
            System.out.println("Comment: " + comment);
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

            // DONE: Load Avtivity Table data
            //dbm.saveActivty(new Activity(101, "Biking"));
            //dbm.saveActivty(new Activity(102, "Running"));
            //dbm.saveActivty(new Activity(103, "Hiking"));
            //dbm.saveActivty(new Activity(104, "Snowboarding"));
            //dbm.saveActivty(new Activity(105, "Skiing"));
            //dbm.saveActivty(new Activity(106, "Fishing"));
            //dbm.saveActivty(new Activity(107, "MountBiking"));
            //dbm.saveActivty(new Activity(108, "Sky Diving"));
            //dbm.saveActivty(new Activity(109, "Climbing"));
            //dbm.saveActivty(new Activity("Rafting"));

            // Load
        }

    }

