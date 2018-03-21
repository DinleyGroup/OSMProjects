package coordinate.convertion;

public class Node {
    String id;
    double lat;
    double lon;
    
    public String getId() {
	return id;
    }
    
    public double getLat() {
	return lat;
    }
    
    public double getLon() {
	return lon;
    }
    
    public void setLat(double lat) {
	this.lat = lat;
    }
    
    public void setLon(double lon) {
	this.lon = lon;
    }
}
