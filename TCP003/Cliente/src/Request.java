import java.io.Serializable;

public class Request implements Serializable {
    private int objectID;
    private String method;
    private String type; // new | invoke
    private Place place;

    public Request(int _objectID, String _method) {
        this.objectID = _objectID;
        this.method = _method;
        this.type = "invoke";
    }

    public Request(Place place) {
        this.place = place;
        this.type = "new";
    }

    public int getObjectID() {
        return this.objectID;
    }

    public String getMethod() {
        return this.method;
    }

    public String getType() {
        return this.type;
    }

    public Place getPlace() {
        return this.place;
    }
}
