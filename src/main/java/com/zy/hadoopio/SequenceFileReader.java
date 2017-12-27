package com.zy.hadoopio;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.ReflectionUtils;

import java.net.URI;

public class SequenceFileReader {

    public static void main(String[] args) throws Exception{
        String uri = args[0];
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(uri),conf);
        Path path = new Path(uri);

        SequenceFile.Reader reader = null;

        try {

            reader = new SequenceFile.Reader(fs,path,conf);
            Writable key = (Writable) ReflectionUtils.newInstance(reader.getKeyClass(),conf);
            Writable value = (Writable) ReflectionUtils.newInstance(reader.getValueClass(),conf);

            long position = reader.getPosition();
            while (reader.next(key,value)){
                String sync = reader.syncSeen() ? "*" : "";
                System.out.printf("\t[%d,%s],\t %s:%s\n",position,sync,key,value);
                //get next position
                position = reader.getPosition();
            }
        } finally {
            IOUtils.closeStream(reader);
        }
    }
}
