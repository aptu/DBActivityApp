package db;
import java.time.LocalDateTime;
/**
 *
 */
public class ActivityHistoryItem {
    private int loggedId;
    private int userId;
    private int activityId;
    private String activityName;
    private LocalDateTime dateTime;
    private int calBurned;
    private int duration;
    private double distance;
    private double latitude;
    private double longitude;


    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


    // Constructor which uses the Auto-increment property of loggedId
    public ActivityHistoryItem( int userId, int activityId, LocalDateTime dateTime,
                            int calBurned, int duration,double distance, double latitude, double longitude) {
        this.userId = userId;
        this.activityId = activityId;
        this.dateTime = dateTime;
        this.calBurned = calBurned;
        this.duration = duration;
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ActivityHistoryItem(int loggedId, int userId, int activityID, String activity, LocalDateTime dateTime,
                           int calBurned, int duration, double distance, double latitude, double longitude) {

        this(userId, activityID, dateTime, calBurned, duration, distance, latitude, longitude);
        activityName = activity;
        this.loggedId = loggedId;
    }

    // getters
    public int getUserId() {
        return userId;
    }

    public int getActivityId() {
        return activityId;
    }

    public String getActivityName(){
        return activityName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getCalBurned() {
        return calBurned;
    }

    public int getDuration() {
        return duration;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getLoggedId() {
        return loggedId;
    }

    public void setLoggedId(int loggedId) {
        this.loggedId = loggedId;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
