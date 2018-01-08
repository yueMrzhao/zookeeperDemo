package com.zy.mpdev;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ConfigurationPrinter extends Configured implements Tool {

    static {
        Configuration.addDefaultResource("hdfs-default.xml");
        Configuration.addDefaultResource("hdfs-site.xml");
        Configuration.addDefaultResource("yarn-default.xml");
        Configuration.addDefaultResource("yarn-site.xml");
        Configuration.addDefaultResource("mapred-default.xml");
        Configuration.addDefaultResource("mapred-site.xml");
    }


    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();
        conf.forEach(stringStringEntry -> System.out.printf("%s=%s\n",stringStringEntry.getKey(),stringStringEntry.getValue()));
        return 0;
    }

    public static void main(String[] args) throws Exception{
//        System.out.println(System.getenv("MAVEN_HOME"));
        System.setProperty("hadoop.home.dir", "/Users/zy/tools/hadoop-2.6.5");
        int exitCode = ToolRunner.run(new ConfigurationPrinter(),args);
        System.exit(exitCode);
    }
}
