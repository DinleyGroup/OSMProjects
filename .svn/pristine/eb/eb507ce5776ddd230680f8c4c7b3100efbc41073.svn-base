package Convertion;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Iterator;

public class Convertion {
    
    private String inputShapefiles;
    private String outputOsm;

    public Convertion(String inputShapefiles, String outputOsm){
        this.inputShapefiles=inputShapefiles;
        this.outputOsm =outputOsm;
    }

    public void convert(){

        //读取SHP文件
        ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
        try {
            ShapefileDataStore sds = (ShapefileDataStore)dataStoreFactory.createDataStore(new File(inputShapefiles).toURI().toURL());
            sds.setCharset(Charset.forName("GBK"));
            SimpleFeatureSource featureSource = sds.getFeatureSource();
            SimpleFeatureIterator itertor = featureSource.getFeatures().features();
            while(itertor.hasNext()) {
                SimpleFeature feature = itertor.next();
                System.out.print(feature.getID());
                System.out.print(": ");
                System.out.println(feature.getDefaultGeometryProperty().getValue());//此行输出的空间信息的wkt表达形式
                Iterator<Property> it = feature.getProperties().iterator();
                while(it.hasNext()) {
                    Property pro = it.next();
                    System.out.println(pro);//输出属性信息
                }
            }
            itertor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //转换成OSM


    }
}
