package coordinate.convertion;

import com.vividsolutions.jts.geom.Coordinate;
import org.geotools.geometry.DirectPosition2D;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;

import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

public class CoordinateConversion {

/*    example
    Point WGS84TO
*/
    
    public static void main(String[] args) {
	
	Point ll2 = new Point(30.52593076994, 114.39294714004, 44);
	Point ll3 = new Point(30.52624012387, 114.39221818634, 44);
	
	CoordinateConversion cts = new CoordinateConversion();
	
	System.out.print("ll2: ");
	cts.coordConvertPrint("EPSG:4326", "EPSG:4547", ll2);
	
	cts.coordConvert("EPSG:4326", "EPSG:4547", ll3);
	System.out.println("ll3: " + ll3.x + " " + ll3.y);
	
    }
    
    //Convert
    public Point coordConvert(String sourceCrs_string, String targetCrs_string, Point point) {
	
	CoordinateReferenceSystem sourceCrs = null;
	CoordinateReferenceSystem targetCrs = null;
	try {
	    sourceCrs = CRS.decode(sourceCrs_string); //"epsg:4326"
	} catch (NoSuchAuthorityCodeException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (FactoryException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	try {
	    targetCrs = CRS.decode(targetCrs_string);//4547
	} catch (NoSuchAuthorityCodeException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (FactoryException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	double lat = point.x;
	double lng = point.y;
	
	boolean lenient = true;
	MathTransform mathTransform = null;
	try {
	    mathTransform = CRS.findMathTransform(sourceCrs, targetCrs, lenient);
	} catch (NoSuchAuthorityCodeException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (FactoryException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	DirectPosition2D srcDirectPosition2D
		= new DirectPosition2D(sourceCrs, lng, lat);
	DirectPosition2D destDirectPosition2D
		= new DirectPosition2D();
	
	try {
	    mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);
	} catch (MismatchedDimensionException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (TransformException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	point.x = destDirectPosition2D.y; //lng
	point.y = destDirectPosition2D.x; //lat
	
	return   point;
    }
    
    
    public void coordConvertPrint(String sourceCrs_string, String targetCrs_string, Point point) {
	
	CoordinateReferenceSystem sourceCrs = null;
	CoordinateReferenceSystem targetCrs = null;
	try {
	    sourceCrs = CRS.decode(sourceCrs_string); //"epsg:4326"
	} catch (NoSuchAuthorityCodeException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (FactoryException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	try {
	    targetCrs = CRS.decode(targetCrs_string);//4547
	} catch (NoSuchAuthorityCodeException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (FactoryException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	try {
	    MathTransform transform = CRS.findMathTransform(sourceCrs, targetCrs, true);
	    Coordinate coorDst = new Coordinate();
	    Coordinate coorSource = new Coordinate(point.y, point.x);
	    JTS.transform(coorSource, coorDst, transform);
	    System.out.println(coorDst.y + " " + coorDst.x);
	} catch (FactoryException e) {
	    e.printStackTrace();
	} catch (TransformException e) {
	    e.printStackTrace();
	}
	
    }
}
