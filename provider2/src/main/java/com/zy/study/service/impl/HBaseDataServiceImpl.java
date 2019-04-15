package com.zy.study.service.impl;

import com.zy.study.service.HBaseUtils;
import com.zy.study.service.IHBaseDataService;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by angel on 4/12/19.
 */
@Service("hbaseDataService")
public class HBaseDataServiceImpl implements IHBaseDataService {


    public List<Map<String, String>> queryBehaviorList(Map<String, Object> parameterMap, List<String> columnList) throws Exception {
        List<Map<String, String>> dataList = null;
        if (parameterMap == null || parameterMap.isEmpty())
            return dataList;

        String userId = (String) parameterMap.get("userId");//userId  ----String*-- user_id
        Date starttime = (Date) parameterMap.get("starttime");//starttime  ----Date*-- start time Object
        Date endtime = (Date) parameterMap.get("endtime");//endtime  ----Date*-- end time Object

        if (isStrEmpty(userId))
            return dataList;
        if (starttime == null || endtime == null)
            return dataList;

        long startTimeStamp = starttime.getTime() / 1000;
        long endTimeStamp = endtime.getTime() / 1000;

        StringBuilder tmpBuilder = new StringBuilder();

        String startRow = tmpBuilder.delete(0, tmpBuilder.length()).append(userId).append("_").append(startTimeStamp).toString();
        String endRow = tmpBuilder.delete(0, tmpBuilder.length()).append(userId).append("_").append(endTimeStamp).toString();

        Table table = HBaseUtils.openConnection().getTable(TableName.valueOf("behavior"));

        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(startRow));
        scan.setStopRow(Bytes.toBytes(endRow));

        if (columnList != null && !columnList.isEmpty()){
            String[] array = null;
            for (String column : columnList){
                if (isStrEmpty(column))
                    continue;

                if (column.contains(":")){
                    array = column.split(":");
                    if (array.length != 2)
                        continue;
                    scan.addColumn(Bytes.toBytes(array[0]), Bytes.toBytes(array[1]));
                } else {
                    scan.addFamily(Bytes.toBytes(column));
                }
            }
        }

        ResultScanner resultScanner = table.getScanner(scan);

        dataList = new ArrayList<Map<String, String>>();
        Map<String, String> dataMap = null;
        String family = null;
        String column = null;
        String row = null;
        String value = null;

        if (resultScanner != null){
            for (Result result : resultScanner) {
                if (result.advance()){
                    Cell[] cells = result.rawCells();

                    dataMap = new HashMap<String, String>();

                    for (Cell cell : cells){
                        if (cell != null){

                            family = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
                            column = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
                            row = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
                            value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());

                            dataMap.put("rowkey", row);
                            dataMap.put(tmpBuilder.delete(0, tmpBuilder.length()).append(family).append(":").append(column).toString(), value);

                        }
                    }

                    dataList.add(dataMap);
                }
            }
        }

        //clear

        table.close();
        resultScanner.close();

        table = null;
        scan = null;
        resultScanner = null;

        return dataList;
    }

    static boolean isStrEmpty(String s){
        return (s == null || "".equals(s));
    }


}
