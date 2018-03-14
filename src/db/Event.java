package db;
import java.time.LocalDateTime;
/**
 *
 */
public class Event {
    private int eventId;
    private  int activityId;
    private String activityName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String eventName;
    private double latitude;
    private double longitude;

    // Constructor that makes use of auto-increment property
    public Event(int activityId, LocalDateTime startTime, LocalDateTime endTime, String eventName, double latitude, double longitude){
        this.activityId = activityId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventName = eventName;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public Event(int eventId,int activityId, String activity, LocalDateTime startTime, LocalDateTime endTime, String eventName, double latitude, double longitude){
        this.eventId = eventId;
        this.activityId = activityId;
        this.activityName = activity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventName = eventName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getEventId() {
        return eventId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


    @Override public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", activityId=" + activityId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", eventName='" + eventName +
                ", latitude=" + latitude +
                ", longitude=" + longitude + '\'' +
                '}';
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
