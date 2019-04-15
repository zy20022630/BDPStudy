package com.zy.study.etl;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;

public class HDFSToHBaseMRReducer2 extends TableReducer<Text, NullWritable, NullWritable> {

    /**
     * ============================ 读取到的一行数据 ============================

hbase(main):003:0> alter 'behavior', NAME => 'raw'
     说明：
     行键row_key			user_id + time_stamp
     列簇raw				adgroup_id		pid		clk	  adgroup_id=a, pid=p, clk=c

     目标：记录原始样本骨架信息（raw_sample）
        time_stamp	(t)		时间（时间戳）
        adgroup_id	(a)		广告ID（脱敏过的广告单元ID）
        pid			(p)		资源位（资源位）
        clk			(c)		是否点击（为0 代表没有点击 为1 代表点击）

HDFS中/test/taobao_bi/raw_sample.csv文件：

     user,   time_stamp,     adgroup_id, pid,            nonclk, clk

     581738, 1494137644,     1,          430548_1007,    1,      0
     449818, 1494638778,     3,          430548_1007,    1,      0
     914836, 1494650879,     4,          430548_1007,    1,      0
     914836, 1494651029,     5,          430548_1007,    1,      0
     399907, 1494302958,     8,          430548_1007,    1,      0
     628137, 1494524935,     9,          430548_1007,    1,      0
     298139, 1494462593,     9,          430539_1007,    1,      0

     字段说明如下：
     (1) user_id：脱敏过的用户ID；
     (2) adgroup_id：脱敏过的广告单元ID；
     (3) time_stamp：时间戳；
     (4) pid：资源位；
     (5) noclk：为1 代表没有点击；为0 代表点击；
     (6) clk：为0 代表没有点击；为1 代表点击；

     raw_sample 原始的样本骨架 用户ID，广告ID，时间，资源位，是否点击
     */
    @Override
    protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        String[] array = key.toString().split(",");
        if (array.length != 6)
            return;

        String userId = array[0];   //脱敏过的用户ID
        String timeStamp = array[1];//时间戳
        String adGroupId = array[2];//脱敏过的广告单元ID
        String pid = array[3];      //资源位
        String noclk = array[4];    //为1 代表没有点击；为0 代表点击；
        String clk = array[5];      //为0 代表没有点击；为1 代表点击；
        String hasClk = clk;

        //用户ID + 时间戳 都不能为空
        if (StringUtil.isStrEmptyWithNull(userId) || StringUtil.isStrEmptyWithNull(timeStamp))
            return;

        if ("user".equalsIgnoreCase(userId) || "time_stamp".equalsIgnoreCase(timeStamp))
            return;


        //创建row-key
        StringBuilder tmpBuilder = new StringBuilder();
        String rowKey = tmpBuilder.append(userId).append("_").append(timeStamp).toString();

        //获取时间
        String time = StringUtil.formatTimeStampToString(timeStamp);


        Put put = new Put(rowKey.getBytes());

        String columnFamily = "raw";

        put.addColumn(columnFamily.getBytes(), "t".getBytes(), time.getBytes());//time_stamp	(t)		时间（时间戳）

        if (!StringUtil.isStrEmptyWithNull(adGroupId))
            put.addColumn(columnFamily.getBytes(), "a".getBytes(), adGroupId.getBytes());//adgroup_id	(a)		广告ID（脱敏过的广告单元ID）

        if (!StringUtil.isStrEmptyWithNull(pid))
            put.addColumn(columnFamily.getBytes(), "p".getBytes(), pid.getBytes());//pid			(p)		资源位（资源位）

        if ("1".equals(noclk) && "0".equals(clk)){
            hasClk = "0";
        } else if ("0".equals(noclk) && "1".equals(clk)){
            hasClk = "1";
        }
        put.addColumn(columnFamily.getBytes(), "c".getBytes(), hasClk.getBytes());//clk			(c)		是否点击（为0 代表没有点击 为1 代表点击）

        context.write(NullWritable.get(), put);

        //清空
        tmpBuilder.delete(0, tmpBuilder.length());
        tmpBuilder = null;
    }

}