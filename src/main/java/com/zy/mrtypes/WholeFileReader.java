package com.zy.mrtypes;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.DataInputStream;
import java.io.IOException;

public class WholeFileReader extends RecordReader<NullWritable,BytesWritable>{

    private boolean process;
    private FileSplit fileSplit;
    private Configuration conf;
    private BytesWritable value = new BytesWritable();

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        this.fileSplit = (FileSplit) split;
        conf = context.getConfiguration();
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {

        if (!process){
            byte[] content = new byte[(int)fileSplit.getLength()];
            Path file      = fileSplit.getPath();
            FileSystem fs  = FileSystem.get(file.toUri(),conf);
            DataInputStream dataInputStream = null;

            try {
                dataInputStream = fs.open(file);
                IOUtils.readFully(dataInputStream,content,0,content.length);
                value.set(content,0,content.length);
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                IOUtils.closeStream(dataInputStream);
            }

            process = true;
        }
        return process;
    }

    @Override
    public NullWritable getCurrentKey() throws IOException, InterruptedException {
        return NullWritable.get();
    }

    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return process ? 1.0f : 0.0f;
    }

    @Override
    public void close() throws IOException {

    }
}
