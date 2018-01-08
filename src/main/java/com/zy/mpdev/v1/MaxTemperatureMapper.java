package com.zy.mpdev.v1;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MaxTemperatureMapper extends Mapper<LongWritable,Text,Text,IntWritable> {

    private static Logger log = LoggerFactory.getLogger(MaxTemperatureMapper.class);
    private NedcRecordParser parser = new NedcRecordParser();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        parser.parse(value);
        if (parser.isValidTemperature()){
            context.write(new Text(parser.getYear()), new IntWritable(parser.getAirTemperature()));
        }
    }
}
