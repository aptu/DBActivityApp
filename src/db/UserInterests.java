package db;

/**
 * Created by pandabaka on 3/6/18.
 */
public class UserInterests {
    private int userId;
    private int activityId;

    public UserInterests(int userId, int activityId) {
        this.activityId = activityId;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }
}
