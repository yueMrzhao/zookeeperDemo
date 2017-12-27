package com.zy.hadoopio;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public class FileDecompressor {

    public static void main(String[] args) throws Exception{

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(args[0]),conf);
        Path input = new Path(args[0]);

        CompressionCodecFactory factory = new CompressionCodecFactory(conf);
        CompressionCodec codec = factory.getCodec(input);

        String output = CompressionCodecFactory.removeSuffix(args[0],codec.getDefaultExtension());

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try{

            inputStream = codec.createInputStream(fs.open(input));
            outputStream = fs.create(new Path(output));
            IOUtils.copyBytes(inputStream,outputStream,conf);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(inputStream);
            IOUtils.closeStream(outputStream);
        }
    }
}
