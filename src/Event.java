/**
 *
 */

import java.sql.Timestamp;

public class Event {
    private int eventId;
    private  int activityId;
    private java.sql.Timestamp dateTime; // not sure
    private String eventName;

    // Constructor that makes use of auto-increment property
    public Event(int activityId, java.sql.Timestamp dateTime, String eventName){
        this.activityId = activityId;
        this.dateTime = dateTime;
        this.eventName = eventName;
    }


    public Event(int eventId, int activityId, java.sql.Timestamp dateTime, String eventName){
        this.eventId = eventId;
        this.activityId = activityId;
        this.dateTime = dateTime;
        this.eventName = eventName;
    }


    public int getEventId() {
        return eventId;
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

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", activityId=" + activityId +
                ", dateTime=" + dateTime +
                ", eventName='" + eventName + '\'' +
                '}';
    }
}
