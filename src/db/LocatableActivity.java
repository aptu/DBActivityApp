package db;

/**
 *
 */
public class LocatableActivity {
    private int locationId;
    private String locName;
    private int activityId;
    private String scenery;
    private String difficulty;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public LocatableActivity(int locationId, String locName, int activityId, String scenery, String difficulty) {
        this.locationId = locationId;
        this.locName = locName;
        this.activityId = activityId;
        this.difficulty = difficulty;
        this.scenery = scenery;
    }

    // Constructor that makes use of auto-increment property
    public LocatableActivity( String locName, int activityId, String scenery, String difficulty) {
        this.locName = locName;
        this.activityId = activityId;
        this.difficulty = difficulty;
        this.scenery = scenery;
    }

    public String getLocName() {
        return locName;
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
}
