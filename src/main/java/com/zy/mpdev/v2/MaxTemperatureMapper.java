package com.zy.mpdev.v2;

import com.zy.mpdev.v1.NedcRecordParser;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MaxTemperatureMapper extends Mapper<LongWritable,Text,Text,IntWritable> {

    private NedcRecordParser parser = new NedcRecordParser();

    enum Temperature {
        OVER_100
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        parser.parse(value);
        if (parser.isValidTemperature()) {
           int temperature = parser.getAirTemperature();
           if (temperature > 100) {
               System.err.println("温度超过100度"+ temperature);
               context.setStatus("温度超过100度"+ temperature);
               context.getCounter(Temperature.OVER_100).increment(1L);
           }
           context.write(new Text(parser.getYear()), new IntWritable(temperature));
        }
    }
}
