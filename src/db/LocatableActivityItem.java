package db;

/**
 *a
 */
public class LocatableActivityItem {
    private int locationId;
    private String locName;
    private int activityId;
    private String activityName;
    private String scenery;
    private String difficulty;
    private int latitude;
    private int longitude;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public LocatableActivityItem(int locationId, String locName, String activity, String scenery, String difficulty, int lat, int lon) {
        this.locationId = locationId;
        this.locName = locName;
        this.activityName = activity;
        //this.activityId = activityId;
        this.difficulty = difficulty;
        this.scenery = scenery;
        this.latitude = lat;
        this.longitude = lon;
    }

    // Constructor that makes use of auto-increment property
    public LocatableActivityItem( String locName, String activity, String scenery, String difficulty, int lat, int lon) {
        this.locName = locName;
        //this.activityId = activityId;
        this.activityName = activity;
        this.difficulty = difficulty;
        this.scenery = scenery;
        this.latitude = lat;
        this.longitude = lon;
    }

    public String getLocName() {
        return locName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getScenery() {
        return scenery;
    }

    public void setScenery(String scenery) {
        this.scenery = scenery;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getLatitude(){
        return latitude;
    }

    public int getLongitude(){
        return longitude;
    }
}
