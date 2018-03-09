package db;

/**
 *
 */
public class Activity {
    private int id;
    private String type;

    // Constructor takes Type of activity
    public Activity( String type) {
        this.type = type;
    }

    // Constructor takes ID and Type of Activity
    public Activity(int id, String type) {
        this.type = type;
        this.id = id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        if (id != activity.id) return false;
        return type.equals(activity.type);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
