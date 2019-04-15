package com.zy.study;

import java.io.Serializable;

/*
    查询条件
 */
public class QueryDO implements Serializable {

    /**
    * 用户ID
    */
    private String userId;

    /**
     * 开始时间（格式：yyyy-MM-dd HH:mm:ss）
     */
    private String startTime;

    /**
     * 截止时间（格式：yyyy-MM-dd HH:mm:ss）
     */
    private String endTime;

    /**
     * 列簇名s（多个列簇以,相连）（格式：列簇,列簇,列簇,列簇）
     */
    private String familyColumns;

    /**
     * 列名s（多个列名以,相连）（格式：列簇:列名,列簇:列名,列簇:列名,列簇:列名）
     */
    private String qualifierColumns;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFamilyColumns() {
        return familyColumns;
    }

    public void setFamilyColumns(String familyColumns) {
        this.familyColumns = familyColumns;
    }

    public String getQualifierColumns() {
        return qualifierColumns;
    }

    public void setQualifierColumns(String qualifierColumns) {
        this.qualifierColumns = qualifierColumns;
    }
}