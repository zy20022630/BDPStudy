package com.zy.study.controller;

import com.zy.study.Behavior;
import com.zy.study.QueryDO;
import com.zy.study.service.DateUtils;
import com.zy.study.service.IHBaseDataService;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/provider")
public class ProviderController {

    @Resource
    private IHBaseDataService hbaseDataService;

    @Resource
    private DiscoveryClient client; // 进行Eureka的发现服务

    @RequestMapping(value = "/queryBehaviorList")
    public List<Behavior> queryBehaviorList(@RequestBody QueryDO queryDO) {
        List<Behavior> behaviorList = null;

        //定义临时变量
        Map<String, Object> parameterMap = null;
        List<String> columnList = null;
        List<Map<String, String>> dataMapList = null;
        String[] array = null;
        Behavior behavior = null;
        String rowkey = null;
        Iterator<String> iterator = null;
        String tmpKey = null;
        String tmpValue = null;

        try {
            if (queryDO == null)
                return behaviorList;
            if (StringUtils.isBlank(queryDO.getUserId()))
                return behaviorList;
            if (StringUtils.isBlank(queryDO.getStartTime()))
                return behaviorList;
            if (StringUtils.isBlank(queryDO.getEndTime()))
                return behaviorList;
            if (StringUtils.isBlank(queryDO.getFamilyColumns()) && StringUtils.isBlank(queryDO.getQualifierColumns()))
                return behaviorList;

            //查询条件
            parameterMap = new HashMap<String, Object>();
            parameterMap.put("userId", queryDO.getUserId());//userId  ----String*-- user_id
            parameterMap.put("starttime", DateUtils.formatDate(queryDO.getStartTime(), DateUtils.DATE_TIME_PATTERN));//starttime  ----Date*-- start time Object
            parameterMap.put("endtime", DateUtils.formatDate(queryDO.getEndTime(), DateUtils.DATE_TIME_PATTERN));//endtime  ----Date*-- end time Object

            //显示字段
            columnList = new ArrayList<String>();
            if (StringUtils.isNotBlank(queryDO.getFamilyColumns())){
                array = queryDO.getFamilyColumns().split(",");
                for (String s : array){
                    if (StringUtils.isNotBlank(s)){
                        columnList.add(s);
                    }
                }
            }
            if (StringUtils.isNotBlank(queryDO.getQualifierColumns())){
                array = queryDO.getQualifierColumns().split(",");
                for (String s : array){
                    if (StringUtils.isNotBlank(s)){
                        columnList.add(s);
                    }
                }
            }

            //查询数据
            dataMapList = hbaseDataService.queryBehaviorList(parameterMap, columnList);

            //数据处理
            if (dataMapList != null && !dataMapList.isEmpty()){
                behaviorList = new ArrayList<>();

                for (Map<String, String> dataMap : dataMapList){
                    if (dataMap == null || dataMap.isEmpty()){
                        continue;
                    }

                    rowkey = dataMap.get("rowkey");
                    array = rowkey.split("_");//user_id + '_' + time_stamp

                    behavior = new Behavior();
                    behavior.setUserId(array[0]);
                    behavior.setLogTime(DateUtils.formatTimeStampToString(array[1]));

                    iterator = dataMap.keySet().iterator();
                    while (iterator.hasNext()){
                        tmpKey = iterator.next();
                        tmpValue = dataMap.get(tmpKey);

                        System.out.println("tmpKey=" + tmpKey + ", tmpValue=" + tmpValue);

                        if ("rowkey".equalsIgnoreCase(tmpKey)){
                            continue;
                        }

                        array = tmpKey.split(":");//family + ':' + column

                        /*
                            time_stamp	(t)		时间（时间戳）
                            cate		(c)		商品类目ID（脱敏过的商品类目）
                            brand		(b)		品牌ID（脱敏过的品牌词）
                         */
                        if ("ipv".equalsIgnoreCase(array[0])){//ipv-浏览
                            if ("t".equalsIgnoreCase(array[1])){//time_stamp	(t)		时间（时间戳）
                                behavior.setIpvTime(tmpValue);
                            } else if ("c".equalsIgnoreCase(array[1])){//cate		(c)		商品类目ID（脱敏过的商品类目）
                                behavior.setIpvCate(tmpValue);
                            } else if ("b".equalsIgnoreCase(array[1])){//brand		(b)		品牌ID（脱敏过的品牌词）
                                behavior.setIpvBrand(tmpValue);
                            }
                        } else if ("cart".equalsIgnoreCase(array[0])){//cart-加入购物车
                            if ("t".equalsIgnoreCase(array[1])){//time_stamp	(t)		时间（时间戳）
                                behavior.setCartTime(tmpValue);
                            } else if ("c".equalsIgnoreCase(array[1])){//cate		(c)		商品类目ID（脱敏过的商品类目）
                                behavior.setCartCate(tmpValue);
                            } else if ("b".equalsIgnoreCase(array[1])){//brand		(b)		品牌ID（脱敏过的品牌词）
                                behavior.setCartBrand(tmpValue);
                            }
                        } else if ("fav".equalsIgnoreCase(array[0])){//fav-喜欢
                            if ("t".equalsIgnoreCase(array[1])){//time_stamp	(t)		时间（时间戳）
                                behavior.setFavTime(tmpValue);
                            } else if ("c".equalsIgnoreCase(array[1])){//cate		(c)		商品类目ID（脱敏过的商品类目）
                                behavior.setFavCate(tmpValue);
                            } else if ("b".equalsIgnoreCase(array[1])){//brand		(b)		品牌ID（脱敏过的品牌词）
                                behavior.setFavBrand(tmpValue);
                            }
                        } else if ("buy".equalsIgnoreCase(array[0])){//buy-购买
                            if ("t".equalsIgnoreCase(array[1])){//time_stamp	(t)		时间（时间戳）
                                behavior.setBuyTime(tmpValue);
                            } else if ("c".equalsIgnoreCase(array[1])){//cate		(c)		商品类目ID（脱敏过的商品类目）
                                behavior.setBuyCate(tmpValue);
                            } else if ("b".equalsIgnoreCase(array[1])){//brand		(b)		品牌ID（脱敏过的品牌词）
                                behavior.setBuyBrand(tmpValue);
                            }
                        }

                    }
                    behaviorList.add(behavior);

                    dataMap.clear();
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            //清空
            if (parameterMap != null)
                parameterMap.clear();
            if (columnList != null)
                columnList.clear();
            if (dataMapList != null)
                dataMapList.clear();
        }

        return behaviorList;
    }

    @RequestMapping("/discover")
    public Object discover() { // 直接返回发现服务信息
        return this.client;
    }

}