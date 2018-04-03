package org.citygml;

import coordinate.convertion.Point;


public class TagPro {
    public double x, y, z, breast, height, width, level;
    long id;
    String wtype = "",name="";
    boolean window=true;
    boolean door=true;
    
    //windows(x,y,z)
    public TagPro(double x, double y, double z, double breast, double height, double width, double level, long id, String name, boolean window) {
	this.x = x;
	this.y = y;
	this.z = z;
	this.breast = breast;
	this.height = height;
	this.width = width;
	this.level = level;
	this.id = id;
	this.name = name;
	this.window = window;
    }
    
    //windows(x,y)
    public TagPro(double x, double y, double breast, double height, double width, double level, long id, String name, boolean window) {
	this.x = x;
	this.y = y;
	this.breast = breast;
	this.height = height;
	this.width = width;
	this.level = level;
	this.id = id;
	this.name = name;
	this.window = window;
    }
    
    //door(x,y)
    public TagPro(double x, double y, double height, double width, double level, long id, String name, boolean door) {
	this.x = x;
	this.y = y;
	this.height = height;
	this.width = width;
	this.level = level;
	this.id = id;
	this.name = name;
	this.door = door;
    }
    
    
/*    public Point[] computeBounds(double dv1_x, double dv1_y){
	
	double length = Math.sqrt(dv1_x * dv1_x + dv1_y * dv1_y);
	
	double bound_01_x = x - (width/2.0)/length * dv1_x;
	double bound_01_y = y - (width/2.0)/length * dv1_y;
	double bound_02_x = x + (width/2.0)/length * dv1_x;
	double bound_02_y = y + (width/2.0)/length * dv1_y;
	
	Point[] bounds = new Point[5];
	bounds[0] = new Point(bound_01_x, bound_01_y, (z + breast));
	bounds[1] = new Point(bound_02_x, bound_02_y, (z + breast));
	bounds[2] = new Point(bound_02_x, bound_02_y, (z + breast + height));
	bounds[3] = new Point(bound_01_x, bound_01_y, (z + breast + height));
	bounds[4] = new Point(bound_01_x, bound_01_y, (z + breast));
	
	return bounds;
    }
    
    public Point[] computeBounds(){
	return computeBounds(this.dv1_x, this.dv1_y);
    }*/
    
}
