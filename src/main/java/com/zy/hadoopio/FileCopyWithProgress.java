package com.zy.hadoopio;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.*;
import java.net.URI;

public class FileCopyWithProgress {

    public static void main(String[] args) {

        System.out.println(System.getProperty("hadoop.master"));



        String fspath    = "hdfs";
        String localPath = "";
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(localPath));

            Configuration conf = new Configuration();
            FileSystem fileSystem = FileSystem.get(URI.create(args[1]), conf);

            OutputStream out = fileSystem.create(new Path(args[1]), new Progressable() {
                public void progress() {
                    System.out.println(".");
                }
            });

            IOUtils.copyBytes(in, out, 4096, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException io) {

        }
    }
}
