
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
}
