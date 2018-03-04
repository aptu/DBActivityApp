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
    private int distance;
    private int percentCompleted;
    private String location;


    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }


    // Constructor which uses the Auto-increment property of loggedId
    public ActivityHistory( int userId, int activityId, LocalDateTime dateTime,
                           int calBurned, int duration,int distance, int percentCompleted, String location) {
        this.userId = userId;
        this.activityId = activityId;
        this.dateTime = dateTime;
        this.calBurned = calBurned;
        this.duration = duration;
        this.percentCompleted = percentCompleted;
        this.location = location;

    }

    public ActivityHistory(int loggedId, int userId, int activityId, LocalDateTime dateTime,
                           int calBurned, int duration, int distance, int percentCompleted, String location) {

        this(userId, activityId, dateTime, calBurned, duration, distance, percentCompleted, location);
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

    public int getPercentCompleted() {
        return percentCompleted;
    }

    public String getLocation() {
        return location;
    }

    public int getLoggedId() {
        return loggedId;
    }

    public void setLoggedId(int loggedId) {
        this.loggedId = loggedId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPercentCompleted(int percentCompleted) {
        this.percentCompleted = percentCompleted;
    }
}
