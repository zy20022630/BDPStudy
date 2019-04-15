package com.zy.study.service;

import com.zy.study.etl.DateUtils;
import com.zy.study.service.impl.HBaseDataServiceImpl;

import java.util.*;

/**
 * Created by angel on 4/13/19.
 */
public class HBaseDataServiceTest {

    public static void main(String[] args) throws Exception {

        IHBaseDataService hbaseDataService = new HBaseDataServiceImpl();
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        List<String> columnList = new ArrayList<String>();

        parameterMap.put("userId", "1000205");//userId  ----String*-- user_id
        parameterMap.put("starttime", DateUtils.formatDate("2017-05-03 17:55:01", DateUtils.DATE_TIME_PATTERN));//starttime  ----Date*-- start time Object
        parameterMap.put("endtime", DateUtils.formatDate("2017-05-03 17:55:51", DateUtils.DATE_TIME_PATTERN));//endtime  ----Date*-- end time Object

        columnList.add("ipv");
        columnList.add("buy");

        List<Map<String, String>> dataList = hbaseDataService.queryBehaviorList(parameterMap, columnList);

        if (dataList == null || dataList.isEmpty()){
            System.out.println("Query NO Datas");
        } else {
            String tmpKey = null;
            String tmpValue = null;
            for (Map<String, String> dataMap : dataList){
                if (dataMap == null || dataMap.isEmpty()){
                    System.out.println("-------------------------dataMap is empty-------------------------");
                } else {
                    System.out.println("------------------------------------------------------------------");
                    Iterator<String> iterator = dataMap.keySet().iterator();
                    while (iterator.hasNext()){
                        tmpKey = iterator.next();
                        tmpValue = dataMap.get(tmpKey);
                        System.out.print(tmpKey + "=" + tmpValue + "\t");
                    }
                }
            }
        }


    }
}
