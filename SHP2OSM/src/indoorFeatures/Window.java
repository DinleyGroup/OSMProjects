package indoorFeatures;

public class Window extends ConnectionPoint {
    int breast;
    long height;
    int level;
    String name;
    long width;
    boolean isWindow;
    
    public Window(int breast, long height, int level, String name,
		  long width, boolean isWindow) {
	this.breast = breast;
	this.height = height;
	this.level = level;
	this.name = name;
	this.width = width;
	this.isWindow = isWindow;
    }
    
    public int getBreast() {
	return breast;
    }
    
    public void setBreast(int breast) {
	this.breast = breast;
    }
    
    public long getHeight() {
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
    
    public String getName() {
	return name;
    }
    
    public void setName(String name) {
	this.name = name;
    }
    
    public long getWidth() {
	return width;
    }
    
    public void setWidth(long width) {
	this.width = width;
    }
    
    public boolean isWindow() {
	return isWindow;
    }
    
    public void setWindow(boolean window) {
	isWindow = window;
    }
}
