package com.zy.mrtypes;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

public class SmallFileToSequenceFileConvetor extends Configured implements Tool{

    static class SequenceFileMapper extends Mapper<NullWritable,BytesWritable,Text,BytesWritable>{
        Text filenameKey;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {

            InputSplit inputSplit = context.getInputSplit();
            Path path = ((FileSplit)inputSplit).getPath();
            filenameKey = new Text(path.toString());
        }

        @Override
        protected void map(NullWritable key, BytesWritable value, Context context) throws IOException, InterruptedException {
            context.write(filenameKey,value);
        }
    }
    @Override
    public int run(String[] args) throws Exception {

        Job job = JobBuilder.getJob(this,getConf(),args);
        if (job == null ) return -1;

        job.setInputFormatClass(WoleFileInputFormat.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);

        job.setMapperClass(SequenceFileMapper.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(BytesWritable.class);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception{
        int exitCode = ToolRunner.run(new SmallFileToSequenceFileConvetor(),args);
        System.exit(exitCode);
    }
}
