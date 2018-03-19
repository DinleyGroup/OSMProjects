package indoorFeatures;

public class Route implements IIndoorFeature{
    String highway;
    boolean isIndoor;
    int level;
    
    public Route(String highway, boolean isIndoor, int level) {
	this.highway = highway;
	this.isIndoor = isIndoor;
	this.level = level;
    }
    
    public String getHighway() {
	return highway;
    }
    
    public void setHighway(String highway) {
	this.highway = highway;
    }
    
    public boolean isIndoor() {
	return isIndoor;
    }
    
    public void setIndoor(boolean indoor) {
	isIndoor = indoor;
    }
    
    public int getLevel() {
	return level;
    }
    
    public void setLevel(int level) {
	this.level = level;
    }
}
