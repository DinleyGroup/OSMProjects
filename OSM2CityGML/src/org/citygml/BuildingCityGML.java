package org.citygml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class BuildingCityGML {
    
    public static void main(String[] args) throws DocumentException {
	Document doc = null;
	String xpath = "//relation/tag[@k='building']/..";
	doc = new SAXReader().read(new File("e:/EBuilding2.xml"));
	List<Element> buidingElements = doc.selectNodes(xpath);
	//reElement = buidingElements.iterator();
	System.out.println(buidingElements.listIterator().hasNext());
	//reElement = doc.getRootElement().elementIterator("relation");
	for (Element buildingElement : buidingElements) {
	    String ID = buildingElement.attributeValue("id");
	    List<Element> subelement = buildingElement.elements("tag");
	    for (Element tagElement : subelement) {
	    
		String ddd=tagElement.attributeValue("k") + "@" + tagElement.attributeValue("v") + "; ";
		System.out.println("value:"+ddd);
		List<Attribute> listAttributes = tagElement.attributes();
		for (Attribute attr : listAttributes) {
		    String name = attr.getName();
		    String value = attr.getValue();
		    System.out.println("value:"+value);
		}
	    }
	}
    }
}
