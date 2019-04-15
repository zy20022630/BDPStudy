package com.zy.study.etl;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class HDFSToHBaseMRMapper2 extends Mapper<LongWritable, Text, Text, NullWritable> {

    /**
     * 每次读取一行数据
     *
     * /test/taobao_bi/raw_sample.csv
     *
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        context.write(value, NullWritable.get());
    }

}