package com.zy.hadoopio;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.io.WritableUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TextPair implements WritableComparable<TextPair>{

    private Text first;
    private Text second;

    public void set(Text first,Text second){
        this.first = first;
        this.second= second;
    }

    public TextPair(){
        first = new Text();
        second = new Text();
    }

    public TextPair(Text first,Text second){
        set(first,second);
    }

    public Text getFirst() {
        return first;
    }

    public void setFirst(Text first) {
        this.first = first;
    }

    public Text getSecond() {
        return second;
    }

    public void setSecond(Text second) {
        this.second = second;
    }

    @Override
    public int hashCode() {
        return first.hashCode()*163 + second.hashCode();
    }


    @Override
    public String toString() {
        return "TextPair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }

    public int compareTo(TextPair o) {
        int temp = first.compareTo(o.first);
        if(temp != 0){
            return temp;
        }
        return second.compareTo(o.second);
    }

    public void write(DataOutput out) throws IOException {
        first.write(out);
        second.write(out);
    }

    public void readFields(DataInput in) throws IOException {
        first.readFields(in);
        second.readFields(in);
    }

    public static class Compator extends WritableComparator{

        private static final Text.Comparator TEXT_COMPARATOR = new Text.Comparator();

        public Compator(){
            super(TextPair.class);
        }

        static {
            WritableComparator.define(TextPair.class,new Compator());
        }
        @Override
        public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {

                int firstL1 = WritableUtils.decodeVIntSize(b1[s1]) + readInt(b1, s1);
                int firstL2 = WritableUtils.decodeVIntSize(b2[s2]) + readInt(b2, s2);
                int temp = TEXT_COMPARATOR.compare(b1, s1, firstL1, b2, s2, firstL2);
                if (temp != 0) {
                    return temp;
                }
                return TEXT_COMPARATOR.compare(b1, s1 + firstL1, l1 - firstL1, b2, s2 + firstL2, l2 - firstL2);
        }
    }
}
