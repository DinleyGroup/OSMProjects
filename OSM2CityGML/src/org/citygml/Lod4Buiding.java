package org.citygml;

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

import org.jaxen.*;

public class Lod4Buiding {
    
    public static void main(String[] args) {
	Lod4Buiding lod4Buiding = new Lod4Buiding();
	lod4Buiding.osm2CityGML();
	System.out.println("Hello CityGML!!!");
    }
    
    public void osm2CityGML() {
	String xpath = "";
	Document doc = null;
	try {
	    doc = new SAXReader().read(new File("e:/Test.xml"));
	} catch (DocumentException e) {
	    e.printStackTrace();
	}
	
	Date date = new Date();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//System.out.println("The maximum value an int can have in Java: " + java.lang.Integer.MAX_VALUE);
	String lod4building = "", lod4geometry = "", lod4appearance = "", lod4rooms = "";
	//<editor-fold desc="Description generate building geomatry">
	lod4building += makePretty("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"yes\"?>", 0);
	lod4building += makePretty("<!-- Written by OSM2CityGML Export Algorithm - Version 0.4b -->", 0);
	lod4building += makePretty("<!-- Developed and implemented by Marcus Goetz, m.goetz@uni-heidelberg.de -->", 0);
	lod4building += makePretty("<!-- GIScience Research Group, Department of Geography, University of Heidelberg, http://giscience.uni-hd.de -->", 0);
	lod4building += makePretty("<CityModel xmlns=\"http://www.opengis.net/citygml/1.0\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:smil20lang=\"http://www.w3.org/2001/SMIL20/Language\" xmlns:smil20=\"http://www.w3.org/2001/SMIL20/\" xmlns:xAL=\"urn:oasis:names:tc:ciq:xsdschema:xAL:2.0\" xmlns:gen=\"http://www.opengis.net/citygml/generics/1.0\" xmlns:veg=\"http://www.opengis.net/citygml/vegetation/1.0\" xmlns:wtr=\"http://www.opengis.net/citygml/waterbody/1.0\" xmlns:trans=\"http://www.opengis.net/citygml/transportation/1.0\" xmlns:tex=\"http://www.opengis.net/citygml/texturedsurface/1.0\" xmlns:app=\"http://www.opengis.net/citygml/appearance/1.0\" xmlns:frn=\"http://www.opengis.net/citygml/cityfurniture/1.0\" xmlns:grp=\"http://www.opengis.net/citygml/cityobjectgroup/1.0\" xmlns:bldg=\"http://www.opengis.net/citygml/building/1.0\" xmlns:luse=\"http://www.opengis.net/citygml/landuse/1.0\" xmlns:dem=\"http://www.opengis.net/citygml/relief/1.0\" xmlns:gml=\"http://www.opengis.net/gml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.opengis.net/citygml/landuse/1.0 http://schemas.opengis.net/citygml/landuse/1.0/landUse.xsd http://www.opengis.net/citygml/cityfurniture/1.0 http://schemas.opengis.net/citygml/cityfurniture/1.0/cityFurniture.xsd http://www.opengis.net/citygml/appearance/1.0 http://schemas.opengis.net/citygml/appearance/1.0/appearance.xsd http://www.opengis.net/citygml/texturedsurface/1.0 http://schemas.opengis.net/citygml/texturedsurface/1.0/texturedSurface.xsd http://www.opengis.net/citygml/transportation/1.0 http://schemas.opengis.net/citygml/transportation/1.0/transportation.xsd http://www.opengis.net/citygml/waterbody/1.0 http://schemas.opengis.net/citygml/waterbody/1.0/waterBody.xsd http://www.opengis.net/citygml/building/1.0 http://schemas.opengis.net/citygml/building/1.0/building.xsd http://www.opengis.net/citygml/relief/1.0 http://schemas.opengis.net/citygml/relief/1.0/relief.xsd http://www.opengis.net/citygml/vegetation/1.0 http://schemas.opengis.net/citygml/vegetation/1.0/vegetation.xsd http://www.opengis.net/citygml/cityobjectgroup/1.0 http://schemas.opengis.net/citygml/cityobjectgroup/1.0/cityObjectGroup.xsd http://www.opengis.net/citygml/generics/1.0 http://schemas.opengis.net/citygml/generics/1.0/generics.xsd\">", 0);
	lod4geometry += makePretty("<cityObjectMember>", 1);
	lod4geometry += makePretty("<creationDate>" + df.format(date) + "</creationDate>", 2);
	//</editor-fold>
 
	String bldgName = "", bldgType = "", bldgYearOfConstruction = "", xalCountryName = "", xalLocalityName = "", xalThoroughfareNumber = "", xalThoroughfareName = "", xalPostalCodeNumber = "";
	//long bldgClass, bldgFunction, bldgUsage, bldgRoofType, bldgStoreysAboveGround, bldgStoreysBelowGround;
	double bldgMeasuredLevels = 0;
	Iterator<Element> reElement = doc.getRootElement().elementIterator("relation");
	Element tag=null;
	String tagKV="";
	while (reElement.hasNext()) {
	    Element elem1 = reElement.next();
	    Iterator<Element> tagElement = elem1.elementIterator("tag");
	    while (tagElement.hasNext()) {
		Element elem2 = tagElement.next();
		if (elem2.attributeValue("k").equals("building")) {
		   Element elem = elem2.getParent();
		    String bldgID = elem.attributeValue("id");
		    //building
//	xpath = "//osm/relation[@id='-43285']";
//	//xpath = "//relation[//tag[@k='building']]";
//	List<Element> users =  doc.selectNodes(xpath);
//	Iterator<Element> it = users.iterator();
//	//Iterator<Element> it = doc.getRootElement().element("relation").element("tag");
//	//region Description
//	while (it.hasNext()) {
//	    Element elem = it.next();
		    if(elem.elementIterator("tag").hasNext()){
			Iterator<Element> tags = elem1.elementIterator("tag");
			while (tags.hasNext()) {
			    tag = tags.next();
			    //System.out.println(tag.attributeValue("k")+"  "+tag.attributeValue("v" ));
			    tagKV+=tag.attributeValue("k")+"@"+tag.attributeValue("v" )+"; ";
			}
		    }
		    System.out.println(tagKV);
		    KeyValuePair[] bkv = getKV(tagKV);
		    
		    //<editor-fold desc="Description： tags K and V">
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
		    //</editor-fold>
		    
		    lod4geometry += makePretty("<bldg:Building gml:id=\"ID_" + bldgID + "\">", 2);
		    lod4geometry += makePretty("<gml:name>" + bldgName + "</gml:name>", 3);
		    lod4geometry += makePretty("<bldg:MeasuredHeight>" + bldgMeasuredLevels + "</bldg:MeasuredHeight>", 3);
		    lod4geometry += makePretty("<bldg:YearOfConstruction>" + bldgYearOfConstruction + "</bldg:YearOfConstruction>", 3);
		    
		    //细化楼层房间等
		    
		    //<editor-fold desc="Description: genarate building  ">
		    lod4geometry += makePretty("<!-- Address -->", 3);
		    lod4geometry += makePretty("<bldg:address>", 3);
		    lod4geometry += makePretty("<Address>", 4);
		    lod4geometry += makePretty("<xalAddress>", 5);
		    lod4geometry += makePretty("<xAL:AddressDetails>", 6);
		    lod4geometry += makePretty("<xAL:Country>", 7);
		    lod4geometry += makePretty("<xAL:CountryName>" + xalCountryName + "</xAL:CountryName>", 8);
		    lod4geometry += makePretty("<xAL:Locality Type=\"Town\">", 8);
		    lod4geometry += makePretty("<xAL:LocalityName>" + xalLocalityName + "</xAL:LocalityName>", 9);
		    lod4geometry += makePretty("<xAL:Thoroughfare Type=\"Street\">", 9);
		    lod4geometry += makePretty("<xAL:ThoroughfareNumber>" + xalThoroughfareNumber + "</xAL:ThoroughfareNumber>", 10);
		    lod4geometry += makePretty("<xAL:ThoroughfareName>" + xalThoroughfareName + "</xAL:ThoroughfareName>", 10);
		    lod4geometry += makePretty("</xAL:Thoroughfare>", 9);
		    lod4geometry += makePretty("<xAL:PostalCode>", 9);
		    lod4geometry += makePretty("<xAL:PostalCodeNumber>" + xalPostalCodeNumber + "</xAL:PostalCodeNumber>", 10);
		    lod4geometry += makePretty("</xAL:PostalCode>", 9);
		    lod4geometry += makePretty("</xAL:Locality>", 8);
		    lod4geometry += makePretty("</xAL:Country>", 7);
		    lod4geometry += makePretty("</xAL:AddressDetails>", 6);
		    lod4geometry += makePretty("</xalAddress>", 5);
		    lod4geometry += makePretty("</Address>", 4);
		    lod4geometry += makePretty("</bldg:address>", 3);
		    
		    lod4building += lod4geometry;
		    lod4building += makePretty("</bldg:Building>", 2);
		    lod4building += makePretty("</cityObjectMember>", 1);
		    //</editor-fold>
		}
		//endregion
		
		File out = new File("e:/LoD4.xml");
		try {
		    FileOutputStream output = new FileOutputStream(out);
		    output.write(lod4building.getBytes());
		    output.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
    }
    
    public void Level(Document doc){
	String xpath = "/osm/relation/tag[@k='level']";
	List<Element> Leveltags =  doc.selectNodes(xpath);
	Iterator<Element> it = Leveltags.iterator();
	while (it.hasNext()) {
	    Element elem1 = it.next().getParent();
	    Element elemLevel=(Element)elem1.selectSingleNode("child::node()[@k='level']");
	    int buildingLevel=Integer.parseInt(elemLevel.attributeValue("v"));
	    //double buildingLevel=Double.parseDouble(it.next().attributeValue("v"));
	    Element elemHeight=(Element)elem1.selectSingleNode("/osm/relation/tag[@k='height']");
	    double levelHeight=Double.parseDouble(elemHeight.attributeValue("v"));
	    System.out.println(elem1.attributeValue("id")+"  "+buildingLevel +"  "+levelHeight);
	}
    }
    
    
    public void Room(Element element){
    
    
    
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
