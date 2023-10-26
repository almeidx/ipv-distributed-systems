import java.io.*;

public class Place implements Serializable {
    private String postalCode;
    private String locality;

    public Place(String postalCode, String locality) {
        this.postalCode = postalCode;
        this.locality = locality;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public String getLocality() {
        return this.locality;
    }
}
