package com.zy.hadoopio;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.*;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;

public class StreamCompressor {

    public static void main(String[] args) throws Exception{
        String compressType = "org.apache.hadoop.io.compress.GzipCodec"; //"DefaultCodec,Bzip2Codec"
        Configuration conf = new Configuration();
        Class<?> codecClass = Class.forName(compressType);

        CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(codecClass,conf);
        Compressor compressor = null;
        Decompressor decompressor = null;

        try{

            compressor = CodecPool.getCompressor(codec);
            decompressor = CodecPool.getDecompressor(codec);
            ByteOutputStream outByte = new ByteOutputStream();
            CompressionOutputStream out = codec.createOutputStream(outByte, compressor);
            CompressionInputStream in = codec.createInputStream(System.in,decompressor);
            IOUtils.copyBytes(System.in,out,1024,false);


            byte[] bytes = new byte[1024];
            ByteInputStream inByte = new ByteInputStream();
            out.write(bytes);
            out.flush();
            inByte.setBuf(bytes);
            IOUtils.copyBytes(inByte,System.out,4096,true);
        } finally {
            CodecPool.returnCompressor(compressor);
            CodecPool.returnDecompressor(decompressor);
        }


    }
}
