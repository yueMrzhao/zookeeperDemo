package com.zy.mrtypes;

import com.zy.mpdev.v1.MaxTemperatureReducer;
import com.zy.mpdev.v1.NedcRecordParser;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MultiFileInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

public class MaxTemperatureWithMultipleInput extends Configured implements Tool{


    static class MetOfficeMaxtemperatureMapper extends Mapper<LongWritable,Text,Text,IntWritable>{

        private NedcRecordParser parser = new NedcRecordParser();
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            parser.parse(value);
            if(parser.isValidTemperature()){
                context.write(new Text(parser.getYear()),new IntWritable(parser.getAirTemperature()));
            }
        }
    }

    @Override
    public int run(String[] args) throws Exception {

        if(args.length!=3){
            System.out.printf("Usage : %s [generic options] <ncdc input> <metoffice input> <output>\n", getClass().getSimpleName());
            ToolRunner.printGenericCommandUsage(System.err);
            return -1;
        }

        Path ncdc = new Path(args[0]);
        Path metoffice = new Path(args[1]);
        Path output = new Path(args[2]);

        Job job = new Job(getConf());
        job.setJarByClass(getClass());

        MultipleInputs.addInputPath(job,ncdc, TextInputFormat.class, MetOfficeMaxtemperatureMapper.class);
        MultipleInputs.addInputPath(job,metoffice,TextInputFormat.class,MetOfficeMaxtemperatureMapper.class);
        FileOutputFormat.setOutputPath(job,output);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setCombinerClass(MaxTemperatureReducer.class);
        job.setReducerClass(MaxTemperatureReducer.class);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception{
        int exitCode = ToolRunner.run(new MaxTemperatureWithMultipleInput(),args);
        System.exit(exitCode);
    }
}
