/**
 * Created by pandabaka on 2/27/18.
 */
public class WayPoints {
    private  int locationId;
    private double latitude;
    private double longitude;
    private int seqNumber;

    public WayPoints(int locationId, double latitude, double longitude, int seqNumber) {
        this.locationId = locationId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.seqNumber = seqNumber;
    }

    // Constructor that makes use of auto-increment property
    public WayPoints( double latitude, double longitude, int seqNumber) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.seqNumber = seqNumber;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
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

    public int getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(int seqNumber) {
        this.seqNumber = seqNumber;
    }
}
