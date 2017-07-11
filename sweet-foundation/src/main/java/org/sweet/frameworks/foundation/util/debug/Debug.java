package org.sweet.frameworks.foundation.util.debug;

import org.apache.log4j.Logger;

/**
 * 调试类Debug
 * @filename:Debug
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public final class Debug {
	/**
	 * 打印调试信息
	 * @param clazz 所在类
	 * @param info 信息
	 */
	public static void debug(Class<?> clazz,Object info){
		Logger.getLogger(clazz).debug(info);
	}

	/**
	 * 打印信息
	 * @param clazz 所在类
	 * @param info 信息
	 */
	public static void info(Class<?> clazz,Object info){
		Logger.getLogger(clazz).info(info);
	}

	/**
	 * 打印警告信息
	 * @param clazz 所在类
	 * @param info 信息
	 */
	public static void warn(Class<?> clazz,Object info){
		Logger.getLogger(clazz).warn(info);
	}

	/**
	 * 打印错误信息
	 * @param clazz 所在类
	 * @param info 信息
	 */
	public static void error(Class<?> clazz,Object info){
		Logger.getLogger(clazz).error(info);
	}

	/**
	 * 打印致命错误信息
	 * @param clazz 所在类
	 * @param info 信息
	 */
	public static void fatal(Class<?> clazz,Object info){
		Logger.getLogger(clazz).fatal(info);
	}
}