/*
 * Copyright 2005-2015 gleme.cn. All rights reserved.
 * Support: http://www.gleme.cn

 */
package cn.gleme;

/**
 * 公共参数
 * 
 * @author XJANY Team
 * @version 4.0
 */
public final class CommonAttributes {

	/** 日期格式配比 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

	/** gleme.xml文件路径 */
	public static final String GLEME_XML_PATH = "/apps/jsmc-shop-config/gleme.xml";
	/** gleme.xml文件路径 */
	public static final String GLEME_XML_PATH_SRC = "/gleme.xml";

	/** gleme.properties文件路径 */
	public static final String GLEME_PROPERTIES_PATH = "/config.properties";

	/**
	 * 不可实例化
	 */
	private CommonAttributes() {
	}

}