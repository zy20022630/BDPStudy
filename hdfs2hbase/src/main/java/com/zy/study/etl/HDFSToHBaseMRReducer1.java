package com.zy.study.etl;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;

public class HDFSToHBaseMRReducer1 extends TableReducer<Text, NullWritable, NullWritable> {

    /**
     * ============================ 读取到的一行数据 ============================

hbase(main):002:0> create 'behavior', 'ipv', 'cart', 'fav', 'buy'
     说明：
     行键row_key			user_id + time_stamp
     列簇ipv				time_stamp	cate		brand	  time_stamp=t, cate=c, brand=b
     列簇cart			time_stamp	cate		brand	  time_stamp=t, cate=c, brand=b
     列簇fav				time_stamp	cate		brand	  time_stamp=t, cate=c, brand=b
     列簇buy				time_stamp	cate		brand	  time_stamp=t, cate=c, brand=b

     目标：记录用户的行为日志信息（behavior_log）（ipv-浏览，cart-加入购物车	，fav-喜欢，	buy-购买）
        time_stamp	    (t)		时间（时间戳）
        cate			(c)		商品类目ID（脱敏过的商品类目）
        brand		    (b)		品牌ID（脱敏过的品牌词）

HDFS中/test/taobao_bi/behavior_log.csv文件：

     user,      time_stamp,     btag,   cate,   brand

     558157,    1493741625,     pv,     6250,   91286
     558157,    1493741626,     pv,     6250,   91286
     558157,    1493741627,     pv,     6250,   91286
     728690,    1493776998,     pv,     11800,  62353
     332634,    1493809895,     pv,     1101,   365477
     857237,    1493816945,     pv,     1043,   110616
     619381,    1493774638,     pv,     385,    428950

     字段说明如下：
     (1) user：脱敏过的用户ID；
     (2) time_stamp：时间戳；
     (3) btag：行为类型, 包括以下四种：
            类型          说明
            ipv         浏览
            cart        加入购物车
            fav         喜欢
            buy         购买
     (4) cate：脱敏过的商品类目；
     (5) brand: 脱敏过的品牌词；

     behavior_log 用户的行为日志 用户ID，行为类型，时间，商品类目ID，品牌ID
     */
    @Override
    protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        String[] array = key.toString().split(",");
        if (array.length != 5)
            return;

        String userId = array[0];   //脱敏过的用户ID
        String timeStamp = array[1];//时间戳
        String btag = array[2];     //行为类型: ipv-浏览，cart-加入购物车	，fav-喜欢，	buy-购买
        String cate = array[3];     //脱敏过的商品类目ID
        String brand = array[4];    //脱敏过的品牌ID

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

        //行为类型: ipv-浏览，cart-加入购物车	，fav-喜欢，	buy-购买
        String columnFamily = null;
        if (!StringUtil.isStrEmptyWithNull(btag)){
            btag = btag.toLowerCase();
            if ("ipv".equals(btag) || "pv".equals(btag))
                columnFamily = "ipv";
            else
                columnFamily = btag;

            put.addColumn(columnFamily.getBytes(), "t".getBytes(), time.getBytes());        //time_stamp	    (t)		时间（时间戳）
            if (!StringUtil.isStrEmptyWithNull(cate))
                put.addColumn(columnFamily.getBytes(), "c".getBytes(), cate.getBytes());    //cate			    (c)		商品类目ID（脱敏过的商品类目）
            if (!StringUtil.isStrEmptyWithNull(brand))
                put.addColumn(columnFamily.getBytes(), "b".getBytes(), brand.getBytes());   //brand		        (b)		品牌ID（脱敏过的品牌词）
        }

        context.write(NullWritable.get(), put);
    }

}