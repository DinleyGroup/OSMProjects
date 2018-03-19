package Convertion;

import indoorFeatures.IIndoorFeature;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.referencing.CRS;

import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

public class CoordinateConversion
{
    
    /**
     * example
     * lng -1.94343,
     * lat 43.32341
     */
    public void coordConvert(String sourceCrs_string, String targetCrs_string, IIndoorFeature indoorFeature){
	
	CoordinateReferenceSystem sourceCrs = null;
	CoordinateReferenceSystem targetCrs = null;
	try {
	    sourceCrs = CRS.decode(sourceCrs_string); //"epsg:4326"
	} catch (NoSuchAuthorityCodeException e2) {
	    // TODO Auto-generated catch block
	    e2.printStackTrace();
	} catch (FactoryException e2) {
	    // TODO Auto-generated catch block
	    e2.printStackTrace();
	}
	
	try {
	    targetCrs = CRS.decode(targetCrs_string);//31466
	} catch (NoSuchAuthorityCodeException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	} catch (FactoryException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	
	
	double lat = (double) indoorFeature.x;
	double lng = (double) indoorFeature.y;
	
	
	
	boolean lenient = true;
	MathTransform mathTransform = null;//接口声明的引用类型变量
	try {
	    mathTransform = CRS.findMathTransform(sourceCrs, targetCrs, lenient);
	} catch (FactoryException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	//System.out.println("lat: "+lat+" "+"lng: "+lng);
	
	DirectPosition2D srcDirectPosition2D
		= new DirectPosition2D(sourceCrs, lat, lng);
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
	
	indoorFeature.y = destDirectPosition2D.x; //lng
	indoorFeature.x = destDirectPosition2D.y; //lat
	
    }
    
    public String coordConvertPrint(String sourceCrs_string, String targetCrs_string, IIndoorFeature indoorFeature, double elevation){
	
	CoordinateReferenceSystem sourceCrs = null;
	CoordinateReferenceSystem targetCrs = null;
	try {
	    sourceCrs = CRS.decode(sourceCrs_string); //"epsg:4326"
	} catch (NoSuchAuthorityCodeException e2) {
	    // TODO Auto-generated catch block
	    e2.printStackTrace();
	} catch (FactoryException e2) {
	    // TODO Auto-generated catch block
	    e2.printStackTrace();
	}
	
	try {
	    targetCrs = CRS.decode(targetCrs_string);//31466
	} catch (NoSuchAuthorityCodeException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	} catch (FactoryException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	
	
	double lat = (double) indoorFeature.x;
	double lng = (double) indoorFeature.y;
	
	
	boolean lenient = true;
	MathTransform mathTransform = null;//接口声明的引用类型变量
	try {
	    mathTransform = CRS.findMathTransform(sourceCrs, targetCrs, lenient);
	} catch (FactoryException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	//System.out.println("lat: "+lat+" "+"lng: "+lng);
	
	DirectPosition2D srcDirectPosition2D
		= new DirectPosition2D(sourceCrs, lat, lng);
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
	
	double y = destDirectPosition2D.x; //lng
	double x = destDirectPosition2D.y; //lat
	
	
	String ret;
	ret = y + " " + x + " " + elevation;
	return ret;
	
	
    }
    
    public static void main(String[] args) {
    
	/*IIndoorFeature ll2 = new IIndoorFeature(47.817955, 7.836981, 13);
	IIndoorFeature ll3 = new IIndoorFeature(47.817955, 7.836981, 10);*/
//		Point ur = new Point(3493000, 5489000, 0);
	
	CoordinateConversion cts = new CoordinateConversion();
	
	System.out.println("print ll2 " + cts.coordConvertPrint("EPSG:4326","EPSG:31466", ll2,12)) ;
	
	cts.coordConvert("EPSG:4326","EPSG:31466",ll3);
	System.out.println("ll3: "+ll3.x+" "+ll3.y+" "+ll3.z);
    }
    
}
