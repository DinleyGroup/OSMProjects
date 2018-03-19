import Convertion.Convertion;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //输入处理的多个shapefile文件路径 inputShapefiles
        //输出转换完成的osm文件路径 outputOsm
        String inputShapefiles,outputOsm;
        Scanner scanner = new Scanner(System.in);

        System.out.println("input shapefiles:");
        inputShapefiles=scanner.nextLine();

        System.out.println("output OSM file:");
        outputOsm=scanner.nextLine();

        Convertion convertion=new Convertion(inputShapefiles,outputOsm);
        convertion.convert();
    }
}
