package test.org.citygml; 

import org.citygml.Lod4Buiding;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.io.File;

/** 
* Lod4Buiding Tester. 
* 
* @author <Authors name> * @since <pre>���� 3, 2018</pre>
* @version 1.0 
*/ 
public class Lod4BuidingTest {
    String xpath = "/osm/relation/tag[@k='level']";
    Document doc = null;
    Lod4Buiding  lod=new Lod4Buiding();
    
@Before
public void before() throws Exception {
    System.out.println("before");
    doc = new SAXReader().read(new File("e:/Test.xml"));
   
} 

@After
public void after() throws Exception {
    lod.Level(doc);
    System.out.println("after");
} 

/** 
* 
* Method: main(String[] args) 
* 
*/ 
@Test
public void testMain() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: osm2CityGML() 
* 
*/ 
@Test
public void testOsm2CityGML() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: Level(Document doc) 
* 
*/ 
@Test
public void testLevel() throws Exception {
//TODO: Test goes here...

} 

/** 
* 
* Method: Room(Element element) 
* 
*/ 
@Test
public void testRoom() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: makePretty(String text, int numTabs) 
* 
*/ 
@Test
public void testMakePretty() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getKV(String str) 
* 
*/ 
@Test
public void testGetKV() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: isKeyDefined(KeyValuePair[] kv, String key) 
* 
*/ 
@Test
public void testIsKeyDefined() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getValue(KeyValuePair[] kv, String key) 
* 
*/ 
@Test
public void testGetValue() throws Exception { 
//TODO: Test goes here... 
} 


} 
