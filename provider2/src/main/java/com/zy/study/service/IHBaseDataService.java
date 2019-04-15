package com.zy.study.service;

import java.util.List;
import java.util.Map;

/**
 * Created by angel on 4/12/19.
 */
public interface IHBaseDataService {


    /**
     * query data from HBase by conditions
     * @param parameterMap
     *      userId  ----String*-- user_id
     *      starttime  ----Date*-- start time Object
     *      endtime  ----Date*-- end time Object
     * @param columnList
     *      format:
     *          familyColumnName
     *          familyColumnName:qualifierColumnName
     *
     * @return
     */
    List<Map<String, String>> queryBehaviorList(Map<String, Object> parameterMap, List<String> columnList) throws Exception;

}
