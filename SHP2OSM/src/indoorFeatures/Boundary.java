package indoorFeatures;

public class Boundary implements IIndoorFeature {
int level;
    
    public Boundary(int level) {
	this.level = level;
    }
    
    public int getLevel() {
	return level;
    }
    
    public void setLevel(int level) {
	this.level = level;
    }
}
