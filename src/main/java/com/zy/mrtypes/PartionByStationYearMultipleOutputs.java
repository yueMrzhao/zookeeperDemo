package com.zy.mrtypes;

import com.zy.mpdev.v1.NedcRecordParser;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

public class PartionByStationYearMultipleOutputs extends Configured implements Tool{

    static class PartionMapper extends Mapper<LongWritable,Text,Text,Text>{

        private NedcRecordParser parser = new NedcRecordParser();
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            parser.parse(value);
            if (parser.isValidTemperature()){
                context.write(new Text(parser.getStationId()),value);
            }
        }
    }

    static class PartionReducer extends Reducer<Text,Text,NullWritable,Text>{

        private MultipleOutputs multipleOutputs;
        private NedcRecordParser parser = new NedcRecordParser();

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            multipleOutputs = new MultipleOutputs(context);
        }

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text value : values) {
                parser.parse(value);
                String bashpath = String.format("%s/%s/part",parser.getStationId(),parser.getYear());
                multipleOutputs.write(NullWritable.get(),value,bashpath);
            }
            super.reduce(key, values, context);
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            multipleOutputs.close();
        }
    }


    @Override
    public int run(String[] args) throws Exception {

        Job job = JobBuilder.getJob(this,getConf(),args);
        if (job == null){
            return -1;
        }

        job.setMapperClass(PartionMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setReducerClass(PartionReducer.class);
        job.setOutputKeyClass(NullWritable.class);
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception{
        int exitCode = ToolRunner.run(new PartionByStationYearMultipleOutputs(),args);
        System.exit(exitCode);
    }
}
