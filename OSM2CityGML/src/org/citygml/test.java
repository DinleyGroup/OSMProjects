package org.citygml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.jaxen.*;

public class test {
    public static void main(String[] args) {
    
	String xpath = "";
	Document doc = null;
	try {
	    doc = new SAXReader().read(new File("e:/Test.xml"));
	} catch (DocumentException e) {
	    e.printStackTrace();
	}
 
 
	xpath = "/osm/relation/tag[@k='level']";
	//xpath = "//relation[//tag[@k='building']]";
	List<Element> Leveltags =  doc.selectNodes(xpath);
	Iterator<Element> it = Leveltags.iterator();

	//Iterator<Element> it = doc.getRootElement().element("relation").element("tag");
	while (it.hasNext()) {
	    Element elem1 = it.next().getParent();
	    Element elemLevel=(Element)elem1.selectSingleNode("child::node()[@k='level']");
	    int buildingLevel=Integer.parseInt(elemLevel.attributeValue("v"));
	    //double buildingLevel=Double.parseDouble(it.next().attributeValue("v"));
	    Element elemHeight=(Element)elem1.selectSingleNode("/osm/relation/tag[@k='height']");
	    double levelHeight=Double.parseDouble(elemHeight.attributeValue("v"));
	    
	    System.out.println(elem1.attributeValue("id")+"  "+buildingLevel +"  "+levelHeight);
	}

	
	/*Iterator<Element> reElement = doc.getRootElement().elementIterator("relation");
	Element elem = null;
	//region Description
	while (reElement.hasNext()) {
	    Element elem1 = reElement.next();
	    Iterator<Element> tagElement = elem1.elementIterator("tag");
	    while (tagElement.hasNext()) {
		Element elem2 = tagElement.next();
		if (elem2.attributeValue("k").equals("building")) {
		    elem = elem2.getParent();
		    if(elem.elementIterator("tag").hasNext()){
			Iterator<Element> tags = elem1.elementIterator("tag");
			while (tags.hasNext()) {
			    Element tag = tags.next();
			    System.out.println(tag.attributeValue("k")+"  "+tag.attributeValue("v" )+"6666");
			}
		    }
		}
	    }
	}*/
	//endregion
	
	
 
 
 
 
//	File out = new File("e:/Test.xml");
//	try {
//	    FileOutputStream output = new FileOutputStream(out);
//	    output.write(lod4building.getBytes());
//	    output.close();
//	} catch (IOException e) {
//	    e.printStackTrace();
//	}
/*	KeyValuePair[] te=getKV2("kk=>456123, vv=>456");
	System.out.println(te[0].getKey()+"  "+te[0].getValue());
	System.out.println(te[1].getKey()+"  "+te[1].getValue());*/
    }
    
    
    private static KeyValuePair[] getKV2(String str) {
	String[] str_parts = str.split(", ");
	KeyValuePair[] res = new KeyValuePair[str_parts.length];
	for (int i = 0; i < str_parts.length; i++) {
	    String[] kvp = str_parts[i].split("=>");
	    for (int k = 0; k < kvp.length; k++) {
		kvp[k] = kvp[k].replace("\"", "");
	    }
	    res[i] = new KeyValuePair(kvp[0], kvp[1]);
	    
	}
	return res;
    }
}
