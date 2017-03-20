package com.zy.file;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileInputFormat;
import org.junit.Before;

import java.io.IOException;

/**
 * 用于展示file的状态，大小，权限
 * Created by zy on 2017/3/15.
 */
public class ShowFileStatus {

//    private MiniDFSCluster

    @Before
    public void setUp() throws IOException {
        Configuration conf = new Configuration();
        if(System.getProperty("test.build.data") == null){
            System.setProperty("test.build.data", "/zy");
        }

        FileSystem file = FileSystem.get(conf);
        Path path = new Path("/zy");
        FSDataInputStream in = file.open(path);

    }
}
