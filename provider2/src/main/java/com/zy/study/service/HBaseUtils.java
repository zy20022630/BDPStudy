package com.zy.study.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

/**
 * Created by angel on 4/12/19.
 */
public class HBaseUtils {

    private static Connection connection = null;

    private HBaseUtils(){
        super();
    }

    private static Configuration getHBaseConfiguration() {
        Configuration config = HBaseConfiguration.create();

        //告诉它我们的hbase在哪
        config.set("hbase.zookeeper.quorum", "Slave01,Slave02,Slave03");
        config.set("zookeeper.znode.parent", "/hbase");

        config.set("fs.defaultFS", "hdfs://Master:9000");


        //注意要把这两个配置文件下载下来，放到自己建的config包下面
        config.addResource("core-site.xml");
        config.addResource("hdfs-site.xml");
        config.addResource("hbase-site.xml");


        System.setProperty("HADOOP_USER_NAME", "angel");

        return config;
    }

    public static Connection openConnection() throws Exception{
        if (connection == null){
            connection = ConnectionFactory.createConnection(getHBaseConfiguration());
        }
        return connection;
    }

    public static void closeConnection() throws Exception{
        if (connection != null){
            connection.close();
        }
    }

}
