package db;
import java.time.LocalDateTime;
/**
 *
 */
public class ActivityHistory {
    private int loggedId;
    private int userId;
    private int activityId;
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
    public ActivityHistory( int userId, int activityId, LocalDateTime dateTime,
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

    public ActivityHistory(int loggedId, int userId, int activityId, LocalDateTime dateTime,
                           int calBurned, int duration, double distance, double latitude, double longitude) {

        this(userId, activityId, dateTime, calBurned, duration, distance, latitude, longitude);
        this.loggedId = loggedId;
    }

    // getters
    public int getUserId() {
        return userId;
    }

    public int getActivityId() {
        return activityId;
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
