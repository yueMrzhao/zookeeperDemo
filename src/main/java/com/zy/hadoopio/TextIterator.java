package com.zy.hadoopio;

import org.apache.hadoop.io.Text;

import java.nio.ByteBuffer;

public class TextIterator {

    public static void main(String[] args) {
        Text t = new Text("\u0041\u00DF\u6771\uD801\uDC00");
        String st = "\u0041\u00DF\u6771\uD801\uDC00";
        byte[] tBytes = t.getBytes();
        ByteBuffer bf = ByteBuffer.wrap(t.getBytes(),0,t.getLength());
        int r =0;
        while(bf.hasRemaining() && (r = Text.bytesToCodePoint(bf)) != -1){
            System.out.printf("the result is :%s \n", Integer.toHexString(r));
        }


        System.out.printf("is.length is :%s \n", st.length());
        System.out.printf("index.of('u0041') is :%s \n", st.indexOf("\u0041"));
        System.out.printf("index.of('u00DF') is :%s \n", st.indexOf("\u00DF"));
        System.out.printf("index.of('u6771') is :%s \n", st.indexOf("\u6771"));
        System.out.printf("index.of('uD801') is :%s \n", st.indexOf("\uD801"));
        System.out.printf("index.of('uDC00') is :%s \n", st.indexOf("\uDC00"));

        System.out.printf("char.at(0) is :%s \n", st.charAt(0));
        System.out.printf("char.at(1) is :%d \n", st.indexOf(1));
        System.out.printf("char.at(2) is :%d \n", st.indexOf(2));
        System.out.printf("char.at(3) is :%d \n", st.indexOf(3));
        System.out.printf("char.at(4) is :%d \n", st.indexOf(4));

        System.out.printf("t.getLength is %s \n", t.getLength());
        System.out.printf("t.find(u0041) is %s \n", t.find("\u0041"));
        System.out.printf("t.find(u00DF) is %s \n", t.find("\u00DF"));
        System.out.printf("t.find(u6771) is %s \n", t.find("\u6771"));
        System.out.printf("t.find(uD801uDC00) is %s \n", t.find("\uD801\uDC00"));
    }
}
