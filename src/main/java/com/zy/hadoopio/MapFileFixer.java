package com.zy.hadoopio;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.SequenceFile;

import java.net.URI;

/**
 * sequenceFile 转换成MapFile对象
 */
public class MapFileFixer {

    public static void main(String[] args) throws Exception{
        String mapUri = args[0];
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(mapUri),conf);
        Path map = new Path(mapUri);
        Path mapData = new Path(mapUri, MapFile.DATA_FILE_NAME);

        /*get zhe key value from the sequenceFile*/
        SequenceFile.Reader reader = new SequenceFile.Reader(fs,mapData,conf);
        Class key = reader.getKeyClass();
        Class value = reader.getKeyClass();

        //create the map file index file
        long entry = MapFile.fix(fs,map,key,value,false,conf);
        System.out.printf("Create the MapFile: %s with  entries:%d \n\t", map, entry);
    }
}
