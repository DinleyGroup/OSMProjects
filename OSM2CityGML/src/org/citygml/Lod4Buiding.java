package org.citygml;

import coordinate.convertion.Point;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Lod4Buiding {
    
    public static void main(String[] args) throws DocumentException {
	Lod4Buiding lod4Buiding = new Lod4Buiding();
	lod4Buiding.osm2CityGML();
	System.out.println("Make CityGML completed!!!");
    }
    
    public void osm2CityGML() throws DocumentException {
	String xpathBuilding = null;
	Document doc = null;
	doc = new SAXReader().read(new File("e:/EBuilding2.xml"));
	Date date = new Date();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String lod4building = "", lod4appearance = "", lod4rooms = "";
	String bldgName = "", bldgType = "", bldgYearOfConstruction = "", xalCountryName = "China", xalLocalityName = "",
		xalThoroughfareNumber = "", xalThoroughfareName = "", xalPostalCodeNumber = "";
	long bldgClass, bldgFunction, bldgUsage, bldgRoofType, bldgStoreysAboveGround, bldgStoreysBelowGround;
	double bldgMeasuredLevels = 0;
	String tagKV = null;
	String bldgID = null;
	ArrayList<Point> listPoint = new ArrayList<>();
	xpathBuilding = "//relation/tag[@k='building']/..";
	List<Element> buidingElements = doc.selectNodes(xpathBuilding);
	Element rootElement = doc.getRootElement();
	//region Description CityModel
	lod4building += makePretty("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" " + "standalone=\"yes\"?>", 0);
	lod4building += makePretty("<!-- Written by OSM2CityGML Export Algorithm " + "- Version 0.4b -->", 0);
	lod4building += makePretty("<!-- Developed and implemented by Marcus Goetz, " + "m.goetz@uni-heidelberg.de -->", 0);
	lod4building += makePretty("<!-- GIScience Research Group, Department of Geography, " + "University of Heidelberg, http://giscience.uni-hd.de -->", 0);
	lod4building += makePretty("<CityModel xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.opengis.net/citygml/2.0\"\n" +
		"\txmlns:xAL=\"urn:oasis:names:tc:ciq:xsdschema:xAL:2.0\" xmlns:xlink=\"http://www.w3.org/1999/xlink\"\n" +
		"\txmlns:gml=\"http://www.opengis.net/gml\" xmlns:dem=\"http://www.opengis.net/citygml/relief/2.0\"\n" +
		"\txmlns:bldg=\"http://www.opengis.net/citygml/building/2.0\"\n" +
		"\txsi:schemaLocation=\"http://www.opengis.net/citygml/building/2.0 ../../CityGML/building.xsd http://www.opengis.net/citygml/relief/2.0 ../../CityGML/relief.xsd\">", 0);
	lod4building += makePretty("<gml:name>" + "Engineering test building" + "</gml:name>", 1);
	lod4building += makePretty("<gml:boundedBy>", 1);
	lod4building += makePretty("<gml:Envelope srsDimension=\"3\" srsName=\"urn:ogc:def:crs,crs:EPSG::4326,crs:EPSG::4326\">", 2);
	lod4building += makePretty("<gml:lowerCorner>" + "-0.012611143330844" + "-0.050087654172727" + "45" + "</gml:lowerCorner>", 3);
	lod4building += makePretty("<gml:upperCorner>" + "0.051158411625351" + "0.019154661096934" + "45" + "</gml:upperCorner>", 3);
	lod4building += makePretty("</gml:Envelope>", 2);
	lod4building += makePretty("</gml:boundedBy>", 1);
	//endregion
	lod4building += makePretty("<cityObjectMember>", 1);
	lod4building += makePretty("<creationDate>" + df.format(date) + "</creationDate>", 2);
	
	
	for (Element buildingElement : buidingElements) {
	    bldgID = buildingElement.attributeValue("id");
	    List<Element> tagElements = buildingElement.elements("tag");
	    for (Element tagElement : tagElements) {
		tagKV += tagElement.attributeValue("k") + "@" + tagElement.attributeValue("v") + "; ";
	    }
	    KeyValuePair[] bkv = getKV(tagKV);
	    //region Description bkv
	    if (isKeyDefined(bkv, "addr:city")) {
		xalLocalityName = getValue(bkv, "addr:city");
	    }
	    if (isKeyDefined(bkv, "addr:country")) {
		xalCountryName = getValue(bkv, "addr:country");
	    }
	    if (isKeyDefined(bkv, "addr:housenumber")) {
		xalThoroughfareNumber = getValue(bkv, "addr:housenumber");
	    }
	    if (isKeyDefined(bkv, "addr:street")) {
		xalThoroughfareName = getValue(bkv, "addr:street");
	    }
	    if (isKeyDefined(bkv, "addr:postcode")) {
		xalPostalCodeNumber = getValue(bkv, "addr:postcode");
	    }
	    if (isKeyDefined(bkv, "building:buildyear")) {
		bldgYearOfConstruction = getValue(bkv, "building:buildyear");
	    }
	    if (isKeyDefined(bkv, "name")) {
		bldgName = getValue(bkv, "name");
	    }
	    if (isKeyDefined(bkv, "type")) {
		bldgType = getValue(bkv, "type");
	    }
	    if (isKeyDefined(bkv, "height")) {
		bldgMeasuredLevels = Double.parseDouble(getValue(bkv, "height"));
	    } else if (isKeyDefined(bkv, "building:levels")) {
		bldgMeasuredLevels = Double.parseDouble(getValue(bkv, "building:levels"));
	    }
	    //endregion
	    
	    //region Description Building
	    lod4building += makePretty("<bldg:Building gml:id=\"ID_" + bldgID + "\">", 2);
	    lod4building += makePretty("<gml:name>" + bldgName + "</gml:name>", 3);
	    lod4building += makePretty("<bldg:function codeSpace=\"http://www.sig3d." + "org/codelists/standard/building/2.0/_AbstractBuilding_function.xml\">" + "1000" + "</bldg:function>", 3);
	    lod4building += makePretty("<bldg:yearOfConstruction>" + bldgYearOfConstruction + "</bldg:yearOfConstruction>", 3);
	    lod4building += makePretty("<bldg:roofType codeSpace=\"http://www.sig3d.org/codelists" + "/standard/building/2.0/_AbstractBuilding_roofType.xml\">" + "1030" + "</bldg:roofType>", 3);
	    lod4building += makePretty("<bldg:MeasuredHeight>" + bldgMeasuredLevels + "</bldg:MeasuredHeight>", 3);
	    lod4building += makePretty("<bldg:storeysAboveGround>" + "1" + "</bldg:storeysAboveGround>", 3);
	    lod4building += makePretty("<bldg:storeyHeightsAboveGround uom=\"#m\">" + "3.0" + "</bldg:storeyHeightsAboveGround>", 3);
	    
	    lod4building += makePretty("<bldg:address>", 3);
	    lod4building += makePretty("<Address>", 4);
	    lod4building += makePretty("<xalAddress>", 5);
	    lod4building += makePretty("<xAL:AddressDetails>", 6);
	    lod4building += makePretty("<xAL:Country>", 7);
	    lod4building += makePretty("<xAL:CountryName>" + xalCountryName + "</xAL:CountryName>", 8);
	    lod4building += makePretty("<xAL:Locality Type=\"Town\">", 8);
	    lod4building += makePretty("<xAL:LocalityName>" + xalLocalityName + "</xAL:LocalityName>", 9);
	    lod4building += makePretty("<xAL:Thoroughfare Type=\"Street\">", 9);
	    lod4building += makePretty("<xAL:ThoroughfareNumber>" + xalThoroughfareNumber + "</xAL:ThoroughfareNumber>", 10);
	    lod4building += makePretty("<xAL:ThoroughfareName>" + xalThoroughfareName + "</xAL:ThoroughfareName>", 10);
	    lod4building += makePretty("</xAL:Thoroughfare>", 9);
	    lod4building += makePretty("<xAL:PostalCode>", 9);
	    lod4building += makePretty("<xAL:PostalCodeNumber>" + xalPostalCodeNumber + "</xAL:PostalCodeNumber>", 10);
	    lod4building += makePretty("</xAL:PostalCode>", 9);
	    lod4building += makePretty("</xAL:Locality>", 8);
	    lod4building += makePretty("</xAL:Country>", 7);
	    lod4building += makePretty("</xAL:AddressDetails>", 6);
	    lod4building += makePretty("</xalAddress>", 5);
	    lod4building += makePretty("</Address>", 4);
	    lod4building += makePretty("</bldg:address>", 3);
	    
	    lod4building += makePretty("<bldg:boundedBy>", 3);
	    lod4building += makePretty("<bldg:GroundSurface>", 4);
	    lod4building += makePretty("<gml:name>" + "GroundSurface" + "</gml:name>", 5);
	    lod4building += makePretty("<bldg:lod4MultiSurface>", 5);
	    lod4building += makePretty("<gml:MultiSurface>", 6);
	    lod4building += makePretty("<gml:surfaceMember>", 7);
	    lod4building += makePretty("<gml:Polygon gml:id=\"GML_d3981803-d4b0-4b5b-969c-53f657594757\">", 8);
	    lod4building += makePretty("<gml:exterior>", 9);
	    lod4building += makePretty("<gml:LinearRing>", 10);
	    lod4building += makePretty("<gml:posList>" + "3378900 537100 44 3378100 537100 44 3378100 537900 44 3378900 537900 44 3378900 537100 44", 11);
	    lod4building += makePretty("</gml:posList>", 11);
	    lod4building += makePretty("</gml:LinearRing>", 10);
	    lod4building += makePretty("</gml:exterior>", 9);
	    lod4building += makePretty("</gml:Polygon>", 8);
	    lod4building += makePretty("</gml:surfaceMember>", 7);
	    lod4building += makePretty("</gml:MultiSurface>", 6);
	    lod4building += makePretty("</bldg:lod4MultiSurface>", 5);
	    lod4building += makePretty("</bldg:GroundSurface>", 4);
	    lod4building += makePretty("</bldg:boundedBy>", 3);
	    //endregion
	    lod4building += makePretty("<bldg:lod4Solid>", 3);
	    lod4building += makePretty("<gml:MultiSolid>", 4);
	    List<Element> memberElements = buildingElement.elements("member");
	    for (Element memberElement : memberElements) {
		String floorID = memberElement.attributeValue("ref");
		String floorName = "", levelUsage = "", geometryType = "";
		int level = 0;
		double height = 0;
		String xpathFloor = "//relation[@id=" + floorID + "]";
		//String xpathWall="//relation[@id=" + floorID + "]/member[@type='wall']";
		Element floorElement = (Element) doc.selectSingleNode(xpathFloor);
		
		List floorList = floorElement.elements("tag");
		for (Object obj : floorList) {
		    Element element = (Element) obj;
		    if (element.attributeValue("k").equals("name")) {
			floorName = element.attributeValue("v");
		    }
		    if (element.attributeValue("k").equals("type")) {
			geometryType = element.attributeValue("v");
		    }
		    if (element.attributeValue("k").equals("level:usage")) {
			levelUsage = element.attributeValue("v");
		    }
		    if (element.attributeValue("k").equals("level")) {
			level = Integer.parseInt(element.attributeValue("v"));
		    }
		    if (element.attributeValue("k").equals("height")) {
			height = Double.parseDouble(element.attributeValue("v"));
		    }
		}
		
		
		lod4building += makePretty("<gml:Solid gml:id=\"relation_" + floorID + "\">", 5);
		lod4building += makePretty("<bldg:boundedBy>", 6);
		lod4building += makePretty("<gml:name>" + floorName + "</gml:name>", 6);
		lod4building += makePretty("<gml:type>" + geometryType + "</gml:type>", 6);
		lod4building += makePretty("<gml:usage>" + levelUsage + "</gml:usage>", 6);
		lod4building += makePretty("<bldg:WallSurface>", 7);
		lod4building += makePretty("<bldg:lod4MultiSurface>", 8);
		lod4building += makePretty("<gml:MultiSurface>", 9);
		lod4building += makePretty("<gml:surfaceMember>", 10);
		
		
		//Elements wallElements = (Elements) doc.selectNodes(xpathWall);
		List memberWallList = floorElement.elements("member");
		for (Object obj : memberWallList) {
		    Element memberWallElement = (Element) obj;
		    if (memberWallElement.attributeValue("type").equals("way")) {
			String wayID = memberWallElement.attributeValue("ref");
			String xpathWay = "//way[@id=" + wayID + "]/tag";
			List elementWay = doc.selectNodes(xpathWay);
			for (Object obj1 : elementWay) {
			    Element wallTagElement = (Element) obj1;
			    //region Description room
			    if (wallTagElement.attributeValue("v").equals("room")) {
				Element wallElement = wallTagElement.getParent();
				String wallId = wallElement.attributeValue("id");
				
				
				//lod4building += makePretty("<gml:posLists rsDimension=\"3\">", 14);
				
				List wallList = wallElement.elements("nd");
				//region Description
				for (Object obj2 : wallList) {
				    Element wallNodeElement = (Element) obj2;
				    String nodeId = wallNodeElement.attributeValue("ref");
				    String xpat = "//node[@id=" + nodeId + "]";
				    Element nodeElement = (Element) doc.selectSingleNode(xpat);
				    double lat = Double.parseDouble(nodeElement.attributeValue("lat"));
				    double lon = Double.parseDouble(nodeElement.attributeValue("lon"));
				    Point point = new Point(lat, lon, 45 + height * (level - 1));
				    listPoint.add(point);
				}
				//endregion
				
				Point[] points = null;
				points = listPoint.toArray(new Point[listPoint.size()]);
				listPoint.clear();
				//region Description
				for (int i = 0; i < points.length - 1; i++) {
				    Point pt1 = new Point(points[i].x, points[i].y, points[i].z);
				    Point pt2 = new Point(points[i + 1].x, points[i + 1].y, points[i + 1].z);
				    Point pt3 = new Point(points[i + 1].x, points[i + 1].y, points[i + 1].z + height);
				    Point pt4 = new Point(points[i].x, points[i].y, points[i].z + height);
				    lod4building += makePretty("<gml:Polygon>", 11);
				    lod4building += makePretty("<gml:exterior>", 12);//wall
				    lod4building += makePretty("<gml:LinearRing gml:id=\"" + wallId + "\" >", 13);
				    lod4building += makePretty("<gml:posList rsDimension=\"3\">", 14);
				    lod4building += makePretty(pt1.x + " " + pt1.y + " " + pt1.z, 15);
				    lod4building += makePretty(pt2.x + " " + pt2.y + " " + pt2.z, 15);
				    lod4building += makePretty(pt3.x + " " + pt3.y + " " + pt3.z, 15);
				    lod4building += makePretty(pt4.x + " " + pt4.y + " " + pt4.z, 15);
				    lod4building += makePretty(pt1.x + " " + pt1.y + " " + pt1.z, 15);
				    lod4building += makePretty("</gml:posList>", 14);
				    lod4building += makePretty("</gml:LinearRing>", 13);
				    lod4building += makePretty("</gml:exterior>", 12);
				    lod4building += makePretty("</gml:Polygon>", 11);
				}
				
				lod4building += makePretty("<gml:Polygon>", 11);
				lod4building += makePretty("<gml:exterior>", 12);//wall
				lod4building += makePretty("<gml:LinearRing gml:id=\"" + wallId + "\" >", 13);
				lod4building += makePretty("<gml:posList rsDimension=\"3\">", 14);
				for (int i = 0; i < points.length; i++) {
				    lod4building += makePretty(points[i].x + " " + points[i].y + " " + points[i].z, 15);
				}
				lod4building += makePretty("</gml:posList>", 14);
				lod4building += makePretty("</gml:LinearRing>", 13);
				lod4building += makePretty("</gml:exterior>", 12);
				lod4building += makePretty("</gml:Polygon>", 11);
				//endregion
			    }
			    //endregion
			}
		    }
///*		    if (memberWallElement.attributeValue("type").equals("node")) {
//			String nodeID = memberWallElement.attributeValue("ref");
//		lod4building += makePretty("<gml:interior>", 12);//windows
//		lod4building += makePretty("<gml:LinearRing>", 13);
//		lod4building += makePretty("<gml:posList rsDimension=\"3\">", 14);
//		/*lod4building += makePretty(pt1.y + " " + pt1.x + " " + pt1.z, 12);
//		lod4building += makePretty(pt1.y + " " + pt1.x + " " + pt1.z, 12);
//		lod4building += makePretty(pt2.y + " " + pt2.x + " " + pt2.z, 12);
//		lod4building += makePretty(pt2.y + " " + pt2.x + " " + pt2.z, 12);
//		lod4building += makePretty(pt1.y + " " + pt1.x + " " + pt1.z, 12);*/
//		    lod4building += makePretty("</gml:posList>", 14);
//		    lod4building += makePretty("</gml:LinearRing>", 13);
//		    lod4building += makePretty("</gml:interior>", 12);
//
//		    lod4building += makePretty("<gml:interior>", 12);//wall
//		    lod4building += makePretty("<gml:LinearRing>", 13);
//		    lod4building += makePretty("<gml:posList rsDimension=\"3\">", 14);
//		/*lod4building += makePretty(pt1.y + " " + pt1.x + " " + pt1.z, 12);
//		lod4building += makePretty(pt1.y + " " + pt1.x + " " + pt1.z, 12);
//		lod4building += makePretty(pt2.y + " " + pt2.x + " " + pt2.z, 12);
//		lod4building += makePretty(pt2.y + " " + pt2.x + " " + pt2.z, 12);
//		lod4building += makePretty(pt1.y + " " + pt1.x + " " + pt1.z, 12);*/
//		    lod4building += makePretty("</gml:posList>", 14);
//		    lod4building += makePretty("</gml:LinearRing>", 13);
//		    lod4building += makePretty("</gml:interior>", 12); }*/
		}
		
		lod4building += makePretty("</gml:surfaceMember>", 10);
		lod4building += makePretty("</gml:MultiSurface>", 9);
		lod4building += makePretty("</bldg:lod4MultiSurface>", 8);
		lod4building += makePretty("</bldg:WallSurface>", 7);
		lod4building += makePretty("</bldg:boundedBy>", 6);
		lod4building += makePretty("</gml:Solid>", 5);
	    }
	    lod4building += makePretty("</gml:MultiSolid>", 4);
	    lod4building += makePretty("</bldg:lod4Solid>", 3);
	    lod4building += makePretty("</bldg:Building>", 2);
	    lod4building += makePretty("</cityObjectMember>", 1);
	    lod4building += makePretty("</CityModel>", 0);
	}
	makeGmlFile(lod4building);
    }
    
    public void makeGmlFile(String lod4building) {
	File out = new File("e:/Lod4Building.xml");
	try {
	    FileOutputStream output = new FileOutputStream(out);
	    output.write(lod4building.getBytes());
	    output.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    public void Level(Document doc) {
	String xpath = "/osm/relation/tag[@k='level']";
	List<Element> Leveltags = doc.selectNodes(xpath);
	Iterator<Element> it = Leveltags.iterator();
	while (it.hasNext()) {
	    Element elem1 = it.next().getParent();
	    Element elemLevel = (Element) elem1.selectSingleNode("child::node()[@k='level']");
	    int buildingLevel = Integer.parseInt(elemLevel.attributeValue("v"));
	    //double buildingLevel=Double.parseDouble(it.next().attributeValue("v"));
	    Element elemHeight = (Element) elem1.selectSingleNode("/osm/relation/tag[@k='height']");
	    double levelHeight = Double.parseDouble(elemHeight.attributeValue("v"));
	    System.out.println(elem1.attributeValue("id") + "  " + buildingLevel + "  " + levelHeight);
	}
    }
    
    public void Room(Element element) {
	
	
    }
    
    public static String makePretty(String text, int numTabs) {
	String pretty = "";
	for (int i = 0; i < numTabs; i++) {
	    pretty += "\t";
	}
	pretty += text + "\n";
	return pretty;
    }
    
    public static KeyValuePair[] getKV(String str) {
	String[] str_parts = str.split("; ");
	KeyValuePair[] res = new KeyValuePair[str_parts.length];
	for (int i = 0; i < str_parts.length; i++) {
	    String[] kvp = str_parts[i].split("@");
	    for (int k = 0; k < kvp.length; k++) {
		kvp[k] = kvp[k].replace("\"", "");
	    }
	    res[i] = new KeyValuePair(kvp[0], kvp[1]);
	    
	}
	return res;
    }
    
    public static boolean isKeyDefined(KeyValuePair[] kv, String key) {
	for (int i = 0; i < kv.length; i++) {
	    if (kv[i].getKey().equals(key)) {
		return true;
	    }
	}
	return false;
    }
    
    public static String getValue(KeyValuePair[] kv, String key) {
	for (int i = 0; i < kv.length; i++) {
	    if (kv[i].getKey().equals(key)) {
		return kv[i].getValue();
	    }
	}
	return null;
    }
}
