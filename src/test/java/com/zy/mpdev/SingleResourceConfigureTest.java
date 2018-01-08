package com.zy.mpdev;


import org.apache.hadoop.conf.Configuration;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SingleResourceConfigureTest {

    @Test
    public void get() throws Exception{
        Configuration conf = new Configuration();
        conf.addResource("confiuration1.xml");
        assertThat (conf.get("color"), is("yellow"));
        assertThat(conf.getInt("size",0), is(10));
        assertThat(conf.get("weight",""),is("heavy"));
        assertThat(conf.get("size-weight"),is("10,heavy"));
        System.setProperty("size","20");
        assertThat(conf.get("size-weight"),is("20,heavy"));
        conf.setInt("size", 40);
        assertThat(conf.get("size-weight"),is("20,heavy"));
    }
}
