package com.zy.mrtypes;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;

public class JobBuilder {

    public static void pritUsage(Tool tool, String mess){
        System.out.printf("Usage:%s,[genericOptions]:%s\n\n",tool.getClass().getSimpleName(),mess);
        GenericOptionsParser.printGenericCommandUsage(System.err);
    }

    public static Job getJob(Tool tool, Configuration conf, String[] args) throws Exception{

        if(args.length!=2){
            pritUsage(tool,"<input><output>");
            return null;
        }
        Job job = new Job(conf);
        job.setJarByClass(job.getClass());
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        return job;
    }

}
