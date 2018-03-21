package coordinate.convertion;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ModXML {
    
    public static void main(String[] args) throws IOException {
	ModXML modXML = new ModXML();
	modXML.modify();
	System.out.println("Hello, your command Completed!");
    }
    
    public void modify() throws IOException {
	List<Node> list = new ArrayList<Node>();
	CoordinateConversion coordinateConversion = new CoordinateConversion();
	//修改就这三行代码
	Document doc = null;
	try {
	    doc = new SAXReader().read(new File("e:/Test.xml"));
	} catch (DocumentException e) {
	    e.printStackTrace();
	}
	//读取contact标签
	Iterator<Element> it = doc.getRootElement().elementIterator("node");
	while (it.hasNext()) {
	    Element elem = it.next();
	    String Lat = elem.attributeValue("lat");
	    String Lon = elem.attributeValue("lon");
	    
	    Point point = coordinateConversion.coordConvert("EPSG:4326", "EPSG:4547",
		    new Point(Double.parseDouble(Lat), Double.parseDouble(Lon), 0));
	    
	    Node node = new Node();
	    node.setLat(point.x);
	    node.setLon(point.y);
	    
	    elem.addAttribute("lat",String.valueOf(node.lat));
	    elem.addAttribute("lon",String.valueOf(node.lon));
	    //list.add(node);
	    //elem.setText("lat")= String.valueOf(node.lat);
	}
	
	FileOutputStream out = new FileOutputStream("e:/Node2.xml");
	OutputFormat format = OutputFormat.createPrettyPrint();
	format.setEncoding("utf-8");
	XMLWriter writer = null;
	writer = new XMLWriter(out, format);
	writer.write(doc);
	writer.close();
    }
}
