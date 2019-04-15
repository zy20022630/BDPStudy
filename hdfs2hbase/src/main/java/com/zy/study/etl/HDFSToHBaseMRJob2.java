package com.zy.study.etl;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.util.Tool;

public class HDFSToHBaseMRJob2 extends Configured implements Tool {

    @Override
    public int run(String[] strings) throws Exception {
        Configuration config = HBaseConfiguration.create();

        //告诉它我们的hbase在哪
        config.set("hbase.zookeeper.quorum", "Slave01,Slave02,Slave03");
        config.set("zookeeper.znode.parent", "/hbase");

        config.set("fs.defaultFS", "hdfs://Master:9000");
		
		
		config.set("dfs.namenode.handler.count", "50");//
		config.set("dfs.client.socket-timeout", "300000");//
		config.set("mapreduce.task.timeout", "0");//
		

        //注意要把这两个配置文件下载下来，放到自己建的config包下面
        config.addResource("core-site.xml");
        config.addResource("hdfs-site.xml");
        config.addResource("hbase-site.xml");

        config.set("hbase.regionserver.handler.count", "50");//默认值10
		config.set("hbase.regionserver.lease.period", "300000");//默认值60000
		
		
        System.setProperty("HADOOP_USER_NAME", "angel");


        Job job = Job.getInstance(config, "HDFSToHBaseMRJob2");
        job.setJarByClass(HDFSToHBaseMRJob2.class);

        job.setMapperClass(HDFSToHBaseMRMapper2.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        //  设置数据读取组件(因为是读取HDFS，相当于我们没改)
        job.setInputFormatClass(TextInputFormat.class);

        /*
            设置数据的输出组件，第一个参数就是表名,第二个参数就是ReduceTask,这个方法会做两个事
            1：改掉我们的数据输出组件
            2：设置一个表名并获取到Htable对象
            3：调用我们reduce阶段我们输出的所有put对象
            4:自动往HBase中去插入
         */

        TableMapReduceUtil.initTableReducerJob("behavior", HDFSToHBaseMRReducer2.class, job);
//        TableMapReduceUtil.initTableReducerJob("behavior_test", HDFSToHBaseMRReducer2.class, job);//Test
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Put.class);

        //HDFS的文件目录
        FileInputFormat.addInputPath(job, new Path("/test/taobao_bi_part/raw_sample_top2w.csv"));
//        FileInputFormat.addInputPath(job, new Path("/test/taobao_bi/raw_sample.csv"));
//        FileInputFormat.addInputPath(job, new Path("/test/taobao_bi_test/raw_simple_top20.csv"));//Test

        boolean isDone = job.waitForCompletion(true);

        return isDone ? 0 : 1;
    }
}