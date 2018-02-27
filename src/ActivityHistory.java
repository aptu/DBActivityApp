import java.sql.Timestamp;

/**
 *
 */
public class ActivityHistory {
    private int loggedId;
    private int userId;
    private int activityId;
    private java.sql.Timestamp dateTime;
    private int calBurned;
    private int duration;

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    private int distance;
    private int percentCompleted;
    private String location;

    // Constructor which uses the Auto-increment property of loggedId
    public ActivityHistory( int userId, int activityId, java.sql.Timestamp dateTime,
                           int calBurned, int duration, int percentCompleted, String location) {
        this.userId = userId;
        this.activityId = activityId;
        this.dateTime = dateTime;
        this.calBurned = calBurned;
        this.duration = duration;
        this.percentCompleted = percentCompleted;
        this.location = location;

    }

    public ActivityHistory(int loggedId, int userId, int activityId, java.sql.Timestamp dateTime,
                           int calBurned, int duration, int percentCompleted, String location) {

        this(userId, activityId, dateTime, calBurned, duration, percentCompleted, location);
        this.loggedId = loggedId;
    }

    // getters
    public int getUserId() {
        return userId;
    }

    public int getActivityId() {
        return activityId;
    }


    public Timestamp getDateTime() {
        return dateTime;
    }

    public int getCalBurned() {
        return calBurned;
    }

    public int getDuration() {
        return duration;
    }

    public int getPercentCompleted() {
        return percentCompleted;
    }

    public String getLocation() {
        return location;
    }
}
