package indoorFeatures;

public class Floor implements IIndoorFeature{
    
    long height;
    int level;
    String levelUsage;
    String name;
    String type;
    
    public Floor(long height, int level, String levelUsage, String name, String type) {
	this.height = height;
	this.level = level;
	this.levelUsage = levelUsage;
	this.name = name;
	this.type = type;
    }
    
    public long getHeight(){
	return height;
    }
    
    public void setHeight(long height) {
	this.height = height;
    }
    
    public int getLevel() {
	return level;
    }
    
    public void setLevel(int level) {
	this.level = level;
    }
    
    public String getLevelUsage() {
	return levelUsage;
    }
    
    public void setLevelUsage(String levelUsage) {
	this.levelUsage = levelUsage;
    }
    
    public String getName() {
	return name;
    }
    
    public void setName(String name) {
	this.name = name;
    }
    
    public String getType() {
	return type;
    }
    
    public void setType(String type) {
	this.type = type;
    }
}
