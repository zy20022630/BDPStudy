package com.zy.study.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Http工具
 * 
 * @date 2017-08-01 17:49:31
 */
public class HttpUtil {
	
	/**
	 * 签名涉及的秘钥：gjcredit170920090901
	 */
	private static final String KEY_OF_SIGN = "gjcredit170920090901";
	
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	private HttpUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 从HttpServletRequest中获取请求内容字符串
	 * @param requestStream --InputStream*-- 请求输入流
	 * @param characterName --String-- 编码格式（默认值为UTF-8）
	 * @return 请求内容字符串
	 * @throws IOException
	 */
	public static String getContentFromRequest(InputStream requestStream, String characterName) throws IOException {
		String requestContent = null;

		//定义临时变量
		StringBuilder tmpBuffer = new StringBuilder();
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		String tmpStr = null;
		try {
			inputStreamReader = new InputStreamReader(requestStream, StringUtils.isBlank(characterName) ? "UTF-8" : characterName);
			reader = new BufferedReader(inputStreamReader);
            while ((tmpStr = reader.readLine()) != null) {
                tmpBuffer.append(tmpStr);
            }
            requestContent = tmpBuffer.toString();
			
            return requestContent;
    	} catch (IOException e) {
    		logger.error(e.getMessage());
    		throw e;
    	} finally {
    		//清空
    		tmpBuffer = null;
			if (inputStreamReader != null){
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				} finally{
					inputStreamReader = null;
				}
			}
			if (reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				} finally{
					reader = null;
				}
			}
        	tmpStr = null;
    	}
	}
	


	
	/**
	 * 将字符串集合合成一个字符串
	 * @param contentList --List--以字符串为元素的集合
	 * @return 新的字符串
	 */
	public static String getStringFromList(List<String> contentList){
		if (contentList == null || contentList.isEmpty())
			return "";
		
		StringBuilder tmpBuffer = new StringBuilder();
		for (String line : contentList){
			tmpBuffer.append(line);
		}
		
		return tmpBuffer.toString();
	}

	
	/**
	 * 获取32位随机字符串
	 * @return 随机字符串
	 */
	public static String getNoncestr(){
		return getNoncestr(32);
	}
	
	/**
	 * 获取指定位数的随机字符串
	 * @param bit --int-- 指定位数
	 * @return 随机字符串
	 */
	public static String getNoncestr(int bit){
		StringBuilder tmpBuffer = new StringBuilder();
		Random random = new Random();
		for (int i = 0;i < bit;i++){
			tmpBuffer.append(random.nextInt(10));
		}
		return tmpBuffer.toString();
	}
	
	/**
	 * 获取Unix时间戳（从1970-01-01 00:00:00开始的秒数）
	 * @return Unix时间戳
	 */
	public static long getTimestamp(){
		return (System.currentTimeMillis() / 1000);
	}
	
}