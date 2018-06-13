package org.citygml;

import com.vividsolutions.jts.geom.LinearRing;
import coordinate.convertion.Point;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
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
	doc = new SAXReader().read(new File("e:/EBuildingOSM2CityGML.xml"));
	Date date = new Date();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String lod4building = "";
	String bldgName = "", bldgType = "", bldgYearOfConstruction = "", xalCountryName = "China", xalLocalityName = "",
		xalThoroughfareNumber = "", xalThoroughfareName = "", xalPostalCodeNumber = "";
	double bldgMeasuredLevels = 0;
	String tagKV = null;
	String bldgID = null;
	String windowId = null;
	String doorId = null;
	String windowHeight, windowbreast, windowWidth;
	String doorHeight, doorWidth, windowType, doorType;
	String windowName = null;
	String doorName = null;
	ArrayList<Point> listPoint = new ArrayList<>();
	ArrayList<Point> listPointDoor = new ArrayList<>();
	ArrayList<Point> listPointWindow = new ArrayList<>();
	ArrayList<Point> listPointFootway = new ArrayList<>();
	ArrayList<Point> listPointCorridor = new ArrayList<>();
	ArrayList<String> listWindowHeight = new ArrayList<>();
	ArrayList<String> listWindowWidth = new ArrayList<>();
	ArrayList<String> listDoorHeight = new ArrayList<>();
	ArrayList<String> listDoorWidth = new ArrayList<>();
	ArrayList<String> lisTWindowName = new ArrayList<>();
	ArrayList<String> listWindowType = new ArrayList<>();
	ArrayList<String> listDoorType = new ArrayList<>();
	ArrayList<String> listDoorName = new ArrayList<>();
	ArrayList<String> listWindowId = new ArrayList<>();
	ArrayList<String> listDoorId = new ArrayList<>();
	ArrayList<String> corridorId = new ArrayList<>();
	ArrayList<String> footwayId = new ArrayList<>();
	xpathBuilding = "//relation/tag[@k='building']/..";
	List<Element> buidingElements = doc.selectNodes(xpathBuilding);
	Element rootElement = doc.getRootElement();
	
	lod4building += makePretty("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" " + "standalone=\"yes\"?>", 0);
	lod4building += makePretty("<!-- Written by OSM2CityGML Export Algorithm-->", 0);
	lod4building += makePretty("<!-- Developed and implemented by Deanly-->", 0);
	lod4building += makePretty("<!-- Ubiloc, China University of Geosciences (Wuhan), http://www.cug.edu.cn -->", 0);
	lod4building += makePretty("<CityModel xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.opengis.net/citygml/2.0\"\n" +
		"\txmlns:xAL=\"urn:oasis:names:tc:ciq:xsdschema:xAL:2.0\" xmlns:xlink=\"http://www.w3.org/1999/xlink\"\n" +
		"\txmlns:gml=\"http://www.opengis.net/gml\" xmlns:dem=\"http://www.opengis.net/citygml/relief/2.0\"\n" +
		"\txmlns:bldg=\"http://www.opengis.net/citygml/building/2.0\"\n" +
		"\txsi:schemaLocation=\"http://www.opengis.net/citygml/building/2.0 ../../CityGML/building.xsd http://www.opengis.net/citygml/relief/2.0 ../../CityGML/relief.xsd\"\n" +
		"\txmlns:app=\"http://www.opengis.net/citygml/appearance/2.0\"\n" +
		"\txmlns:tex=\"http://www.opengis.net/citygml/textures/2.0\">", 0);
	lod4building += makePretty("<gml:name>" + "Engineering test building" + "</gml:name>", 1);
	lod4building += makePretty("<gml:boundedBy>", 1);
	lod4building += makePretty("<gml:Envelope srsDimension=\"3\" srsName=\"urn:ogc:def:crs,crs:EPSG::4326,crs:EPSG::4326\">", 2);
	lod4building += makePretty("<gml:lowerCorner>" + "-0.012611143330844" + "-0.050087654172727" + "45" + "</gml:lowerCorner>", 3);
	lod4building += makePretty("<gml:upperCorner>" + "0.051158411625351" + "0.019154661096934" + "45" + "</gml:upperCorner>", 3);
	lod4building += makePretty("</gml:Envelope>", 2);
	lod4building += makePretty("</gml:boundedBy>", 1);
	lod4building += makePretty("<cityObjectMember>", 1);
	lod4building += makePretty("<creationDate>" + df.format(date) + "</creationDate>", 2);
	//a building
	for (Element buildingElement : buidingElements) {
	    bldgID = buildingElement.attributeValue("id");
	    List<Element> tagElements = buildingElement.elements("tag");
	    for (Element tagElement : tagElements) {
		tagKV += tagElement.attributeValue("k") + "@" + tagElement.attributeValue("v") + "; ";
	    }
	    KeyValuePair[] bkv = getKV(tagKV);
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
	    lod4building += makePretty("<gml:Polygon gml:id=\"" + bldgID + "\">", 8);
	    lod4building += makePretty("<gml:exterior>", 9);
	    lod4building += makePretty("<gml:LinearRing>", 10);
	    lod4building += makePretty("<gml:posList>" + "3378900 537100 44 3378100 537100 44 3378100 537900 44 " +
		    "3378900 537900 44 3378900 537100 44", 11);
	    lod4building += makePretty("</gml:posList>", 11);
	    lod4building += makePretty("</gml:LinearRing>", 10);
	    lod4building += makePretty("</gml:exterior>", 9);
	    lod4building += makePretty("</gml:Polygon>", 8);
	    lod4building += makePretty("</gml:surfaceMember>", 7);
	    lod4building += makePretty("</gml:MultiSurface>", 6);
	    lod4building += makePretty("</bldg:lod4MultiSurface>", 5);
	    lod4building += makePretty("</bldg:GroundSurface>", 4);
	    lod4building += makePretty("</bldg:boundedBy>", 3);
	    
	    lod4building += makePretty("<bldg:lod4Solid>", 3);
	    lod4building += makePretty("<gml:MultiSolid>", 4);
	    //a floor
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
		//floor information
		lod4building += makePretty("<gml:Solid gml:id=\"relation_" + floorID + "\">", 5);
		lod4building += makePretty("<bldg:boundedBy>", 6);
		lod4building += makePretty("<gml:name>" + floorName + "</gml:name>", 6);
		lod4building += makePretty("<gml:type>" + geometryType + "</gml:type>", 6);
		lod4building += makePretty("<gml:usage>" + levelUsage + "</gml:usage>", 6);
		lod4building += makePretty("<bldg:WallSurface>", 7);
		
		lod4building += makePretty("<bldg:lod4MultiSurface>", 8);
		lod4building += makePretty("<gml:MultiSurface>", 9);
		lod4building += makePretty("<gml:surfaceMember>", 10);
		//way and node
		List memberFloorList = floorElement.elements("member");
		for (Object obj : memberFloorList) {
		    Element memberFloorElement = (Element) obj;
		    if (memberFloorElement.attributeValue("type").equals("way")) {
			String wayID = memberFloorElement.attributeValue("ref");
			String xpathWay = "//way[@id=" + wayID + "]/tag";
			List elementWay = doc.selectNodes(xpathWay);
			for (Object obj1 : elementWay) {
			    Element wallTagElement = (Element) obj1;
			    //building room
			    if (wallTagElement.attributeValue("v").equals("room")) {
				Element wallElement = wallTagElement.getParent();
				String wallId = wallElement.attributeValue("id");
				List wallList = wallElement.elements("nd");
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
				Point[] points = null;
				points = listPoint.toArray(new Point[listPoint.size()]);
				listPoint.clear();
				for (int i = 0; i < points.length - 1; i++) {
				    Point pt1 = new Point(points[i].x, points[i].y, points[i].z);
				    Point pt2 = new Point(points[i + 1].x, points[i + 1].y, points[i + 1].z);
				    Point pt3 = new Point(points[i + 1].x, points[i + 1].y, points[i + 1].z + height);
				    Point pt4 = new Point(points[i].x, points[i].y, points[i].z + height);
				    //使构建polygon的5个点从小到大排列
				    if ((pt1.x) * (pt1.x) + (pt1.y) * (pt1.y) > (pt2.y) * (pt2.y) + (pt2.x) * (pt2.x)) {
					Point point12 = pt1;
					pt1 = pt2;
					pt2 = point12;
					Point point34 = pt3;
					pt3 = pt4;
					pt4 = point34;
				    }
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
				
				//每间房子的地面
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
			    }
			    //building footway
			    if (wallTagElement.attributeValue("v").equals("footway")) {
				Element wallElement = wallTagElement.getParent();
				footwayId.add(wallElement.attributeValue("id"));
				List wallList = wallElement.elements("nd");
				for (Object obj2 : wallList) {
				    Element wallNodeElement = (Element) obj2;
				    String nodeId = wallNodeElement.attributeValue("ref");
				    String xpat = "//node[@id=" + nodeId + "]";
				    Element nodeElement = (Element) doc.selectSingleNode(xpat);
				    double lat = Double.parseDouble(nodeElement.attributeValue("lat"));
				    double lon = Double.parseDouble(nodeElement.attributeValue("lon"));
				    Point point = new Point(lat, lon, 45 + height * (level - 1));
				    listPointFootway.add(point);
				}
				Point[] points1 = null;
				points1 = listPointFootway.toArray(new Point[listPointFootway.size()]);
				listPointFootway.clear();
				lod4building += makePretty("<gml:lineStringMember>", 11);
				lod4building += makePretty("<gml:name>Footway</gml:name>", 11);
				lod4building += makePretty("<gml:type gml:type=\"footway\"></gml:type>", 11);
				lod4building += makePretty("<gml:exterior>", 12);//wall
				lod4building += makePretty("<gml:lineString gml:id=\"" + "45678" + "\" >", 13);
				lod4building += makePretty("<gml:posList rsDimension=\"3\">", 14);
				for (int i = 0; i < points1.length; i++) {
				    lod4building += makePretty(points1[i].x + " " + points1[i].y + " " + points1[i].z, 15);
				}
				lod4building += makePretty("</gml:posList>", 14);
				lod4building += makePretty("</gml:lineString>", 13);
				lod4building += makePretty("</gml:exterior>", 12);
				lod4building += makePretty("</gml:lineStringMember>", 11);
			    }
			   //building cooridor
			     if (wallTagElement.attributeValue("v").equals("corridor")) {
				Element wallElement = wallTagElement.getParent();
				corridorId.add(wallElement.attributeValue("id"));
				List wallList = wallElement.elements("nd");
				for (Object obj2 : wallList) {
				    Element wallNodeElement = (Element) obj2;
				    String nodeId = wallNodeElement.attributeValue("ref");
				    String xpat = "//node[@id=" + nodeId + "]";
				    Element nodeElement = (Element) doc.selectSingleNode(xpat);
				    double lat = Double.parseDouble(nodeElement.attributeValue("lat"));
				    double lon = Double.parseDouble(nodeElement.attributeValue("lon"));
				    Point point = new Point(lat, lon, 45 + height * (level - 1));
				    listPointCorridor.add(point);
				}
			 
				Point[] points2 = null;
				points2 = listPointCorridor.toArray(new Point[listPointCorridor.size()]);
				listPointCorridor.clear();
				lod4building += makePretty("<gml:Polygon>", 11);
				lod4building += makePretty("<gml:name>Corridor</gml:name>", 11);
				lod4building += makePretty("<gml:type gml:type=\"corridor\"></gml:type>", 11);
				lod4building += makePretty("<gml:exterior>", 12);//wall
				lod4building += makePretty("<gml:LinearRing gml:id=\"" + corridorId + "\" >", 13);
				lod4building += makePretty("<gml:posList rsDimension=\"3\">", 14);
				for (int j = 0; j < points2.length; j++) {
				    lod4building += makePretty(points2[j].x + " " + points2[j].y + " " + points2[j].z, 15);
				}
				lod4building += makePretty("</gml:posList>", 14);
				lod4building += makePretty("</gml:LinearRing>", 13);
				lod4building += makePretty("</gml:exterior>", 12);
				lod4building += makePretty("</gml:Polygon>", 11);
			    }
			    
			}
		    }
		    //windows and door
		    else if (memberFloorElement.attributeValue("type").equals("node")) {
			String nodeID = memberFloorElement.attributeValue("ref");
			String xpathNode = "osm/node[@id=" + nodeID + "]/tag";
			//System.out.println(nodeID+"666666666");
			List listNodeTag = doc.selectNodes(xpathNode);
			for (Object obj3 : listNodeTag) {
			    Element elementNodeTag = (Element) obj3;
			    if (elementNodeTag.attributeValue("k").equals("window")) {
				Element elementWindowNode = elementNodeTag.getParent();
				for (Object ob : elementWindowNode.elements("tag")) {
				    Element elementNodeW = (Element) ob;
				    if (elementNodeW.attributeValue("k").equals("height")) {
					windowHeight = elementNodeTag.attributeValue("v");
					listWindowHeight.add(windowHeight);
				    }
				    
				    if (elementNodeW.attributeValue("k").equals("width")) {
					windowWidth = elementNodeTag.attributeValue("v");
					listWindowWidth.add(windowWidth);
				    }
				    
				    if (elementNodeW.attributeValue("v").equals("yes")) {
					windowType = elementNodeTag.attributeValue("k");
					listWindowType.add(windowType);
				    }
				    
				    if (elementNodeW.attributeValue("k").equals("name")) {
					windowName = elementNodeW.attributeValue("v");
					lisTWindowName.add(windowName);
				    }
				}
				windowId = elementWindowNode.attributeValue("id");
				listWindowId.add(windowId);
				double lat = Double.parseDouble(elementWindowNode.attributeValue("lat"));
				double lon = Double.parseDouble(elementWindowNode.attributeValue("lon"));
				Point point = new Point(lat, lon, 45 + height * (level - 1) + 1);
				listPointWindow.add(point);
			    }
			    
			    if (elementNodeTag.attributeValue("k").equals("door")) {
				Element elementDoorNode = elementNodeTag.getParent();
				for (Object ob : elementDoorNode.elements("tag")) {
				    Element elementNodeD = (Element) ob;
				    if (elementNodeD.attributeValue("k").equals("height")) {
					doorHeight = elementNodeD.attributeValue("v");
					listDoorHeight.add(doorHeight);
				    }
				    
				    if (elementNodeD.attributeValue("k").equals("width")) {
					doorWidth = elementNodeD.attributeValue("v");
					listDoorWidth.add(doorWidth);
				    }
				    
				    if (elementNodeD.attributeValue("v").equals("yes")) {
					doorType = elementNodeTag.attributeValue("k");
					listDoorType.add(doorType);
				    }
				    
				    if (elementNodeD.attributeValue("k").equals("name")) {
					doorName = elementNodeD.attributeValue("v");
					listDoorName.add(doorName);
				    }
				}
				doorId = elementDoorNode.attributeValue("id");
				listDoorId.add(doorId);
				double lat = Double.parseDouble(elementDoorNode.attributeValue("lat"));
				double lon = Double.parseDouble(elementDoorNode.attributeValue("lon"));
				Point point = new Point(lat, lon, 45 + height * (level - 1));
				listPointDoor.add(point);
			    }
			}
		    }
		}
		
		lod4building += makePretty("</gml:surfaceMember>", 10);
		lod4building += makePretty("</gml:MultiSurface>", 9);
		lod4building += makePretty("</bldg:lod4MultiSurface>", 8);
		
		// Building Window
		Point[] pointsWindows = null;
		pointsWindows = listPointWindow.toArray(new Point[listPointWindow.size()]);
		listPointWindow.clear();
		for (int i = 0; i < pointsWindows.length - 1; i++) {
		    Point ptw1 = null, ptw2 = null, ptw3 = null, ptw4 = null;
		    if (Math.abs(pointsWindows[i].x - pointsWindows[i + 1].x) < 1) {      //lat
			ptw1 = new Point(pointsWindows[i].x, pointsWindows[i].y - 1, pointsWindows[i].z);
			ptw2 = new Point(pointsWindows[i].x, pointsWindows[i].y + 1, pointsWindows[i].z);
			ptw3 = new Point(pointsWindows[i].x, pointsWindows[i].y + 1, pointsWindows[i].z + 2);
			ptw4 = new Point(pointsWindows[i].x, pointsWindows[i].y - 1, pointsWindows[i].z + 2);
		    } else if (Math.abs(pointsWindows[i].y - pointsWindows[i + 1].y) < 1) {     //lon
			ptw1 = new Point(pointsWindows[i].x + 1, pointsWindows[i].y, pointsWindows[i].z);
			ptw2 = new Point(pointsWindows[i].x - 1, pointsWindows[i].y, pointsWindows[i].z);
			ptw3 = new Point(pointsWindows[i].x - 1, pointsWindows[i].y, pointsWindows[i].z + 2);
			ptw4 = new Point(pointsWindows[i].x + 1, pointsWindows[i].y, pointsWindows[i].z + 2);
		    } else {
			ptw1 = new Point(pointsWindows[i].x + 1, pointsWindows[i].y, pointsWindows[i].z);
			ptw2 = new Point(pointsWindows[i].x - 1, pointsWindows[i].y, pointsWindows[i].z);
			ptw3 = new Point(pointsWindows[i].x - 1, pointsWindows[i].y, pointsWindows[i].z + 2);
			ptw4 = new Point(pointsWindows[i].x + 1, pointsWindows[i].y, pointsWindows[i].z + 2);
		    }
		    
		    lod4building += makePretty("<bldg:opening>", 8);
		    lod4building += makePretty("<bldg:Window gml:id=\"" + listWindowId.get(i) + "\">", 9);
		    lod4building += makePretty("<gml:name>" + lisTWindowName.get(i) + "</gml:name>", 10);
		    lod4building += makePretty("<gml:type gml:type=\"" + listWindowType.get(i) + "\"></gml:type>", 10);
		    lod4building += makePretty("<bldg:lod4MultiSurface>", 10);
		    lod4building += makePretty("<gml:MultiSurface>", 11);
		    lod4building += makePretty("<gml:surfaceMember>", 12);
		    lod4building += makePretty("<gml:Polygon gml:id=\"" + listWindowId.get(i) + "\">", 13);
		    lod4building += makePretty("<gml:exterior>", 14);
		    lod4building += makePretty("<gml:LinearRing gml:id=\"" + listWindowId.get(i) + "\">", 15);
		    lod4building += makePretty("<gml:posList rsDimension=\"3\">", 16);
		    lod4building += makePretty(ptw1.x + " " + ptw1.y + " " + ptw1.z, 17);
		    lod4building += makePretty(ptw2.x + " " + ptw2.y + " " + ptw2.z, 17);
		    lod4building += makePretty(ptw3.x + " " + ptw3.y + " " + ptw3.z, 17);
		    lod4building += makePretty(ptw4.x + " " + ptw4.y + " " + ptw4.z, 17);
		    lod4building += makePretty(ptw1.x + " " + ptw1.y + " " + ptw1.z, 17);
		    lod4building += makePretty(" </gml:posList>", 16);
		    lod4building += makePretty("</gml:LinearRing>", 15);
		    lod4building += makePretty("</gml:exterior>", 14);
		    lod4building += makePretty("</gml:Polygon>", 13);
		    lod4building += makePretty("</gml:surfaceMember>", 12);
		    lod4building += makePretty("</gml:MultiSurface>", 11);
		    lod4building += makePretty("</bldg:lod4MultiSurface>", 10);
		    lod4building += makePretty("</bldg:Window>", 9);
		    lod4building += makePretty("</bldg:opening>", 8);
		}
		//building door
		Point[] pointsDoor = null;
		pointsDoor = listPointDoor.toArray(new Point[listPointDoor.size()]);
		listPointDoor.clear();
		for (int j = 0; j < pointsDoor.length - 1; j++) {
		    Point ptd1 = null, ptd2 = null, ptd3 = null, ptd4 = null;
		    if (Math.abs(pointsDoor[j].x - pointsDoor[j + 1].x) < 0.5) {      //lat纬度
			ptd1 = new Point(pointsDoor[j].x, pointsDoor[j].y - 0.5, pointsDoor[j].z);
			ptd2 = new Point(pointsDoor[j].x, pointsDoor[j].y + 0.5, pointsDoor[j].z);
			ptd3 = new Point(pointsDoor[j].x, pointsDoor[j].y + 0.5, pointsDoor[j].z + 2);
			ptd4 = new Point(pointsDoor[j].x, pointsDoor[j].y - 0.5, pointsDoor[j].z + 2);
		    } else if (Math.abs(pointsDoor[j].y - pointsDoor[j + 1].y) < 0.5) {     //lon经度
			ptd1 = new Point(pointsDoor[j].x + 0.5, pointsDoor[j].y, pointsDoor[j].z);
			ptd2 = new Point(pointsDoor[j].x - 0.5, pointsDoor[j].y, pointsDoor[j].z);
			ptd3 = new Point(pointsDoor[j].x - 0.5, pointsDoor[j].y, pointsDoor[j].z + 2);
			ptd4 = new Point(pointsDoor[j].x + 0.5, pointsDoor[j].y, pointsDoor[j].z + 2);
		    } else {
			ptd1 = new Point(pointsDoor[j].x + 0.5, pointsDoor[j].y, pointsDoor[j].z);
			ptd2 = new Point(pointsDoor[j].x - 0.5, pointsDoor[j].y, pointsDoor[j].z);
			ptd3 = new Point(pointsDoor[j].x - 0.5, pointsDoor[j].y, pointsDoor[j].z + 2);
			ptd4 = new Point(pointsDoor[j].x + 0.5, pointsDoor[j].y, pointsDoor[j].z + 2);
		    }
		    
		    lod4building += makePretty("<bldg:opening>", 8);
		    lod4building += makePretty("<bldg:Door gml:id=\"" + listDoorId.get(j) + "\">", 9);
		    lod4building += makePretty("<gml:name>" + listDoorName.get(j) + "</gml:name>", 10);
		    lod4building += makePretty("<gml:type gml:type=\"" + listDoorType.get(j) + "\"></gml:type>", 10);
		    lod4building += makePretty("<bldg:lod4MultiSurface>", 10);
		    lod4building += makePretty("<gml:MultiSurface>", 11);
		    lod4building += makePretty("<gml:surfaceMember>", 12);
		    lod4building += makePretty("<gml:Polygon gml:id=\"" + listDoorId.get(j) + "\">", 13);
		    lod4building += makePretty("<gml:exterior>", 14);
		    lod4building += makePretty("<gml:LinearRing gml:id=\"" + listDoorId.get(j) + "\">", 15);
		    lod4building += makePretty("<gml:posList rsDimension=\"3\">", 16);
		    lod4building += makePretty(ptd1.x + " " + ptd1.y + " " + ptd1.z, 17);
		    lod4building += makePretty(ptd2.x + " " + ptd2.y + " " + ptd2.z, 17);
		    lod4building += makePretty(ptd3.x + " " + ptd3.y + " " + ptd3.z, 17);
		    lod4building += makePretty(ptd4.x + " " + ptd4.y + " " + ptd4.z, 17);
		    lod4building += makePretty(ptd1.x + " " + ptd1.y + " " + ptd1.z, 17);
		    lod4building += makePretty(" </gml:posList>", 16);
		    lod4building += makePretty("</gml:LinearRing>", 15);
		    lod4building += makePretty("</gml:exterior>", 14);
		    lod4building += makePretty("</gml:Polygon>", 13);
		    lod4building += makePretty("</gml:surfaceMember>", 12);
		    lod4building += makePretty("</gml:MultiSurface>", 11);
		    lod4building += makePretty("</bldg:lod4MultiSurface>", 10);
		    lod4building += makePretty("</bldg:Door>", 9);
		    lod4building += makePretty("</bldg:opening>", 8);
		}
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
